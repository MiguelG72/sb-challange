package com.challenge.products.client;

import com.challenge.products.model.Product;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "products", url = "${products.url}")
public interface ProductClient {

	@GetMapping("/{id}")
	Product getProduct(@PathVariable("id") UUID id);
}
