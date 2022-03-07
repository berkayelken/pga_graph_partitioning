package com.berkay.yelken.parallel.ga.aspect;

import static org.springframework.http.ResponseEntity.status;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler {
	@ExceptionHandler({ Throwable.class })
	public ResponseEntity<String> handleServiceException(HttpServletRequest req, Throwable t) {
		Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

		String result = "Graph partitioning is not properly completed.";
		logger.error(result, t);

		return status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
	}
}
