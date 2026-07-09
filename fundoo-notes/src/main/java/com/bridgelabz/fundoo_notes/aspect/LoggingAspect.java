package com.bridgelabz.fundoo_notes.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger =
            LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.bridgelabz.fundoo_notes.service.impl.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {

        logger.info("Entering Method : {}",
                joinPoint.getSignature().getName());

    }

    @AfterReturning(
            pointcut = "execution(* com.bridgelabz.fundoo_notes.service.impl.*.*(..))",
            returning = "result")
    public void logAfter(
            JoinPoint joinPoint,
            Object result) {

        logger.info("Method Completed : {}",
                joinPoint.getSignature().getName());

    }

}