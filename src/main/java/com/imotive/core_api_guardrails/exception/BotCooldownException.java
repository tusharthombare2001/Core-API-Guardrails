package com.imotive.core_api_guardrails.exception;

public class BotCooldownException extends RuntimeException {
	public BotCooldownException(String message) {
		super(message);
	}

}
