package com.pluralsight;

public class SignatureSandwich extends Sandwich {
    public SignatureSandwich(String preset) {
        super(BreadType.WHITE, Size.MEDIUM, true);

        switch (preset.toUpperCase()) {
            case "BLT":
                addTopping(new Topping("Bacon", ToppingType.MEAT, false));
                addTopping(new Topping("Cheddar", ToppingType.CHEESE, false));
                addTopping(new Topping("Lettuce", ToppingType.REGULAR, false));
                addTopping(new Topping("Tomato", ToppingType.REGULAR, false));
                addTopping(new Topping("Ranch", ToppingType.REGULAR, false));
                break;
            case "PHILLY":
                addTopping(new Topping("Steak", ToppingType.MEAT, false));
                addTopping(new Topping("American", ToppingType.CHEESE, false));
                addTopping(new Topping("Peppers", ToppingType.REGULAR, false));
                addTopping(new Topping("Mayo", ToppingType.REGULAR, false));
                break;
            default:
                throw new IllegalArgumentException("Unknown signature sandwich: " + preset);
        }
    }
}