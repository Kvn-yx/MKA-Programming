/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

/**
 *
 * @author Anthony Aimacaña
 */
public class Transfer extends Payment {
    private String accountNumber;
    private String accountHolder;

    public Transfer(double amount, String accountNumber, String accountHolder) {
        super(amount);
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
    }

    @Override
    public boolean processPayment() {
        // Transfer process logic
        return true;
    }
}