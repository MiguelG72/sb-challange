package com.challenge.catalogue.service;

import com.challenge.catalogue.model.Product;
import java.util.UUID;

public interface ProductService {

	public Product getProduct(UUID id);
}
