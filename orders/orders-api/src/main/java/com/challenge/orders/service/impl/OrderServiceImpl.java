package com.challenge.orders.service.impl;

import com.challenge.orders.model.Order;
import com.challenge.orders.model.OrderList;
import com.challenge.orders.model.PatchOrder;
import com.challenge.orders.model.PostOrder;
import com.challenge.orders.repository.OrderRepository;
import com.challenge.orders.repository.entity.OrderEntity;
import com.challenge.orders.repository.entity.OrderProductEntity;
import com.challenge.orders.service.OrderService;
import com.challenge.orders.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import java.time.ZoneId;
import java.util.UUID;
import org.challenge.core.error.BadRequestException;
import org.challenge.core.error.ConflictException;
import org.challenge.core.error.NotFoundException;
import org.challenge.core.util.PriceCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository repository;
    private final ZoneId zoneId;

	private final ProductService productService;

	public OrderServiceImpl(OrderRepository repository, @Value("${local.timezone}") String tz, ProductService productService) {
		this.repository = repository;
		zoneId = ZoneId.of(tz);
		this.productService = productService;
	}

	@Override
	@Transactional
	public Order createOrder(PostOrder postOrder) {
		var entity = OrderEntity.builder()
			.address(postOrder.address())
			.price(productService.priceWithTax(postOrder.products()))
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

		validatePatchBody(patchOrder);
		var entity = repository.getOrder(id);

		validateOrderStatus(entity);

		if(patchOrder.status() != null){
			entity.setStatus(patchOrder.status());
		}

		if(patchOrder.address() != null){
			entity.setAddress(patchOrder.address());
		}
		return repository.saveOrder(entity)
			.toModel(zoneId);

	}

	public void validatePatchBody(PatchOrder patchOrder){
		if(patchOrder.status() == null && patchOrder.address() == null){
			throw new BadRequestException("All fields are null");
		}
	}

	public void validateOrderStatus(OrderEntity order){
		try{
			if(order.getStatus().isFinal()){
				throw new ConflictException("Order already closed");
			}
		}catch(EntityNotFoundException e){
			throw new NotFoundException("order");
		}
	}
}
