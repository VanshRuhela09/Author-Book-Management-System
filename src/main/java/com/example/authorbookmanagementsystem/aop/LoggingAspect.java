package com.example.authorbookmanagementsystem.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect for logging method entry and exit across all service and controller layers.
 * This is the sole place for logging — no log statements exist in business logic.
 */
@Slf4j
@Aspect
@Component
public class LoggingAspect {

    /**
     * Pointcut for all methods in the service layer.
     */
    @Pointcut("execution(* com.example.authorbookmanagementsystem.service..*(..))")
    public void serviceLayer() {}

    /**
     * Pointcut for all methods in the controller layer.
     */
    @Pointcut("execution(* com.example.authorbookmanagementsystem.controller..*(..))")
    public void controllerLayer() {}

    /**
     * Logs method name and arguments before executing any service method.
     */
    @Before("serviceLayer()")
    public void logBeforeServiceMethod(JoinPoint joinPoint) {
        log.info("[SERVICE] >> Entering: {} | Args: {}",
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * Logs method name and return value after a service method returns successfully.
     */
    @AfterReturning(pointcut = "serviceLayer()", returning = "result")
    public void logAfterReturningServiceMethod(JoinPoint joinPoint, Object result) {
        log.info("[SERVICE] << Exiting: {} | Returned: {}",
                joinPoint.getSignature().toShortString(), result);
    }

    /**
     * Logs method name and arguments before executing any controller method.
     */
    @Before("controllerLayer()")
    public void logBeforeControllerMethod(JoinPoint joinPoint) {
        log.info("[CONTROLLER] >> Entering: {} | Args: {}",
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * Logs method name and return value after a controller method returns successfully.
     */
    @AfterReturning(pointcut = "controllerLayer()", returning = "result")
    public void logAfterReturningControllerMethod(JoinPoint joinPoint, Object result) {
        log.info("[CONTROLLER] << Exiting: {} | Returned: {}",
                joinPoint.getSignature().toShortString(), result);
    }
}
