package org.challenge.core.model;

import lombok.Getter;
import org.challenge.core.error.HttpStatusException;

@Getter
public class ErrorResponse {

	private final String errorDescription;

	public ErrorResponse(HttpStatusException ex){
		this.errorDescription = ex.getMessage();
	}

	public ErrorResponse(String errorDescription){
		this.errorDescription = errorDescription;
	}
}
