package com.challange.orders.service.impl;

import com.challange.orders.model.Order;
import com.challange.orders.model.OrderStatus;
import com.challange.orders.model.PostOrder;
import com.challange.orders.repository.OrderRepository;
import com.challange.orders.repository.entity.OrderEntity;
import com.challange.orders.repository.entity.OrderProductEntity;
import com.challange.orders.service.OrderService;
import java.time.ZoneId;
import org.challange.core.util.PriceCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository repository;

    private final ZoneId zoneId;

	@Value("${local.tax}")
    private Double tax;

	public OrderServiceImpl(OrderRepository repository, @Value("${local.timezone}") String tz) {
		this.repository = repository;
		zoneId = ZoneId.of(tz);
	}

	@Override
	@Transactional
	public Order createOrder(PostOrder postOrder) {
		var entity = OrderEntity.builder()
			.address(postOrder.address())
			.price(PriceCalculator.priceWithTax(5.5, tax)) //TODO: get price from products api
			.products(postOrder.products().stream()
				.map(productId -> OrderProductEntity.builder()
					.productId(productId)
					.build()
				)
				.toList()
			)
			.build();

		return repository.createOrder(entity)
			.toModel(zoneId);
	}
}
