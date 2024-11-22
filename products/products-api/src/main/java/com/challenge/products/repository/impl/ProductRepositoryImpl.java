package com.challenge.products.repository.impl;

import com.challenge.products.repository.ProductRepository;
import com.challenge.products.repository.entity.ProductEntity;
import com.challenge.products.repository.jpa.JpaProductRepository;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
	private final JpaProductRepository jpaRepository;

	public ProductRepositoryImpl(JpaProductRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	@Override
	public ProductEntity getProduct(UUID id) {
		return jpaRepository.getReferenceById(id);
	}
}
