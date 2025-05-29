package com.pluralsight;

import java.util.Scanner;

public class ToppingCustomizer {
    private static final Scanner scanner = new Scanner(System.in);

    public static void customizeSandwich(Sandwich sandwich) {
        while (true) {
            System.out.println("\nTopping Customization:");
            System.out.println("1) Add Topping");
            System.out.println("2) Remove Topping");
            System.out.println("0) Done");
            System.out.print("Choose an option: ");

            int option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    addToppingToSandwich(sandwich);
                    break;
                case 2:
                    removeToppingFromSandwich(sandwich);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addToppingToSandwich(Sandwich sandwich) {
        System.out.println("Enter topping name to add:");
        String name = scanner.nextLine();

        System.out.println("Choose topping type:");
        System.out.println("1) REGULAR\n2) MEAT\n3) CHEESE\n4) SAUCE\n5) SIDE");
        int typeChoice = Integer.parseInt(scanner.nextLine());

        ToppingType type;
        switch (typeChoice) {
            case 1: type = ToppingType.REGULAR; break;
            case 2: type = ToppingType.MEAT; break;
            case 3: type = ToppingType.CHEESE; break;
            case 4: type = ToppingType.SAUCE; break;
            case 5: type = ToppingType.SIDE; break;
            default:
                System.out.println("Invalid type, defaulting to REGULAR.");
                type = ToppingType.REGULAR;
        }

        System.out.print("Is this an extra? (yes/no): ");
        boolean isExtra = scanner.nextLine().equalsIgnoreCase("yes");

        sandwich.addTopping(new Topping(name, type, isExtra));
    }

    private static void removeToppingFromSandwich(Sandwich sandwich) {
        System.out.println("Current toppings:");
        int index = 1;
        for (Topping t : sandwich.getToppings()) {
            System.out.println(index++ + ") " + t.getName());
        }

        System.out.println("0) Return to main menu");

        System.out.print("Enter topping number to remove (or 0 to cancel): ");

        int removeIndex = Integer.parseInt(scanner.nextLine());

        if (removeIndex == 0) {
            System.out.println("Returning to main menu.");
            return;
        }
        if (removeIndex > 0 && removeIndex <= sandwich.getToppings().size()) {
            sandwich.getToppings().remove(removeIndex - 1);
            System.out.println("Topping removed.");
        } else {
            System.out.println("Invalid number.");
        }
    }
}
