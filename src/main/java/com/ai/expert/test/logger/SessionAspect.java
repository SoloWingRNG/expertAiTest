package com.ai.expert.test.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class SessionAspect {

	private static Logger logger = LogManager.getLogger(SessionAspect.class);
	
	@Before("execution(* com.ai.expert.test.controller.*.*(..))")
	public void sessionBefore(JoinPoint joinPoint) {
		logger.info("{} - Session managing", joinPoint);
	}
}
