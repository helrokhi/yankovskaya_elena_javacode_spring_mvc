package ru.pro.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.pro.model.entity.UserAccess;
import ru.pro.repository.UserAccessRepository;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserAccessRepository userAccessRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", login);

        UserAccess userAccess = userAccessRepository.findByLogin(login)
                .orElseThrow(() -> {
                    log.warn("Customer {} doesn't exist", login);
                    return new UsernameNotFoundException("Customer doesn't exist");
                });

        log.info("Customer {} / {} /{}",
                userAccess.getLogin(),
                userAccess.getPassword(),
                userAccess.getRole());

        Collection<? extends GrantedAuthority> authorities =
                userAccess.getRole() != null ?
                        userAccess.getRole().getAuthorities() : Collections.emptyList();

        log.info("authorities {}", authorities);
        return new User(
                userAccess.getLogin(),
                userAccess.getPassword(),
                authorities
        );
    }
}
