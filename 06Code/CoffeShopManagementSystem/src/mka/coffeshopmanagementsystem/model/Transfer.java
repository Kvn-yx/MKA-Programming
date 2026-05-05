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
public class Transfer extends Payment {
    private String accountNumber;

    public Transfer(BigDecimal amount, String accountNumber) {
        super(amount);
        this.accountNumber = accountNumber;
    }

    @Override
    public boolean processPayment(PaymentProcessor processor) {
        return processor.process(this);
    }
}

