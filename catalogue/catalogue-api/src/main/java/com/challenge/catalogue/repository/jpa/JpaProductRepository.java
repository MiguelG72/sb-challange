package com.challenge.catalogue.repository.jpa;

import com.challenge.catalogue.repository.entity.ProductEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<ProductEntity, UUID> {

}
