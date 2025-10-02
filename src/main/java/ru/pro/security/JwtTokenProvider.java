package ru.pro.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.pro.annotations.AuthLog;
import ru.pro.exception.JwtAuthenticationException;
import ru.pro.model.enums.TokenType;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static ru.pro.model.enums.TokenType.ACCESS;
import static ru.pro.model.enums.TokenType.REFRESH;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String secretKeyRaw;

    @Value("${jwt.header}")
    private String authorizationHeader;

    @Value("${jwt.expiration}")
    private long validityInMilliseconds;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKeyRaw);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    @AuthLog(action = "JWT_CREATED")
    public String createAccessToken(String username, String role) {
        log.info("Creating access token for user: {}", username);
        return createToken(username, role, validityInMilliseconds * 1000, ACCESS);
    }

    public String createRefreshToken(String username, String role) {
        log.info("Creating refresh token for {}", username);
        return createToken(username, role, 7L * 24 * 60 * 60 * 1000, REFRESH);
    }

    public void validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);

            Date expiration = claimsJws.getPayload().getExpiration();
            if (expiration.before(new Date())) {
                throw new JwtAuthenticationException("Token expired", UNAUTHORIZED);
            }
        } catch (ExpiredJwtException e) {
            throw new JwtAuthenticationException("Token expired", UNAUTHORIZED);
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("Invalid JWT token", UNAUTHORIZED);
        }
    }

    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(authorizationHeader);
    }

    private String createToken(String username, String role, long time, TokenType type) {
        Claims claims = Jwts.claims()
                .add("sub", username)
                .add("role", role)
                .add("type", type)
                .build();

        Date now = new Date();
        Date validity = new Date(now.getTime() + time);

        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(validity)
                .signWith(secretKey)
                .compact();
    }
}
