package ru.pro.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import ru.pro.mapper.UserMapper;
import ru.pro.model.dto.UserDto;
import ru.pro.repository.UserAccessRepository;
import ru.pro.service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService;
    private final UserAccessRepository userAccessRepository;
    private final UserMapper userMapper;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, null);
    }

    @Override
    public UserDto loadUser(Authentication authentication) {
        String login = authentication.getName();

        return userAccessRepository
                .findByLogin(login)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found with login: " + login));
    }

    @Transactional
    public void unlockUser(String login) {
        userAccessRepository.findByLogin(login).ifPresent(user -> {
            user.setAccountNonLocked(true);
            userAccessRepository.save(user);
        });
    }
}
