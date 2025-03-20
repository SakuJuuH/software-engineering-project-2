package org.example;

public record Item(double price, int quantity) {
	public double calculateTotalPrice() {
		return price * quantity;
	}
}
