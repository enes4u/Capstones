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
            System.out.println("2) Add Drink");
            System.out.println("3) Add Chips");
            System.out.println("4) Checkout");
            System.out.println("0) Cancel Order");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Sandwich sandwich = buildSandwich();
                    order.addSandwich(sandwich);
                    break;
                case 2:
                    order.addDrink(new Drink(promptSize(), prompt("Enter drink flavor: ")));
                    break;
                case 3:
                    order.addChip(new Chip(prompt("Enter chip type: ")));
                    break;
                case 4:
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
        BreadType bread = BreadType.valueOf(scanner.nextLine().toUpperCase());

        Size size = promptSize();

        System.out.print("Would you like the sandwich toasted? (yes/no): ");
        boolean toasted = scanner.nextLine().equalsIgnoreCase("yes");

        Sandwich sandwich = new Sandwich(bread, size, toasted);

        // Add toppings interactively
        while (true) {
            System.out.println("Add a topping (type \"done\" to finish): ");
            System.out.println("Type: REGULAR, MEAT, CHEESE");
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("done")) break;

            ToppingType type = null;
            while (type == null) {
                try {
                    System.out.println("Type: REGULAR, MEAT, CHEESE");
                    String input = scanner.nextLine().toUpperCase();
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

    // TODO: SignatureSandwich class system to be implemented later


    //  input-driven sandwich customization done
}
