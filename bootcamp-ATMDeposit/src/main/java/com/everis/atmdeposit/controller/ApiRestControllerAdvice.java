package com.everis.atmdeposit.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiRestControllerAdvice {

	@ExceptionHandler(IllegalAccessException.class)
	public ResponseEntity<Map<String, Object>> statusForbidden(IllegalAccessException ex) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", ex.getMessage());
		response.put("exception", ex.getClass());
		response.put("trace", ex.getStackTrace()[0]);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(ClassNotFoundException.class)
	public ResponseEntity<Map<String, Object>> statusNotFound(ClassNotFoundException ex) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", ex.getMessage());
		response.put("exception", ex.getClass());
		response.put("trace", ex.getStackTrace()[0]);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
	}

}
