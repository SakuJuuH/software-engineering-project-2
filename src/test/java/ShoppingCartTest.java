import org.example.Item;
import org.example.ShoppingCart;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

	ShoppingCart shoppingCart = new ShoppingCart();

	@Test
	void addToTotalCostTest() {
		shoppingCart.addToTotalCost(10.0);
		assertEquals(10.0, shoppingCart.getTotalCost());
	}

	@Test
	void getTotalCostTest() {
		shoppingCart.addToTotalCost(10.0);
		assertEquals(10.0, shoppingCart.getTotalCost());
	}

	@Test
	void calculateTotalCostOfItemTest() {
		assertEquals(0, new Item(0, 0).calculateTotalPrice());
		assertEquals(0, new Item(0, 1).calculateTotalPrice());
		assertEquals(0, new Item(10, 0).calculateTotalPrice());
		assertEquals(10, new Item(10, 1).calculateTotalPrice());
		assertEquals(20, new Item(10, 2).calculateTotalPrice());
	}
}