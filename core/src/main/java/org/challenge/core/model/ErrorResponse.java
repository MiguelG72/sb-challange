package org.challenge.core.model;

import org.challenge.core.error.HttpStatusException;

public class ErrorResponse {

	private final int statusCode;
	private final String message;

	public ErrorResponse(HttpStatusException ex){
		this.statusCode = ex.getStatusCode();
		this.message = ex.getMessage();
	}
}
