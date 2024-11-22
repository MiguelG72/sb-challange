package com.challenge.orders.repository;

import com.challenge.orders.repository.entity.OrderEntity;
import java.util.List;
import java.util.UUID;

public interface OrderRepository {

	OrderEntity saveOrder(OrderEntity entity);

	List<OrderEntity> getAll();

	OrderEntity getOrder(UUID id);
}
