package com.challenge.orders.model;

import lombok.Getter;

@Getter
public enum OrderStatus {
	PENDING(false),
	DELIVERED(true),
	FAILED(true);

	private final boolean isFinal;

	OrderStatus(boolean isFinal) {
		this.isFinal = isFinal;
	}
}
