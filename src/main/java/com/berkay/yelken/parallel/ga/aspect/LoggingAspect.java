package com.berkay.yelken.parallel.ga.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	@Pointcut("within(com.berkay.yelken.parallel.ga..*) && !within(com.berkay.yelken.parallel.ga.aspect..*)")
	private void loggingPointcut() {
	}

	@Pointcut("within(com.berkay.yelken.parallel.ga.configuration..*)")
	private void configPointcut() {

	}

	@Pointcut("execution(public * *(..))")
	private void publicMethods() {

	}

	@AfterReturning(pointcut = "loggingPointcut() && publicMethods() && !configPointcut()", returning = "returnValue")
	public void logInfo(JoinPoint jp, Object returnValue) {
		String methodName = jp.getSignature().getName();

		StringBuilder message = new StringBuilder(methodName);
		message.append(" method is successfully completed. ");

		String className = getTargetClass(jp, methodName);
		Logger logger = LoggerFactory.getLogger(className);

		if (returnValue != null) {
			message.append(" :: returnValue={} ");
			logger.info(message.toString(), returnValue);
		} else
			logger.info(message.toString());
	}

	@AfterThrowing(pointcut = "loggingPointcut()", throwing = "ex")
	public void logError(JoinPoint jp, Exception ex) {
		String methodName = jp.getSignature().getName();

		StringBuilder message = new StringBuilder(methodName);
		message.append(" method is not completed due to exception. ");

		String className = getTargetClass(jp, methodName);
		Logger logger = LoggerFactory.getLogger(className);

		logger.error(message.toString(), ex);
	}

	private String getTargetClass(JoinPoint jp, String methodName) {
		String packageName = jp.getSignature().getDeclaringTypeName();
		String longName = jp.getSignature().toLongString();
		int start = longName.indexOf(packageName);
		int end = longName.indexOf(methodName) - 1;

		return longName.substring(start, end);
	}
}
