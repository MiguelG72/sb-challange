package com.challenge.products.service;

import com.challenge.products.model.Product;
import java.util.UUID;

public interface ProductService {

	public Product getProduct(UUID id);
}
