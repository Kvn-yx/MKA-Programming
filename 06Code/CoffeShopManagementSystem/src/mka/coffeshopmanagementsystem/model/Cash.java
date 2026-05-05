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
public class Cash extends Payment {
    private BigDecimal amountTendered;

    public Cash(BigDecimal amount, BigDecimal amountTendered) {
        super(amount);
        this.amountTendered = amountTendered;
    }

    @Override
    public boolean processPayment(PaymentProcessor processor) {
        return processor.process(this);
    }
}

