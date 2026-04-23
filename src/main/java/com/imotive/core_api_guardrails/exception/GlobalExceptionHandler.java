package com.imotive.core_api_guardrails.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(InvalidPostException.class)
	public ResponseEntity<String> postExceptionHandler(InvalidPostException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(PostNotFoundException.class)
	public ResponseEntity<String> PostNotFound(PostNotFoundException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BotLimitExceededException.class)
	public ResponseEntity<String> handleBotLimit(BotLimitExceededException ex) {
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.TOO_MANY_REQUESTS);
	}
	
	@ExceptionHandler(BotCooldownException.class)
	public ResponseEntity<String> handleCooldown(BotCooldownException ex){
		return new ResponseEntity<>(ex.getMessage(),  HttpStatus.BAD_REQUEST);
	}

}
