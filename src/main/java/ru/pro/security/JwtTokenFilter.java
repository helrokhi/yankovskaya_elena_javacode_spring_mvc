package ru.pro.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.pro.exception.JwtAuthenticationException;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest servletRequest,
            @NonNull HttpServletResponse servletResponse,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = servletRequest.getRequestURI();
        log.info("requestURI {}", requestURI);

        filterChain.doFilter(servletRequest, servletResponse);

        String token = jwtTokenProvider.resolveToken(servletRequest);
        log.info("Processing token: {}", token != null ? token.substring(0, 10) + "..." : "null");

        if (token != null) {
            try {
                if (!jwtTokenProvider.validateToken(token)) {
                    log.warn("Invalid token detected");
                    SecurityContextHolder.clearContext();
                    servletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
                    return;
                }

                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtAuthenticationException e) {
                log.warn("JWT authentication failed: {}", e.getMessage());
                SecurityContextHolder.clearContext();
                servletResponse.sendError(e.getHttpStatus().value(), e.getMessage());

                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}

