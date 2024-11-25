package com.challenge.catalogue.controller;

import com.challenge.catalogue.model.ProductList;
import com.challenge.catalogue.service.ProductService;
import com.challenge.catalogue.model.Product;
import java.util.UUID;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
	private final ProductService service;

	public ProductController(ProductService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	public Product get(@PathVariable(value = "id") UUID id){

		return service.getProduct(id);
	}

	@GetMapping
	public ProductList getAll(){

		return service.getAll();
	}

}
