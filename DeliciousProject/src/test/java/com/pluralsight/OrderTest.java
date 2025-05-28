package com.pluralsight;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class OrderTest {

    @Test
    public void testSingleSandwichTotal() {
        Sandwich sandwich = new Sandwich(BreadType.RYE, Size.SMALL, false);
        sandwich.addTopping(new Topping("Ham", ToppingType.MEAT, false));
        sandwich.addTopping(new Topping("Swiss", ToppingType.CHEESE, false));

        Order order = new Order();
        order.addSandwich(sandwich);

        double expected = 5.50 + 1.00 + 0.75;
        assertEquals(expected, order.calculateTotal(), 0.001);
    }

    @Test
    public void testOrderWithDrinkAndChips() {
        Order order = new Order();
        order.addDrink(new Drink(Size.MEDIUM, "Cola"));
        order.addChip(new Chip("BBQ"));

        double expected = 2.50 + 1.50;
        assertEquals(expected, order.calculateTotal(), 0.001);
    }

    @Test
    public void testSignatureSandwichPrice() {
        SignatureSandwich blt = new SignatureSandwich("BLT");
        Order order = new Order();
        order.addSandwich(blt);

        double expected = 7.00 + 2.00 + 1.50; // Base + bacon + cheddar
        assertEquals(expected, order.calculateTotal(), 0.001);
    }
}
