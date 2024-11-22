package org.challenge.core.error;

import lombok.Getter;

@Getter
public abstract class HttpStatusException extends RuntimeException{

	private final Integer statusCode;

	protected HttpStatusException(Integer statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
	}
}
