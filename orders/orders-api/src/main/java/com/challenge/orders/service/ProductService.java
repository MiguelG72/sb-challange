package com.challenge.orders.service;

import java.util.List;
import java.util.UUID;

public interface ProductService {
	public Double priceWithTax(List<UUID> products);
}
