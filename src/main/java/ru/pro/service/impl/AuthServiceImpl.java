package ru.pro.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import ru.pro.model.dto.AuthenticationRequestDto;
import ru.pro.repository.UserAccessRepository;
import ru.pro.security.JwtTokenProvider;
import ru.pro.service.AuthService;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final UserAccessRepository userAccessRepository;

    @Override
    public String authenticate(AuthenticationRequestDto request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.login(), request.password())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.login());

            if (!userDetails.isAccountNonLocked()) {
                throw new LockedException("User account is locked due to multiple failed login attempts");
            }

            return jwtTokenProvider.createToken(request.login(), userDetails.getAuthorities().toString());
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Invalid login/password combination");
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, null);
    }
}
