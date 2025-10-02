package ru.pro.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pro.annotations.AuthLog;
import ru.pro.repository.UserAccessRepository;
import ru.pro.security.LoginAttemptCache;
import ru.pro.service.UserAccessService;

@Service
@RequiredArgsConstructor
public class UserAccessServiceImpl implements UserAccessService {
    private final UserAccessRepository userAccessRepository;
    private final LoginAttemptCache loginAttemptCache;

    @Override
    @Transactional
    @AuthLog(action = "ACCOUNT_BLOCKED")
    public void lockAccount(String login) {
        userAccessRepository.findByLogin(login).ifPresent(userAccess -> {
            userAccess.setAccountNonLocked(false);
            userAccessRepository.save(userAccess);
        });
    }

    @Override
    @Transactional
    public void unlockAccount(String login) {
        userAccessRepository.findByLogin(login).ifPresent(userAccess -> {
            userAccess.setAccountNonLocked(true);
            userAccessRepository.save(userAccess);
        });

        if (loginAttemptCache.isBlocked(login)) {
            loginAttemptCache.loginSucceeded(login);
        }
    }
}
