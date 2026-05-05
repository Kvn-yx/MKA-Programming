/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

import java.math.BigDecimal;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public abstract class Payment {
    private BigDecimal amount;

    public Payment(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public abstract boolean processPayment(PaymentProcessor processor);
}

