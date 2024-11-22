package com.challenge.orders.repository.entity;

import com.challenge.orders.model.OrderProduct;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
	private OrderEntity order;

	@Column(name = "product_id")
	private UUID productId;

	public OrderProduct toModel(){
		return OrderProduct.builder()
			.id(id)
			.productId(productId)
			.build();
	}


}
