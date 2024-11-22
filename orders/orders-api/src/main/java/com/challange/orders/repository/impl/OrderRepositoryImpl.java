package com.challange.orders.repository.impl;

import com.challange.orders.repository.OrderRepository;
import com.challange.orders.repository.entity.OrderEntity;
import com.challange.orders.repository.jpa.JpaOrderRepository;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
	private final JpaOrderRepository jpaRepository;

	public OrderRepositoryImpl(JpaOrderRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	@Override
	public OrderEntity createOrder(OrderEntity entity) {
		return jpaRepository.save(entity);
	}
}
