package com.pluralsight;

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;

public class MainApp {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nWelcome to DELI-cious POS System");
            System.out.println("1) New Order");
            System.out.println("0) Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    handleNewOrder();
                    break;
                case 0:
                    System.out.println("Thank you for using DELI-cious POS!");

                    try {
                        for (int i = 3; i > 0; i--) {
                            System.out.println("Exiting in... " + i);
                            Thread.sleep(1000); // Pause for 1 second between counts
                        }
                    } catch (InterruptedException e) {
                        System.out.println("Oops! Something interrupted the countdown.");
                    }

                    System.out.println("Goodbye! Hope your next order is even more DELI-cious!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void handleNewOrder() {
        Order order = new Order();

        while (true) {
            System.out.println("\nOrder Menu:");
            System.out.println("1) Add Sandwich");
            System.out.println("2) Add Signature Sandwich (BLT or PHILLY or TURKISH Panini)");
            System.out.println("3) Add Drink");
            System.out.println("4) Add Chips ");
            System.out.println("5) Checkout ");
            System.out.println("0) Cancel Order");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Sandwich sandwich = buildSandwich();
                    ToppingCustomizer.customizeSandwich(sandwich);
                    order.addSandwich(sandwich);
                    break;
                case 2:
                    System.out.print("Which signature sandwich? (BLT or PHILLY or Turkish Panini): ");
                    String preset = scanner.nextLine();
                    Sandwich sig = new SignatureSandwich(preset);
                    ToppingCustomizer.customizeSandwich(sig);
                    order.addSandwich(sig);
                    break;
                case 3:
                    order.addDrink(new Drink(promptSize(), prompt("Enter drink flavor: ")));
                    break;
                case 4:
                    order.addChip(new Chip(prompt("Enter chip type: ")));
                    break;
                case 5:
                    System.out.println("\n" + order.getOrderSummary());
                    saveReceipt(order);
                    return;
                case 0:
                    System.out.println("Order canceled.");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static Sandwich buildSandwich() {
        System.out.println("Select bread type: WHITE, WHEAT, RYE, WRAP");
        BreadType bread = BreadType.valueOf(scanner.nextLine().trim().toUpperCase());

        Size size = promptSize();

        System.out.print("Would you like the sandwich toasted? (yes/no): ");
        boolean toasted = scanner.nextLine().equalsIgnoreCase("yes");

        Sandwich sandwich = new Sandwich(bread, size, toasted);

        // Offer advanced menu
        System.out.println("Would you like to use the advanced topping menu? (y/n)");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("y")) {
            toppingCustomizerMenu(sandwich);  // Modular, structured topping selection
        } else {
            // Basic manual topping entry
            while (true) {
                System.out.println("Add a topping (type \"done\" to finish): ");
                String name = scanner.nextLine();
                if (name.equalsIgnoreCase("done")) break;

                ToppingType type = null;
                while (type == null) {
                    try {
                        System.out.println("Type: REGULAR, MEAT, CHEESE");
                        String input = scanner.nextLine().trim().toUpperCase();
                        type = ToppingType.valueOf(input);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid topping type. Please enter REGULAR, MEAT, or CHEESE.");
                    }
                }

                System.out.print("Is this an extra? (yes/no): ");
                boolean extra = scanner.nextLine().equalsIgnoreCase("yes");

                sandwich.addTopping(new Topping(name, type, extra));
            }
        }

        return sandwich;
    }
    private static void toppingCustomizerMenu(Sandwich sandwich) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWould you like to add toppings to your sandwich? (y/n)");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("y")) {
            while (true) {
                System.out.println("Choose your topping (1) Meat, (2) Cheese, (3) Regular, (4) Sauce, (0) No more toppings:");
                int toppingSelection = Integer.parseInt(scanner.nextLine());

                if (toppingSelection == 0) break;

                Topping selectedTopping = null;
                switch (toppingSelection) {
                    case 1:
                        System.out.println("Choose your meat type (1) Steak, (2) Ham, (3) Salami, (4) Roast Beef, (5) Chicken, (6) Bacon");
                        selectedTopping = switch (Integer.parseInt(scanner.nextLine())) {
                            case 1 -> new Topping("Steak", ToppingType.MEAT, false);
                            case 2 -> new Topping("Ham", ToppingType.MEAT, false);
                            case 3 -> new Topping("Salami", ToppingType.MEAT, false);
                            case 4 -> new Topping("Roast Beef", ToppingType.MEAT, false);
                            case 5 -> new Topping("Chicken", ToppingType.MEAT, false);
                            case 6 -> new Topping("Bacon", ToppingType.MEAT, false);
                            default -> selectedTopping;
                        };
                        break;
                    case 2:
                        System.out.println("Choose your cheese type (1) American, (2) Provolone, (3) Cheddar, (4) Swiss");
                        switch (Integer.parseInt(scanner.nextLine())) {
                            case 1: selectedTopping = new Topping("American", ToppingType.CHEESE, false); break;
                            case 2: selectedTopping = new Topping("Provolone", ToppingType.CHEESE, false); break;
                            case 3: selectedTopping = new Topping("Cheddar", ToppingType.CHEESE, false); break;
                            case 4: selectedTopping = new Topping("Swiss", ToppingType.CHEESE, false); break;
                        }
                        break;
                    case 3:
                        System.out.println("Choose your regular topping (1) Lettuce, (2) Peppers, (3) Onions, (4) Tomatoes, (5) Jalapenos, (6) Cucumbers, (7) Pickles, (8) Guacamole, (9) Mushrooms");
                        switch (Integer.parseInt(scanner.nextLine())) {
                            case 1: selectedTopping = new Topping("Lettuce",ToppingType.REGULAR, false); break;
                            case 2: selectedTopping = new  Topping("Peppers",ToppingType.REGULAR, false); break;
                            case 3: selectedTopping = new  Topping("Onions",ToppingType.REGULAR, false); break;
                            case 4: selectedTopping = new  Topping("Tomatoes",ToppingType.REGULAR, false); break;
                            case 5: selectedTopping = new  Topping("Jalapenos",ToppingType.REGULAR, false); break;
                            case 6: selectedTopping = new  Topping("Cucumbers",ToppingType.REGULAR, false); break;
                            case 7: selectedTopping = new  Topping("Pickles",ToppingType.REGULAR, false); break;
                            case 8: selectedTopping = new  Topping("Guacamole",ToppingType.REGULAR, false); break;
                            case 9: selectedTopping = new  Topping("Mushrooms",ToppingType.REGULAR, false); break;
                        }
                        break;
                    case 4:
                        System.out.println("Choose your sauces (1) Mayo, (2) Mustard, (3) Ketchup, (4) Ranch, (5) Thousand Islands, (6) Vinaigrette");
                        switch (Integer.parseInt(scanner.nextLine())) {
                            case 1: selectedTopping = new  Topping("Mayo",ToppingType.SAUCE, false); break;
                            case 2: selectedTopping = new  Topping("Mustard",ToppingType.SAUCE, false); break;
                            case 3: selectedTopping = new  Topping("Ketchup",ToppingType.SAUCE, false); break;
                            case 4: selectedTopping = new  Topping("Ranch",ToppingType.SAUCE, false); break;
                            case 5: selectedTopping = new  Topping("Thousand Islands",ToppingType.SAUCE, false); break;
                            case 6: selectedTopping = new  Topping("Vinaigrette",ToppingType.SAUCE, false); break;
                        }
                        break;
                    default:
                        System.out.println("Invalid selection. Try again.");
                        continue;
                }

                if (selectedTopping != null) {
                    sandwich.addTopping(selectedTopping);
                    System.out.println(selectedTopping.getName() + " added!");
                }
            }
        } else {
            System.out.println("No toppings added.");
        }
    }


    private static Size promptSize() {
        System.out.print("Select size (4, 8, 12 inches): ");
        int inches = scanner.nextInt();
        scanner.nextLine();
        switch (inches) {
            case 4: return Size.SMALL;
            case 8: return Size.MEDIUM;
            case 12: return Size.LARGE;
            default: System.out.println("Invalid size. Defaulting to SMALL."); return Size.SMALL;
        }
    }

    private static String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    private static void saveReceipt(Order order) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        File receiptDir = new File("receipts");
        if (!receiptDir.exists()) {
            receiptDir.mkdir();
        }

        File receiptFile = new File(receiptDir, timestamp + ".txt");
        try (PrintWriter writer = new PrintWriter(receiptFile)) {
            writer.print(order.getOrderSummary());
            System.out.println("Receipt saved as " + receiptFile.getPath());
        } catch (IOException e) {
            System.out.println("Error saving receipt: " + e.getMessage());
        }
    }

    // DONE SignatureSandwich class system to be implemented later


    //  input-driven sandwich customization done
}
