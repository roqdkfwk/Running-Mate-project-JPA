package com.suseok.run.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionTimeAspect.class);

    /**
     * 이메일 인증 서비스의 실행 시간 측정
     */
    @Around("execution(* com.suseok.run.model.service.EmailVerificationServiceImpl.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        logger.info("{} 실행 시간: {} ms", joinPoint.getSignature(), (end - start));

        return result;
    }
}
