package ru.pro.aspects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.pro.annotations.AuthLog;
import ru.pro.model.entity.AuthLogEntity;
import ru.pro.model.enums.AuthLogAction;
import ru.pro.model.enums.AuthLogStatus;
import ru.pro.repository.AuthLogRepository;

import java.util.Arrays;

import static ru.pro.model.enums.AuthLogStatus.SUCCESS;
import static ru.pro.model.enums.AuthLogStatus.FAILED;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthLogAspect {
    private final AuthLogRepository authLogRepository;

    @Around("@annotation(authLog)")
    public Object logAuthAction(ProceedingJoinPoint joinPoint, AuthLog authLog) throws Throwable {
        String action = authLog.action();
        String login = extractLogin(joinPoint.getArgs()); // достанем логин из аргументов
        AuthLogStatus status = SUCCESS;
        String details = null;

        try {
            Object result = joinPoint.proceed();
            log.info("Auth action [{}] for user [{}] succeeded", action, login);
            return result;
        } catch (Exception ex) {
            status = FAILED;
            details = ex.getMessage();
            log.warn("Auth action [{}] for user [{}] failed: {}", action, login, ex.getMessage());
            throw ex;
        } finally {
            AuthLogEntity logEntity = new AuthLogEntity();
            logEntity.setLogin(login);
            logEntity.setAction(AuthLogAction.valueOf(action));
            logEntity.setStatus(status);
            logEntity.setDetails(details);

            authLogRepository.save(logEntity);
        }
    }

    private String extractLogin(Object[] args) {
        return Arrays.stream(args)
                .filter(arg -> arg instanceof String)
                .map(Object::toString)
                .findFirst()
                .orElse("UNKNOWN");
    }
}

