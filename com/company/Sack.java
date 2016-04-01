package com.company;

import java.util.HashMap;
import java.util.Iterator;

/**
 A sack is a class that contains a
 collection of resources, of which
 each have a specified weight. This
 data can be used to calculate the
 price of an order.
 */
public class Sack {
    public SackResource resource; // The resource of the sack
    public double resourceWeight; // The weight of said resource in the sack

    // Returns true if the sack is valid (in terms of resource weight rules)
    public boolean isValid() {
        return resource.weightRange.weightIsSatisfied(resourceWeight); // Perform the test based on the resource's A-Priori weight range
    }
    // Computes the price of the sack based on the resource inside and its respective price
    public double computePrice() {
        return resource.price; // Return the A-Priori price of the resource
    }

    // Initialize the sack
    Sack(SackResource resource, double resourceWeight) {
        // Set our passed resource type and weight
        this.resource = resource;
        this.resourceWeight = resourceWeight;
    }
    // Compares two sack objects by their resource type
    public boolean equals(Object other) {
        return this.resource.equals(((Sack)other).resource);
    }
    // Overload the toString method
    public String toString() {
        return "Sack of resource " + resource.name + " and weight " + resourceWeight + "kg";
    }
}
