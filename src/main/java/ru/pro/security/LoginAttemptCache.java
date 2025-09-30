package ru.pro.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import ru.pro.service.UserAccessService;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginAttemptCache {
    private final CacheManager cacheManager;
    private final UserAccessService userAccessService;

    @Value("${security.login.max-attempts}")
    private int maxAttempts;

    private Cache getCache() {
        return cacheManager.getCache("loginAttempts");
    }

    public void loginFailed(String login) {
        Cache cache = getCache();
        Integer attempts = cache.get(login, Integer.class);
        attempts = (attempts == null ? 0 : attempts) + 1;
        cache.put(login, attempts);

        log.warn("Failed login attempt {} for user {}", attempts, login);

        if (attempts >= maxAttempts) {
            userAccessService.lockAccount(login);
            log.warn("User {} has been locked due to {} failed login attempts", login, attempts);
        }
    }

    public void loginSucceeded(String login) {
        getCache().evict(login);
        log.info("Successful login. Resetting failed attempts for user {}", login);
    }

    public boolean isBlocked(String login) {
        Integer attempts = getCache().get(login, Integer.class);
        boolean blocked = attempts != null && attempts >= maxAttempts;
        if (blocked) {
            log.warn("User {} is currently blocked due to {} failed login attempts", login, attempts);
        }
        return blocked;
    }
}
