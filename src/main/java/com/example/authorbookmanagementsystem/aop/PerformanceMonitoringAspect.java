package com.example.authorbookmanagementsystem.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Aspect for monitoring execution time of all service methods.
 * Logs a warning if any service method takes longer than 2 seconds.
 */
@Slf4j
@Aspect
@Component
public class PerformanceMonitoringAspect {

    private static final long SLOW_METHOD_THRESHOLD_MS = 2000;

    /**
     * Pointcut for all methods in the service layer.
     */
    @Pointcut("execution(* com.example.authorbookmanagementsystem.service..*(..))")
    public void serviceLayer() {}

    /**
     * Measures and logs the execution time of every service method.
     * Warns if execution exceeds the defined threshold.
     */
    @Around("serviceLayer()")
    public Object monitorExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - startTime;

        if (executionTime > SLOW_METHOD_THRESHOLD_MS) {
            log.warn("[PERFORMANCE] SLOW execution detected - {} completed in {} ms (threshold: {} ms)",
                    methodName, executionTime, SLOW_METHOD_THRESHOLD_MS);
        } else {
            log.debug("[PERFORMANCE] {} completed in {} ms", methodName, executionTime);
        }

        return result;
    }
}

