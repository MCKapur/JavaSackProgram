package com.company;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;

/**
 This class contains the constants
 involved in the program application
 tool, most specifically regarding
 sack resource types and their weight
 ranges.
 */
public class Constants {
    // Values for cement
    private static final double minimumCementWeight = 24.9;
    private static final double maximumCementWeight = 25.1;
    private static final double cementPrice = 3.0f;
    private static final String cementID = "C";
    private static final String cementName = "Cement";
    // Values for gravel
    private static final double minimumGravelWeight = 49.9;
    private static final double maximumGravelWeight = 50.1;
    private static final double gravelPrice = 2.0f;
    private static final String gravelID = "G";
    private static final String gravelName = "Gravel";
    // Values for sand
    private static final double minimumSandWeight = 49.9;
    private static final double maximumSandWeight = 50.1;
    private static final double sandPrice = 2.0f;
    private static final String sandID = "S";
    private static final String sandName = "Sand";

    public enum SackResourceType {
        SackResourceTypeCement,
        SackResourceTypeGravel,
        SackResourceTypeSand
    } // The different allowed types of resources in the sack
    public static ArrayList<SackResource> sackResources; // The different sack resources available
    public static ArrayList<DiscountRule> discountRules; // The different discount rules available

    public static void setupConstants() {
        // Create different sack resources
        SackResource standardCementResource = new SackResource(SackResourceType.SackResourceTypeCement, cementID, cementName, cementPrice, minimumCementWeight, maximumCementWeight);
        SackResource standardGravelResource = new SackResource(SackResourceType.SackResourceTypeGravel, gravelID, gravelName, gravelPrice, minimumGravelWeight, maximumGravelWeight);
        SackResource standardSandResource = new SackResource(SackResourceType.SackResourceTypeSand, sandID, sandName, sandPrice, minimumSandWeight, maximumSandWeight);
        // Insert different sack resources
        sackResources = new ArrayList<SackResource>();
        sackResources.add(standardCementResource);
        sackResources.add(standardGravelResource);
        sackResources.add(standardSandResource);

        // Create the discounted orders
        SackOrder discountedOrder1 = new SackOrder();
        discountedOrder1.addSack(new Sack(standardCementResource, (standardCementResource.weightRange.minimumWeight + standardCementResource.weightRange.maximumWeight) / 2)); // Discount pack includes 1 sack of cement. Note that we do not actually look at the weights themselves in the -equal method, but we need to take a valid weight (max + min)/2 so the sack is accepted.
        // Discount pack includes 2 sacks of sand
        discountedOrder1.addSack(new Sack(standardSandResource, (standardSandResource.weightRange.minimumWeight + standardSandResource.weightRange.maximumWeight) / 2));
        discountedOrder1.addSack(new Sack(standardSandResource, (standardSandResource.weightRange.minimumWeight + standardSandResource.weightRange.maximumWeight) / 2));
        // Discount pack includes 2 sacks of gravel
        discountedOrder1.addSack(new Sack(standardGravelResource, (standardGravelResource.weightRange.minimumWeight + standardGravelResource.weightRange.maximumWeight) / 2));
        discountedOrder1.addSack(new Sack(standardGravelResource, (standardGravelResource.weightRange.minimumWeight + standardGravelResource.weightRange.maximumWeight) / 2));
        final double discountedOrder1Price = 10.0f;

        // Insert the individual discount rules/clauses
        discountRules = new ArrayList<DiscountRule>();
        discountRules.add(new DiscountRule(discountedOrder1, discountedOrder1Price));
    }

}