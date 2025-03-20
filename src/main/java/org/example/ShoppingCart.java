package org.example;


public class ShoppingCart {
	private double total_cost;

	public ShoppingCart() {
		this.total_cost = 0;
	}

	public void addToTotalCost(double totalCostOfItem) {
		this.total_cost += totalCostOfItem;
	}

	public double getTotalCost() {
		return total_cost;
	}
}
