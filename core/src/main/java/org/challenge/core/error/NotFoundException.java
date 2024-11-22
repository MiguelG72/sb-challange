package org.challenge.core.error;

import java.net.HttpURLConnection;
import org.springframework.http.HttpStatus;

public class NotFoundException extends HttpStatusException{

	public NotFoundException(String resource) {
		super(HttpStatus.NOT_FOUND.value(), resource + " not found");
	}
}
