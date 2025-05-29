package com.pluralsight;

import java.util.*;

public class Order {
    private List<Sandwich> sandwiches;
    private List<Drink> drinks;
    private List<Chip> chips;

    public Order() {
        sandwiches = new ArrayList<>();
        drinks = new ArrayList<>();
        chips = new ArrayList<>();
    }

    public void addSandwich(Sandwich sandwich) {
        sandwiches.add(sandwich);
    }

    public void addDrink(Drink drink) {
        drinks.add(drink);
    }

    public void addChip(Chip chip) {
        chips.add(chip);
    }

    public double calculateTotal() {
        double total = 0;
        for (Sandwich s : sandwiches) {
            total += s.calculatePrice();
        }
        for (Drink d : drinks) {
            total += d.getPrice();
        }
        for (Chip c : chips) {
            total += c.getPrice();
        }
        return total;
    }

    public String getOrderSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order Summary:\n");
        for (int i = 0; i < sandwiches.size(); i++) {
            Sandwich sandwich = sandwiches.get(i);
            sb.append("Sandwich ").append(i + 1).append(":\n");

            if (sandwich instanceof SignatureSandwich) {
                sb.append(((SignatureSandwich) sandwich).getPresetName());
            }


            sb.append(sandwiches.get(i).getDescription()).append("\n");
        }
        for (Drink d : drinks) {
            sb.append(d.getDescription()).append("\n");
        }
        for (Chip c : chips) {
            sb.append(c.getDescription()).append("\n");
        }
        sb.append("Total: $").append(String.format("%.2f", calculateTotal())).append("\n");
        return sb.toString();
    }
}