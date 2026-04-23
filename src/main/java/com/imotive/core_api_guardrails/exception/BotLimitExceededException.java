package com.imotive.core_api_guardrails.exception;

public class BotLimitExceededException extends RuntimeException {
	public BotLimitExceededException(String message) {
		super(message);
	}
}
