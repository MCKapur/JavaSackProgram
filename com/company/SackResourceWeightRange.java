package com.company;

/**
 This class stores the weight ranges
 (minimum and maximum weights) for a
 sack resource.
 */
public class SackResourceWeightRange {
    double minimumWeight; // The minimum weight required for the sack resource
    double maximumWeight; // The maximum weight allowed for the sack resource

    // This method takes a weight value and checks if it falls in our range
    public boolean weightIsSatisfied(double weight) {
        // Performing check: min < weight < max
        return (minimumWeight < weight && maximumWeight > weight);
    }

    // Initializes a sack resource weight range with the max and min values
    SackResourceWeightRange(double _minimumWeight, double _maximumWeight) {
        minimumWeight = _minimumWeight; // Set min value
        maximumWeight = _maximumWeight; // Set max value
    }
    // Overloads the -toString method to display weight range details
    public String toString() {
        return "Between " + this.minimumWeight + "kg and " + this.maximumWeight + "kg, non inclusive";
    }
}
