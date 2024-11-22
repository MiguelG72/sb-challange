package org.challenge.core.error;

import java.net.HttpURLConnection;
import org.springframework.http.HttpStatus;

public class ConflictException extends HttpStatusException {

	public ConflictException(String message) {
		super(HttpStatus.CONFLICT.value(), message);
	}
}
