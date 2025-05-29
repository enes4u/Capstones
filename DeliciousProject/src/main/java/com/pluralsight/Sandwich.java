package com.pluralsight;

import java.util.ArrayList;
import java.util.List;

public class Sandwich {
    private BreadType breadType;
    private Size size;
    private boolean toasted;
    private List<Topping> toppings;

    public Sandwich(BreadType breadType, Size size, boolean toasted) {
        this.breadType = breadType;
        this.size = size;
        this.toasted = toasted;
        this.toppings = new ArrayList<>();
    }

    public void addTopping(Topping topping) {
        toppings.add(topping);
    }

    public double calculatePrice() {
        double basePrice;
        switch (size) {
            case SMALL: basePrice = 5.50; break;
            case MEDIUM: basePrice = 7.00; break;
            case LARGE: basePrice = 8.50; break;
            default: basePrice = 0;
        }

        double toppingsTotal = toppings.stream()
                .mapToDouble(t -> t.getPrice(size))
                .sum();

        return basePrice + toppingsTotal;
    }

    public List<Topping> getToppings() {
        return toppings;
    }

    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(size).append(" ").append(breadType).append(" sandwich\n");
        if (toasted) sb.append("Toasted\n");
        for (Topping topping : toppings) {
            sb.append("- ").append(topping.getName());
            if (topping.isExtra()) sb.append(" (extra)");
            sb.append("\n");
        }
        return sb.toString();
    }
}