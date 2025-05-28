package com.pluralsight;

public class Drink {
    private Size size;
    private String flavor;

    public Drink(Size size, String flavor) {
        this.size = size;
        this.flavor = flavor;
    }

    public double getPrice() {
        switch (size) {
            case SMALL: return 2.00;
            case MEDIUM: return 2.50;
            case LARGE: return 3.00;
            default: return 0.0;
        }
    }

    public String getDescription() {
        return size + " drink - " + flavor + " ($" + getPrice() + ")";
    }
}