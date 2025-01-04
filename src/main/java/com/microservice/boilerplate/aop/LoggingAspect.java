package com.microservice.boilerplate.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Definir o ponto de corte para todos os métodos públicos de qualquer classe
    @Pointcut("execution(public * com.microservice..*(..))")
    public void applicationMethods() {}

    @Around("applicationMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        logger.info("Entering method: {} with arguments: {}", methodName, args);

        try {
            Object result = joinPoint.proceed();
            logger.info("Exiting method: {} with result: {}", methodName, result);
            return result;
        } catch (Throwable throwable) {
            logger.error("Exception in method: {} with message: {}", methodName, throwable.getMessage());
            throw throwable;
        }
    }
}
