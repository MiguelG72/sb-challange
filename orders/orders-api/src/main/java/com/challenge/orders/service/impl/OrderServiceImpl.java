package com.challenge.orders.service.impl;

import com.challenge.orders.model.Order;
import com.challenge.orders.model.OrderList;
import com.challenge.orders.model.PatchOrder;
import com.challenge.orders.model.PostOrder;
import com.challenge.orders.repository.OrderRepository;
import com.challenge.orders.repository.entity.OrderEntity;
import com.challenge.orders.repository.entity.OrderProductEntity;
import com.challenge.orders.service.OrderService;
import java.time.ZoneId;
import java.util.UUID;
import org.challenge.core.util.PriceCalculator;
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

		return repository.saveOrder(entity)
			.toModel(zoneId);
	}

	@Override
	public OrderList getAll(){
		return new OrderList(
			repository.getAll()
				.stream()
				.map(entity -> entity.toModel(zoneId))
				.toList()
		);
	}

	@Override
	public Order updateOrder(PatchOrder patchOrder, UUID id) {

		//TODO: replace thrown exception and develop a handler
		var entity = repository.getOrder(id);

		if(entity.getStatus().isFinal()){
			throw new RuntimeException();
		}
		if(patchOrder.status() == null && patchOrder.address() == null){
			throw new RuntimeException();
		}

		if(patchOrder.status() != null){
			entity.setStatus(patchOrder.status());
		}

		if(patchOrder.address() != null){
			entity.setAddress(patchOrder.address());
		}
		return repository.saveOrder(entity)
			.toModel(zoneId);

	}
}
