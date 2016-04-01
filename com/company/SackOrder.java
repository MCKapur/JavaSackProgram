package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 A sack order is an order/transaction
 of one or more sacks. The order contains
 a collection of approved sacks and rejected
 sacks. This data can be used to compute overall
 prices and weights for the consumer.
 */
public class SackOrder {
    private ArrayList<Sack> rejectedSacks; // A mutable collection storing the rejected sacks in our order
    private ArrayList<Sack> acceptedSacks; // A mutable collection storing the accepted sacks in our order
    private int numberSpecialPacks = 0; // The number of special packs in the order

    // Adds a sack to our order and returns whether it was placed in the accepted sack pool
    public boolean addSack(Sack sack) {
        boolean valid = sack.isValid(); // Store the sack's validity into this variable
        if (valid) // If valid, then add it to our accepted sacks
            acceptedSacks.add(sack);
        else // Else add it to our rejected sacks
            rejectedSacks.add(sack);
        return valid; // This boolean corresponds to the placement in the accepted pool
    }

    // Calculates the total weight of our order (summation over sack weights)
    public double computeWeight() {
        double totalWeight = 0;
        // Iterate through our accepted sacks
        for (Sack sack : acceptedSacks)
            totalWeight += sack.resourceWeight; // And add each weight to the total
        return totalWeight;
    }
    // Remove a single special pack instance from the order
    private void removeSpecialPackInstance(ArrayList<Sack> specialPack, SackOrder order) {
        for (Sack sack : specialPack) // Iterate through the sacks in the special pack
            order.acceptedSacks.remove(order.acceptedSacks.indexOf(sack)); // Delete ONE MATCH of the sack only - can do this using -indexOf which only returns one occurrence's index
    }
    // Recursively apply discounts -> look for discounts, add discount, remove discounted items, restart
    private double recursivelyApplyDiscounts(double cumulativeDiscount, SackOrder order) {
        for (DiscountRule discountRule : Constants.discountRules) { // Iterate through the official discount rules
            if (discountRule.orderIsEligible(order)) { // If this order is eligible for a discount
                numberSpecialPacks++; // Increment the number of special packs that exist in the order
                cumulativeDiscount += discountRule.newPrice; // Set the total price to the discounted price
                removeSpecialPackInstance(discountRule.specialPack(), order);
                return recursivelyApplyDiscounts(cumulativeDiscount, order); // And look for any more discounts
            }
        }
        return cumulativeDiscount; // If no more discounts, then return the discounted price
    }
    // Calculates the total price of our order (summation over sack costs). Pass a boolean argument that specifies whether to apply discounts for special packs.
    public double computePrice(boolean applyDiscounts) {
        SackOrder orderToEvaluate = new SackOrder(); // Create new variable copy because we will be removing values from it for discounting purposes
        orderToEvaluate.acceptedSacks = new ArrayList<>(this.fetchAcceptedSacks());
        double totalPrice = 0;
        if (applyDiscounts) {
            numberSpecialPacks = 0;
            // Look for discount rules first
            // This method will also modify the order to remove the discounted sacks (special packs)
            totalPrice = recursivelyApplyDiscounts(0, orderToEvaluate);
        }
        // After discounts are applied, iterate through our accepted sacks and compute price based on regular sacks
        for (Sack sack : orderToEvaluate.acceptedSacks)
            totalPrice += sack.computePrice(); // And add each price to the total
        return totalPrice;
    }
    // Computing the savings simply means subtracting the price with applied discounts from the price without discounts
    public double computeSavings() {
        return computePrice(false) - computePrice(true);
    }

    // Returns the sacks that were accepted
    public ArrayList<Sack> fetchAcceptedSacks() {
        // Perform any other processing here
        return acceptedSacks;
    }
    // Returns the sacks that were rejected
    public ArrayList<Sack> fetchRejectedSacks() {
        // Perform any other processing here
        return rejectedSacks;
    }
    // Returns the total number of sacks in the order
    public int numberOfSacks() {
        return numberAccepted() + numberRejected(); // Which is the addition of the rejected and accepted sacks
    }
    // Returns the number of accepted sacks
    public int numberAccepted() {
        return acceptedSacks.size(); // Number of accepted sacks = size of accepted sacks array
    }
    // Returns the number of rejected sacks
    public int numberRejected() {
        return rejectedSacks.size(); // Number of rejected sacks = size of rejected sacks array
    }
    // Returns the number of special packs
    // This must be called after -computePrice
    public int numberSpecialPacks() {
        return this.numberSpecialPacks;
    }

    SackOrder() {
        // Initialize the sack arrays
        acceptedSacks = new ArrayList<Sack>();
        rejectedSacks = new ArrayList<Sack>();
    }
}
