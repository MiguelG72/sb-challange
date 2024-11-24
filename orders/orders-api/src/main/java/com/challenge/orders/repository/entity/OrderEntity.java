package com.challenge.orders.repository.entity;

import com.challenge.orders.model.Order;
import com.challenge.orders.model.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.challenge.core.util.TimeUtil;

@Entity
@Table(name = "\"order\"")
@Data
@NoArgsConstructor
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false, updatable = false)
	private String address;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@Column(name = "updated_at")
	private Instant updatedAt;

	@Column(nullable = false)
	private Double price;

	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
	private OrderStatus status;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<OrderProductEntity> products;

	@Builder
	public OrderEntity(UUID id, String address, Double price, List<OrderProductEntity> products, OrderStatus status) {
		this.id = id;
		this.address = address;
		this.price = price;

		for(var product : products){
			product.setOrder(this);
		}
		this.products = products;
		this.status = status;
	}

	@PrePersist
	protected void onCreate(){
        this.createdAt = Instant.now();
		this.status = OrderStatus.PENDING;
	}

	@PreUpdate
	protected void onUpdate(){
        this.updatedAt = Instant.now();
	}

	public Order toModel(ZoneId zoneId){
		return Order.builder()
			.id(id)
			.address(address)
			.updatedAt(TimeUtil.fromInstant(updatedAt, zoneId))
			.createdAt(TimeUtil.fromInstant(createdAt, zoneId))
			.priceWithTax(price)
			.products(products
				.stream()
				.map(OrderProductEntity::toModel)
				.toList()
			)
			.status(status)
			.build();

	}
}
