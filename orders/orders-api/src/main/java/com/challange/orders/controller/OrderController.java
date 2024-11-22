package com.challange.orders.controller;

import com.challange.orders.model.Order;
import com.challange.orders.model.PostOrder;
import com.challange.orders.service.OrderService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
	private final OrderService service;

	public OrderController(OrderService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	public Order get(@PathVariable(value = "id") UUID id){

		return Order.builder()
			.address("Hello")
			.build();
	}

	@PostMapping
	public Order createOrder(@RequestBody @Valid PostOrder postOrder){
		return service.createOrder(postOrder);
	}
}
