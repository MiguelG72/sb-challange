package com.challange.orders.repository;

import com.challange.orders.model.Order;
import com.challange.orders.repository.entity.OrderEntity;

public interface OrderRepository {

	OrderEntity createOrder(OrderEntity entity);

}
