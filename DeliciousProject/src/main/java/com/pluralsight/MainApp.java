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

        // Sample logic to add a sandwich
        Sandwich sandwich = new Sandwich(BreadType.WHITE, Size.MEDIUM, true);
        sandwich.addTopping(new Topping("Meat", ToppingType.MEAT, false));
        sandwich.addTopping(new Topping("RegularExtra", ToppingType.REGULAR, true));
        sandwich.addTopping(new Topping("LettuceRegular", ToppingType.REGULAR, false));
        order.addSandwich(sandwich);

        // Sample logic to add a drink
        order.addDrink(new Drink(Size.SMALL, "Coke"));

        // Sample logic to add chips
        order.addChip(new Chip("FrenchFries"));

        System.out.println("\n" + order.getOrderSummary());

        saveReceipt(order);
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

    // @todo: input-driven sandwich customization will be implemented later
}
