package com.challenge.products.controller;

import com.challenge.products.model.Product;
import com.challenge.products.service.ProductService;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
	private final ProductService service;

	public ProductController(ProductService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	public Product get(@PathVariable(value = "id") UUID id){

		return service.getProduct(id);
	}

}
