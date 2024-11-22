package com.challenge.orders.service;

import com.challenge.orders.model.Order;
import com.challenge.orders.model.OrderList;
import com.challenge.orders.model.PatchOrder;
import com.challenge.orders.model.PostOrder;
import java.util.UUID;

public interface OrderService {

	public Order createOrder(PostOrder order);

	OrderList getAll();

	Order updateOrder(PatchOrder patchOrder, UUID id);
}
