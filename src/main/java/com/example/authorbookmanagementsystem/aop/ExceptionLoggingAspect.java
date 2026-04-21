package com.example.authorbookmanagementsystem.aop;

import com.example.authorbookmanagementsystem.exception.DuplicateResourceException;
import com.example.authorbookmanagementsystem.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Aspect for centralized exception logging across all service and controller layers.
 * Captures exceptions thrown from service/controller methods and logs them
 * with contextual information (method name, exception type and message).
 */
@Slf4j
@Aspect
@Component
public class ExceptionLoggingAspect {

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
     * Logs ResourceNotFoundException thrown from any service method.
     */
    @AfterThrowing(pointcut = "serviceLayer()", throwing = "ex")
    public void logServiceException(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().toShortString();

        if (ex instanceof ResourceNotFoundException) {
            log.warn("[EXCEPTION][SERVICE] ResourceNotFoundException in {} : {}", methodName, ex.getMessage());
        } else if (ex instanceof DuplicateResourceException) {
            log.warn("[EXCEPTION][SERVICE] DuplicateResourceException in {} : {}", methodName, ex.getMessage());
        } else {
            log.error("[EXCEPTION][SERVICE] Unexpected exception in {} : {} - {}",
                    methodName, ex.getClass().getSimpleName(), ex.getMessage(), ex);
        }
    }

    /**
     * Logs exceptions thrown from any controller method.
     */
    @AfterThrowing(pointcut = "controllerLayer()", throwing = "ex")
    public void logControllerException(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().toShortString();
        log.error("[EXCEPTION][CONTROLLER] Exception in {} : {} - {}",
                methodName, ex.getClass().getSimpleName(), ex.getMessage());
    }
}

