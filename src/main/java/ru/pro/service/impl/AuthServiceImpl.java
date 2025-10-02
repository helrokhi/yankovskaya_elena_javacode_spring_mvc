package ru.pro.service.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import ru.pro.annotations.AuthLog;
import ru.pro.exception.JwtAuthenticationException;
import ru.pro.model.dto.AuthenticationRequestDto;
import ru.pro.security.JwtTokenProvider;
import ru.pro.security.LoginAttemptCache;
import ru.pro.service.AuthService;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final LoginAttemptCache loginAttemptCache;

    @Override
    @AuthLog(action = "LOGIN_SUCCESS")
    public String authenticate(AuthenticationRequestDto request, HttpServletResponse response) {
        String login = request.login();

        if (loginAttemptCache.isBlocked(login)) {
            throw new LockedException("Account is temporarily locked due to failed login attempts");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login, request.password())
            );

            loginAttemptCache.loginSucceeded(login);

            UserDetails userDetails = userDetailsService.loadUserByUsername(login);

            String accessToken = jwtTokenProvider.createAccessToken(login, userDetails.getAuthorities().toString());
            String refreshToken = jwtTokenProvider.createRefreshToken(login, userDetails.getAuthorities().toString());
            ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                    .httpOnly(true)
                    .secure(true)
                    .path("/api/auth/refresh")
                    .maxAge(7 * 24 * 60 * 60)
                    .sameSite("Strict")
                    .build();
            response.addHeader(SET_COOKIE, cookie.toString());

            return accessToken;
        } catch (BadCredentialsException ex) {
            loginAttemptCache.loginFailed(login);
            throw new BadCredentialsException("Invalid login/password combination");
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/refresh")
                .maxAge(0)
                .build();
        response.addHeader(SET_COOKIE, cookie.toString());
        new SecurityContextLogoutHandler().logout(request, response, null);
    }

    @Override
    public String refreshToken(HttpServletRequest request) {
        String refreshToken = Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(c -> "refreshToken".equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new JwtAuthenticationException("Refresh token not found", UNAUTHORIZED));

        jwtTokenProvider.validateToken(refreshToken);

        String username = jwtTokenProvider.getUsername(refreshToken);
        String role = jwtTokenProvider.getAuthentication(refreshToken).toString();

        return jwtTokenProvider.createAccessToken(username, role);
    }
}
