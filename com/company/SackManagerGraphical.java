package com.company;

import java.awt.event.*;
import java.awt.Dimension;
import javax.swing.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import javax.swing.text.*;
import javax.swing.event.*;

/**
 This class manages the user
 input of sacks, sack orders,
 and the output of said values. All
 of this is done through a graphical
 interface.
 */
public class SackManagerGraphical extends JFrame {
    final String resourceTypesDescriptive[] = new String[]{"Cement (between 24.9kg and 25.1kg, $3.0)", "Gravel (between 49.9kg and 50.1kg, $2.0)", "Sand (between 49.9kg and 50.1kg, $2.0)"};
    final String resourceTypesCode[] = new String[]{"C", "G", "S"};

    public SackOrder order;
    public JPanel panel;

    public void refreshPanel() {
        panel.repaint();
        panel.revalidate();
    }

    public void resetPanel() {
        panel.removeAll();
        refreshPanel();
    }

    public void addOrderCreationElements() {
        resetPanel();
        JButton finishOrder = new JButton("Complete Order");
        JButton addNewSack = new JButton("Add Sack");
        JButton cancelOrder = new JButton("Cancel");
        addNewSack.setEnabled(false);
        final String weightTextFieldPlaceholder = "Input sack weight";
        JTextField weightTextField = new JTextField(weightTextFieldPlaceholder);
        JComboBox resourceTypeComboBox = new JComboBox(resourceTypesDescriptive);
        cancelOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addHomeScreen();
            }
        });
        finishOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addHomeScreen();
                addOrderDetails();
            }
        });
        addNewSack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SackResource chosenResource = null;
                for (SackResource resource : Constants.sackResources) {
                    if (resource.uniqueIdentifier.equals(resourceTypesCode[resourceTypeComboBox.getSelectedIndex()])) {
                        chosenResource = resource;
                    }
                }
                order.addSack(new Sack(chosenResource, Double.parseDouble(weightTextField.getText())));
            }
        });
        weightTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (weightTextField.getText().equals(weightTextFieldPlaceholder))
                    weightTextField.setText("");
            }
            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        weightTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (weightTextField.getText().length() == 0)
                    addNewSack.setEnabled(false);
                else
                    addNewSack.setEnabled(true);
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                if (weightTextField.getText().length() == 0)
                    addNewSack.setEnabled(false);
                else
                    addNewSack.setEnabled(true);
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                if (weightTextField.getText().length() == 0)
                    addNewSack.setEnabled(false);
                else
                    addNewSack.setEnabled(true);
            }
        });
        weightTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || (c == '.' && !weightTextField.getText().contains("" + c)) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
        panel.add(finishOrder);
        panel.add(addNewSack);
        panel.add(cancelOrder);
        panel.add(resourceTypeComboBox);
        panel.add(weightTextField);
        refreshPanel();
    }

    public void addOrderDetails() {
        String statusString = "<html>";
        statusString += "\nThanks for inputting your order! Here are the details:" + "<br>";
        statusString += "Number of accepted orders: " + order.numberAccepted() + "<br>";
        statusString += "Number of rejected orders: " + order.numberRejected() + "<br>";
        statusString += "Weight of order: " + order.computeWeight() + "kg" + "<br>";
        statusString += "Regular price: $" + order.computePrice(false) + "<br>";
        statusString += "Price after discount: $" + order.computePrice(true) + "<br>";
        statusString += "Number of special packs (discounts): " + order.numberSpecialPacks() + "<br>";
        statusString += "Savings: $" + order.computeSavings() + "<br>";
        if (order.numberAccepted() > 0) { // If there are accepted sacks
            statusString += "Accepted Sacks:" + "<br>";
            for (Sack sack : order.fetchAcceptedSacks()) // Iterate through them and print them
                statusString += "- " + sack + "<br>";
        }
        if (order.numberRejected() > 0) { // If there are rejected sacks
            statusString += "Rejected Sacks:" + "<br>";
            for (Sack sack : order.fetchRejectedSacks()) // Iterate through them and print them
                statusString += "- " + sack + "<br>";
        }
        statusString += "</html>";
        JLabel statusLabel = new JLabel(statusString);
        panel.add(statusLabel);
        refreshPanel();
    }

    public void addHomeScreen() {
        resetPanel();
        JButton createOrderButton = new JButton("Create Order");
        createOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addOrderCreationElements();
            }
        });
        panel.add(createOrderButton);
        refreshPanel();
    }

    SackManagerGraphical() {
        order = new SackOrder();

        panel = new JPanel();
        addHomeScreen();
        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);
    }
}
