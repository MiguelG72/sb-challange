package com.challenge.products.service.impl;

import com.challenge.products.model.Product;
import com.challenge.products.repository.ProductRepository;
import com.challenge.products.service.ProductService;
import java.time.ZoneId;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository repository;
    private final ZoneId zoneId;

	public ProductServiceImpl(ProductRepository repository, @Value("${local.timezone}") String tz) {
		this.repository = repository;
		zoneId = ZoneId.of(tz);
	}

	@Override
	public Product getProduct(UUID id) {

		return repository.getProduct(id)
			.toModel(zoneId);
	}
}
