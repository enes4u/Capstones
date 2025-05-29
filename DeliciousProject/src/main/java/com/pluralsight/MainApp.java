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

        // Add toppings interactively
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

        return sandwich;
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
