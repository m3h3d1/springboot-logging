package com.mehedi.logging.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

//    @Around("execution(* com.mehedi.logging..*(..))")
//    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
////        String methodName = joinPoint.getSignature().toShortString();
////        String apiEndpoint = MDC.get("apiEndpoint");
////        String httpMethod = MDC.get("httpMethod");
////
////        logger.info("Entering API: {}; HTTP Method: {}, Endpoint: {}", methodName, httpMethod, apiEndpoint);
//        try {
//            Object result = joinPoint.proceed();
////            logger.info("Exiting API: {}; HTTP Method: {}, Endpoint: {}; Return value: {}", methodName, httpMethod, apiEndpoint, result);
//            return result;
//        } catch (Exception e) {
////            logger.error("Exception in API: {}; HTTP Method: {}, Endpoint: {}", methodName, httpMethod, apiEndpoint, e);
//            throw e;
//        }
//    }

    @Around("execution(* com.mehedi.logging.controller..*(..))")
    public Object logAroundController(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        String apiEndpoint = MDC.get("apiEndpoint");
        String httpMethod = MDC.get("httpMethod");

        logger.info("Entering API: {}; HTTP Method: {}, Endpoint: {}", methodName, httpMethod, apiEndpoint);
        try {
            Object result = joinPoint.proceed();
            logger.info("Exiting API: {}; HTTP Method: {}, Endpoint: {}; Return value: {}", methodName, httpMethod, apiEndpoint, result);
            return result;
        } catch (Exception e) {
            logger.error("Exception in API: {}; HTTP Method: {}, Endpoint: {}", methodName, httpMethod, apiEndpoint, e);
            throw e;
        }
    }

    @Around("execution(* com.mehedi.logging..*(..)) && !execution(* com.mehedi.logging.controller..*(..))")
    public Object logAroundService(ProceedingJoinPoint joinPoint) throws Throwable {
//        String methodName = joinPoint.getSignature().toShortString();
//        String apiEndpoint = MDC.get("apiEndpoint");
//        String httpMethod = MDC.get("httpMethod");

//        logger.info("Entering service method: {}; HTTP Method: {}, Endpoint: {}", methodName, httpMethod, apiEndpoint);
//        long startTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
//            long elapsedTime = System.currentTimeMillis() - startTime;
//            logger.info("Exiting service method: {}; HTTP Method: {}, Endpoint: {}; Return value: {}; Execution time: {} ms",
//                    methodName, httpMethod, apiEndpoint, result, elapsedTime);
            return result;
        } catch (Exception e) {
//            logger.error("Exception in service method: {}; HTTP Method: {}, Endpoint: {}", methodName, httpMethod, apiEndpoint, e);
            throw e;
        }
    }
}
