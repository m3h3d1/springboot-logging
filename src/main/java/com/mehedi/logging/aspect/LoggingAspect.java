package com.mehedi.logging.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("within(@org.springframework.web.bind.annotation.RestController *)")
    public void logBefore(JoinPoint joinPoint) {
        MDC.put("apiEndpoint", joinPoint.getSignature().toShortString());
        logger.info("Entering API: {}", joinPoint.getSignature().toShortString());
    }

    @After("within(@org.springframework.web.bind.annotation.RestController *)")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Exiting API: {}", joinPoint.getSignature().toShortString());
        MDC.clear();
    }

    @Around("execution(* com.mehedi.logging.service..*(..))")
    public Object logAroundService(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug("Entering service method: {}", joinPoint.getSignature().toShortString());
        try {
            Object result = joinPoint.proceed();
            logger.debug("Exiting service method: {}; Return value: {}", joinPoint.getSignature().toShortString(), result);
            return result;
        } catch (Exception e) {
            logger.error("Exception in service method: {}", joinPoint.getSignature().toShortString(), e);
            throw e;
        }
    }
}
