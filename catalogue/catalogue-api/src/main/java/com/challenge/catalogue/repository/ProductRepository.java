package com.challenge.catalogue.repository;

import com.challenge.catalogue.repository.entity.ProductEntity;
import java.util.UUID;

public interface ProductRepository {

	ProductEntity getProduct(UUID id);

}
