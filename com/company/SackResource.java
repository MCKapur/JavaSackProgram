package com.company;

/**
 A sack resource is a type of
 resource, for example sand,
 gravel, or cement, that is contained
 inside a sack. It contains properties
 about the resource like its names, IDs,
 prices, and weight ranges.
 */
public class SackResource {
    public Constants.SackResourceType resourceType; // The type of sack resource
    public String uniqueIdentifier; // The unique ID of the sack resource
    public String name; // The name of the sack resource
    public double price; // The price of the sack resource
    public SackResourceWeightRange weightRange; // The weight range of the sack resource

    SackResource(Constants.SackResourceType resourceType, String uniqueIdentifier, String name, double price, double minimumWeight, double maximumWeight) {
        // Initialize resource properties
        this.resourceType = resourceType;
        this.uniqueIdentifier = uniqueIdentifier;
        this.name = name;
        this.price = price;
        this.weightRange = new SackResourceWeightRange(minimumWeight, maximumWeight); // Take the min/max provided and create a weight range from it
    }

    // Overload equals method - check if resource types are equal
    public boolean equals(Object other) {
        return ((SackResource)other).resourceType.equals(this.resourceType);
    }
    // Overload -toString method and display details
    public String toString() {
        return this.uniqueIdentifier + " (" + this.name + ")" + " | " + this.weightRange.toString() /* Use weight range -toString method */ + " | Costs " + "$" + this.price;
    }
}
