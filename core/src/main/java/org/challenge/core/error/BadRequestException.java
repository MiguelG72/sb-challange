package org.challenge.core.error;

import java.net.HttpURLConnection;
import org.springframework.http.HttpStatus;

public class BadRequestException extends HttpStatusException {

	public BadRequestException(String message) {
		super(HttpStatus.BAD_REQUEST.value(), message);
	}
}
