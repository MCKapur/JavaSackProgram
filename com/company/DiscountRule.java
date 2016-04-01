package com.company;

import java.util.ArrayList;
import java.util.HashMap;

/**
 This class is a discount rule
 for an order. If an order is
 a specific type/rendition of
 a special discount pack order,
 then it is eligible for a new,
 cheaper price.
 */
public class DiscountRule {
    private SackOrder order; // The order that will satisfy a discount condition
    public double newPrice; // The new price after the discount rule is applied

    // Initialize a new discount rule with an order and price
    DiscountRule(SackOrder order, double newPrice) {
        this.order = order; // Set order
        this.newPrice = newPrice; // Set newPrice
    }
    // This method checks if a sack order is eligible for a discount
    public boolean orderIsEligible(SackOrder order) {
        return order.fetchAcceptedSacks().containsAll(this.order.fetchAcceptedSacks()); // It does this by checking if this order's accepted sacks are a subarray of the passed order's
    }
    // This method returns the special pack of this discount rule
    // Which is really just the discount rule's order's acceptedSacks
    public ArrayList<Sack> specialPack() {
        return this.order.fetchAcceptedSacks();
    }
}
