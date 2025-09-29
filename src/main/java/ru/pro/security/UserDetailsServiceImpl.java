package ru.pro.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.pro.model.entity.Customer;
import ru.pro.repository.CustomerRepository;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", login);

        Customer customer = customerRepository.findByEmail(login)
                .orElseThrow(() -> {
                    log.warn("Customer {} doesn't exist", login);
                    return new UsernameNotFoundException("Customer doesn't exist");
                });

        log.info("Customer {} / {} /{}",
                customer.getEmail(),
                customer.getPassword(),
                customer.getRole());

        Collection<? extends GrantedAuthority> authorities =
                customer.getRole() != null ?
                        customer.getRole().getAuthorities() : Collections.emptyList();

        log.info("authorities {}", authorities);
        return new User(
                customer.getEmail(),
                customer.getPassword(),
                authorities
        );
    }
}
