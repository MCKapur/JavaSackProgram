package com.company;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 This class manages the user
 input of sacks, sack orders,
 and the output of said values.
 This is all done through the console.
 */
public class SackManagerConsole {
    public Scanner userInput; // User input mechanism through console
    SackManagerConsole() {
        userInput = new Scanner(System.in); // Initialize the user input mechanism
        initiate(); // Begin asking for input
    }

    // This captures a boolean Y or N input
    private boolean captureBooleanInput() {
        while (true) {
            String YOrN = userInput.next(); // Allow input to enter text in a new line
            if (YOrN.equals("Y")) // If user inputted Y, return true
                return true;
            else if (YOrN.equals("N")) // If N, return false
                return false;
            else
                System.out.println("Invalid input. Please re-enter either a Y or N."); // If they did not enter either, then have them re-input.
        }
    }
    // This captures a double value input
    private double captureDoubleInput() {
        double value = 0;
        boolean stopAsking = false;
        while (!stopAsking) { // Run this until we stop asking for input
            try {
                value = userInput.nextDouble(); // Enter the next double through user input
                stopAsking = true; // Stop asking because they entered a valid input
            } catch (InputMismatchException error) { // If the user did not input a valid double, they must re-enter
                System.out.println("You did not enter a valid number. Please re-enter.");
                userInput.nextLine(); // Set the input to the next line
            }
        }
        return value;
    }
    // This captures an integer value input
    private int captureIntegerInput() {
        int value = 0;
        boolean stopAsking = false;
        while (!stopAsking) { // Run this until we stop asking for input
            try {
                value = userInput.nextInt(); // Enter the next int through user input
                stopAsking = true; // Stop asking because they entered a valid input
            } catch (InputMismatchException error) { // If the user did not input a valid integer, they must re-enter
                System.out.println("You did not enter a valid number. Please re-enter.");
                userInput.nextLine(); // Set the input to the next line
            }
        }
        return value;
    }
    // This captures the input of a new sack
    private Sack captureSackInput() {
        while (true) {
            System.out.println("What type of sack would you like to add?"); // Ask the user for the resource type to add
            for (SackResource resource : Constants.sackResources) // Iterate through each sack resource
                System.out.println("- " + resource); // Use the resource -toString method which has been overloaded to display its details
            SackResource chosenResource = null; // Allow user to choose a resource
            String inputtedResource = userInput.next(); // Using input of ID
            for (SackResource resource : Constants.sackResources) {
                if (resource.uniqueIdentifier.equals(inputtedResource))
                    chosenResource = resource; // Iterate through and find the chosen resource
            }
            if (chosenResource != null) { // If it exists, find the weight
                System.out.println("Please input the weight for this sack (kg):"); // Ask user to input weight
                double weight = captureDoubleInput(); // Capture the weight into a variable as a double input
                return new Sack(chosenResource, weight); // With the weight and resource, create sack and return it
            }
            else {
                System.out.println("You did not input a valid sack type. Please re-enter."); // If user did not input valid resource, ask them to re-enter
            }
        }
    }

    // This method outputs the details of an order
    private void outputOrder(SackOrder order) {
        // Prints out mutiple different details of an order
        System.out.println("\nThanks for inputting your order! Here are the details:\n");
        System.out.println("Number of accepted orders: " + order.numberAccepted());
        System.out.println("Number of rejected orders: " + order.numberRejected());
        System.out.println("Weight of order: " + order.computeWeight() + "kg");
        System.out.println("Regular price: $" + order.computePrice(false));
        System.out.println("Price after discount: $" + order.computePrice(true));
        System.out.println("Number of special packs (discounts): " + order.numberSpecialPacks());
        System.out.println("Savings: $" + order.computeSavings());
        if (order.numberAccepted() > 0) { // If there are accepted sacks
            System.out.println("Accepted Sacks:");
            for (Sack sack : order.fetchAcceptedSacks()) // Iterate through them and print them
                System.out.println("- " + sack);
        }
        if (order.numberRejected() > 0) { // If there are rejected sacks
            System.out.println("Rejected Sacks:");
            for (Sack sack : order.fetchRejectedSacks()) // Iterate through them and print them
                System.out.println("- " + sack);
        }
        System.out.println("\r");
    }
    // This method enables the input of an order
    private void inputOrder() {
        boolean inputNewOrder = true;
        while (inputNewOrder) {
            SackOrder order = new SackOrder(); // Create the order object
            System.out.println("How many sacks would you like to input?"); // Ask the user for the size of the order
            int numberOfSacks = captureIntegerInput(); // And then capture this into a variable
            while (order.numberOfSacks() < numberOfSacks) { // Keep asking for orders until that number is hit
                Sack sack = captureSackInput(); // Capture a new sack if the user would like to keep adding
                order.addSack(sack); // Add it to the order
            }
            if (order.numberOfSacks() > 0)
                outputOrder(order);
            System.out.println("Would you like to input a new order? Y or N.");
            inputNewOrder = captureBooleanInput(); // Check if user would like to input a new order
        }
        System.out.println("Okay, thank you for using this software. Bye.");
    }
    // Initiate the manager program and user input
    private void initiate() {
        System.out.println("This tool enables you to create orders for sacks of different resources.");
        inputOrder();
    }
}
