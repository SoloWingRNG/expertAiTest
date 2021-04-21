package com.ai.expert.test.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class LoggerAspect {
	
	private static Logger logger = LogManager.getLogger(LoggerAspect.class);
	
	@Before("execution(* com.ai.expert.test.*.*(..))")
	public void logsBefore(JoinPoint joinPoint) {
		logger.info("{} - START", joinPoint);
	}
	
	@After("execution(* com.ai.expert.test.*.*(..))")
	public void logsAfter(JoinPoint joinPoint) {
		logger.info("{} - END", joinPoint);
	}
	
	@AfterReturning(value = "execution(* com.ai.expert.test.*.*(..))", returning = "result")
	public void logsAfterReturning(JoinPoint joinPoint, Object result) {
		logger.info("{} returns {}", joinPoint, result);
	}
	
	@AfterThrowing(value = "execution(* com.ai.expert.test.*.*(..))", throwing = "ex")
	public void logsAfterThrowing(Exception ex) throws Throwable {
		logger.error("Logging exception: {}", ex);
	}
}
