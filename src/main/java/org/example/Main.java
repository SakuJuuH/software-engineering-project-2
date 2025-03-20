package org.example;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		ShoppingCart shoppingCart = new ShoppingCart();

		Scanner sc = new Scanner(System.in);

		System.out.println("Select the language: ");
		System.out.println("1. Finnish");
		System.out.println("2. Japanese");
		System.out.println("3. Swedish");

		int language_num = sc.nextInt();

		Locale locale = switch (language_num) {
			case 1 -> new Locale("fi", "FI");
			case 2 -> new Locale("ja", "JP");
			case 3 -> new Locale("sv", "SE");
			default -> {
				System.out.println("Invalid input. Defaulting to English (US).");
				yield new Locale("en", "US");
			}
		};

		ResourceBundle rb;

		try {
			rb = ResourceBundle.getBundle("MessagesBundle", locale);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return;
		}

		System.out.println(rb.getString("amount"));

		int amountOfItems = sc.nextInt();

		for (int i = 0; i < amountOfItems; i++) {
			System.out.println(i+1);

			System.out.println(rb.getString("price"));

			double price_per_item = sc.nextDouble();

			System.out.println(rb.getString("quantity"));

			int quantity = sc.nextInt();
			Item item = new Item(price_per_item, quantity);

			double totalCostOfItem = item.calculateTotalPrice();
			shoppingCart.addToTotalCost(totalCostOfItem);
		}

		String totalCost = MessageFormat.format(rb.getString("total"), String.format("%.2f", shoppingCart.getTotalCost()));

		System.out.println(totalCost);
	}
}