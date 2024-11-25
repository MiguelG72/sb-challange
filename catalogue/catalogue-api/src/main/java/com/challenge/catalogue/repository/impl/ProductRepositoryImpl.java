package com.challenge.catalogue.repository.impl;

import com.challenge.catalogue.repository.entity.ProductEntity;
import com.challenge.catalogue.repository.ProductRepository;
import com.challenge.catalogue.repository.jpa.JpaProductRepository;
import java.util.List;
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

	@Override
	public List<ProductEntity> getAll() {
		return jpaRepository.findAll();
	}
}
