package com.pluralsight;

public class SignatureSandwich extends Sandwich {
    private final String preset;
    public SignatureSandwich(String preset) {
        super(BreadType.WHITE, Size.MEDIUM, true);
        this.preset = preset.trim().toUpperCase();

        switch (this.preset) {
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
            case "TURKISH":
                System.out.println("Applying TURKISH Panini preset toppings");
                addTopping(new Topping("Sucuk", ToppingType.MEAT, false));
                addTopping(new Topping("Kasar", ToppingType.CHEESE, false));
                addTopping(new Topping("Tomatoes", ToppingType.REGULAR, false));
                addTopping(new Topping("Cucumbers", ToppingType.REGULAR, false));
                addTopping(new Topping("Thousand Islands", ToppingType.SAUCE, false));
                break;
            default:
                throw new IllegalArgumentException("Unknown signature sandwich: " + preset);
        }
    }
    public String getPresetName() {

        return preset;
    }

    @Override
    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("Signature Sandwich - ").append(preset).append("\n");
        sb.append(super.getDescription());
        return sb.toString();
    }
}