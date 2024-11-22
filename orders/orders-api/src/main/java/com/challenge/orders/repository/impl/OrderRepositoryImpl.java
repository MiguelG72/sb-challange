package com.challenge.orders.repository.impl;

import com.challenge.orders.repository.OrderRepository;
import com.challenge.orders.repository.entity.OrderEntity;
import com.challenge.orders.repository.jpa.JpaOrderRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
	private final JpaOrderRepository jpaRepository;

	public OrderRepositoryImpl(JpaOrderRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	@Override
	public OrderEntity saveOrder(OrderEntity entity) {
		return jpaRepository.save(entity);
	}

	@Override
	public List<OrderEntity> getAll(){
		return jpaRepository.findAll();
	}

	@Override
	public OrderEntity getOrder(UUID id){
		return jpaRepository.getReferenceById(id);
	}
}
