package com.challenge.catalogue.service.impl;

import com.challenge.catalogue.model.ProductList;
import com.challenge.catalogue.service.ProductService;
import com.challenge.catalogue.model.Product;
import com.challenge.catalogue.repository.ProductRepository;
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

	@Override
	public ProductList getAll() {

		return new ProductList(
			repository.getAll()
			.stream()
			.map(e -> e.toModel(zoneId))
			.toList()
		);
	}
}
