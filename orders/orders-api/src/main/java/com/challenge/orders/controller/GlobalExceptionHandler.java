package com.challenge.orders.controller;

import feign.FeignException;
import org.challenge.core.error.HttpStatusException;
import org.challenge.core.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(HttpStatusException.class)
    public ResponseEntity<ErrorResponse> handleHttpStatusException(
            HttpStatusException ex) {

        return ResponseEntity
			.status(ex.getStatusCode())
			.contentType(MediaType.APPLICATION_JSON)
			.body(new ErrorResponse(ex));
    }

	@ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<ErrorResponse> handleExternalApiNotFound(
            FeignException.NotFound ex) {

        return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.contentType(MediaType.APPLICATION_JSON)
			.body(new ErrorResponse(ex.getMessage()));
    }

}
