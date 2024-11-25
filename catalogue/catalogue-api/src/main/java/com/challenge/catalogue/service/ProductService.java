package com.challenge.catalogue.service;

import com.challenge.catalogue.model.Product;
import com.challenge.catalogue.model.ProductList;
import java.util.UUID;

public interface ProductService {

	public Product getProduct(UUID id);

	ProductList getAll();
}
