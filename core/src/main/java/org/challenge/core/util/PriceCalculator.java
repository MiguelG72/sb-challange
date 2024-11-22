package org.challenge.core.util;

public class PriceCalculator {

	public static Double priceWithTax(Double price, Double tax){

		return price + tax*price;
	}

	public static Double priceWithoutTax(Double priceWithTax, Double tax){

		return priceWithTax/(1 + tax);
	}
}
