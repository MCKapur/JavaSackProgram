package com.company;

public class Main {
    public static void maSin(String[] args) {
        // Create a new sack manager class and allow it to take control of application
        Constants.setupConstants(); // Setup the basic constants
        SackManagerGraphical graphical = new SackManagerGraphical();
//        SackManagerConsole console = new SackManagerConsole();
    }
}