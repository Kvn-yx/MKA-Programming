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
public class CreditCard extends Payment {
    private String paymentToken;

    public CreditCard(BigDecimal amount, String paymentToken) {
        super(amount);
        this.paymentToken = paymentToken;
    }

    @Override
    public boolean processPayment(PaymentProcessor processor) {
        return processor.process(this);
    }
}

