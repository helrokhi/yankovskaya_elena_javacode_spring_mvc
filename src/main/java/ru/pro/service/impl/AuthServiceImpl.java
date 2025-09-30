package ru.pro.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import ru.pro.model.dto.AuthenticationRequestDto;
import ru.pro.security.JwtTokenProvider;
import ru.pro.security.LoginAttemptCache;
import ru.pro.service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final LoginAttemptCache loginAttemptCache;

    @Override
    public String authenticate(AuthenticationRequestDto request) {
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
            return jwtTokenProvider.createToken(login, userDetails.getAuthorities().toString());
        } catch (BadCredentialsException ex) {
            loginAttemptCache.loginFailed(login);
            throw new BadCredentialsException("Invalid login/password combination");
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, null);
    }
}
