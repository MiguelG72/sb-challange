package com.challenge.catalogue.repository.entity;

import com.challenge.catalogue.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.challenge.core.util.TimeUtil;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false, updatable = false)
	private String name;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@Column(name = "updated_at")
	private Instant updatedAt;

	@Column(nullable = false)
	private Double price;
	@PrePersist
	protected void onCreate(){
        this.createdAt = Instant.now();
	}

	@PreUpdate
	protected void onUpdate(){
        this.updatedAt = Instant.now();
	}

	public Product toModel(ZoneId zoneId){
		return Product.builder()
			.id(id)
			.name(name)
			.updatedAt(TimeUtil.fromInstant(updatedAt, zoneId))
			.createdAt(TimeUtil.fromInstant(createdAt, zoneId))
			.price(price)
			.build();

	}
}
