package com.challenge.orders.controller;

import com.challenge.orders.model.Order;
import com.challenge.orders.model.OrderList;
import com.challenge.orders.model.PatchOrder;
import com.challenge.orders.model.PostOrder;
import com.challenge.orders.service.OrderService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

	@PostMapping
	public Order createOrder(@RequestBody @Valid PostOrder postOrder){
		return service.createOrder(postOrder);
	}

	@GetMapping
	public OrderList getAllOrders(){
		return service.getAll();
	}

	@PatchMapping("/{id}")
	public Order updateOrder(@RequestBody PatchOrder patchOrder, @PathVariable(value = "id") UUID id){

		return service.updateOrder(patchOrder, id);
	}
}
