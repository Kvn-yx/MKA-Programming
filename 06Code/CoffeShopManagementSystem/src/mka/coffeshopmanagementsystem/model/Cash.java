/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

/**
 *
 * @author Anthony Aimacaña
 */
public class Cash extends Payment {
    private double amountTendered;

    public Cash(double amount, double amountTendered) {
        super(amount);
        this.amountTendered = amountTendered;
    }

    @Override
    public boolean processPayment() {
        // Cash process logic
        return amountTendered >= getAmount();
    }
}