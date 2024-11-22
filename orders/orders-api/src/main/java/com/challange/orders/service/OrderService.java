package com.challange.orders.service;

import com.challange.orders.model.Order;
import com.challange.orders.model.PostOrder;

public interface OrderService {

	public Order createOrder(PostOrder order);
}
