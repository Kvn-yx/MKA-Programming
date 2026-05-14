/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.payment;

import java.math.BigDecimal;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class Cash extends Payment {
    private BigDecimal amountTendered;

    public Cash() {
        super();
    }

    public Cash(BigDecimal amount, BigDecimal amountTendered) {
        super(amount, "CASH");
        this.amountTendered = amountTendered;
    }

    public BigDecimal getAmountTendered() {
        return amountTendered;
    }

    public void setAmountTendered(BigDecimal amountTendered) {
        this.amountTendered = amountTendered;
    }

    @Override
    public boolean processPayment(PaymentProcessor processor) {
        if (processor != null) {
            return processor.process(this);
        }
        return false;
    }
}
