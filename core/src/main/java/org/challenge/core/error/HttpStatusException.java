package org.challenge.core.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class HttpStatusException extends RuntimeException{

	private final HttpStatus statusCode;

	protected HttpStatusException(HttpStatus statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
	}
}
