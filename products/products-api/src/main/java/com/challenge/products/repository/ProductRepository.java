package com.challenge.products.repository;

import com.challenge.products.repository.entity.ProductEntity;
import java.util.UUID;

public interface ProductRepository {

	ProductEntity getProduct(UUID id);

}
