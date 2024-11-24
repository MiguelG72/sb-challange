package com.challenge.orders.service.impl;

import com.challenge.orders.service.ProductService;
import com.challenge.catalogue.client.ProductClient;
import com.challenge.catalogue.model.Product;
import java.util.List;
import java.util.UUID;
import org.challenge.core.util.PriceCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
	private final ProductClient client;

	public ProductServiceImpl(ProductClient client) {
		this.client = client;
	}

	@Value("${local.tax}")
    private Double tax;

	@Override
	public Double priceWithTax(List<UUID> products) {
		return PriceCalculator.priceWithTax(
			products.stream()
				.map(client::getProduct)
				.map(Product::price)
				.reduce(0.0, Double::sum), tax
		);
	}
}
