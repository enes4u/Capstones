package com.pluralsight;

public class Topping {
    private String name;
    private ToppingType type;
    private boolean isExtra;

    public Topping(String name, ToppingType type, boolean isExtra) {
        this.name = name;
        this.type = type;
        this.isExtra = isExtra;
    }

    public String getName() {
        return name;
    }

    public ToppingType getType() {
        return type;
    }

    public boolean isExtra() {
        return isExtra;
    }

    public double getPrice(Size size) {
        switch (type) {
            case MEAT:
                return baseMeatPrice(size) + (isExtra ? extraMeatPrice(size) : 0);
            case CHEESE:
                return baseCheesePrice(size) + (isExtra ? extraCheesePrice(size) : 0);
            default:
                return 0.0; // Regular toppings included
        }
    }

    private double baseMeatPrice(Size size) {
        switch (size) {
            case SMALL: return 1.00;
            case MEDIUM: return 2.00;
            case LARGE: return 3.00;
            default: return 0.0;
        }
    }

    private double extraMeatPrice(Size size) {
        switch (size) {
            case SMALL: return 0.50;
            case MEDIUM: return 1.00;
            case LARGE: return 1.50;
            default: return 0.0;
        }
    }

    private double baseCheesePrice(Size size) {
        switch (size) {
            case SMALL: return 0.75;
            case MEDIUM: return 1.50;
            case LARGE: return 2.25;
            default: return 0.0;
        }
    }

    private double extraCheesePrice(Size size) {
        switch (size) {
            case SMALL: return 0.30;
            case MEDIUM: return 0.60;
            case LARGE: return 0.90;
            default: return 0.0;
        }
    }
}