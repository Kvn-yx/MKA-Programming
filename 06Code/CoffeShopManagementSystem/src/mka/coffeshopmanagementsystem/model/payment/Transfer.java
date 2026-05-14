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
public class Transfer extends Payment {
    private String accountNumber;

    public Transfer() {
        super();
    }

    public Transfer(BigDecimal amount, String accountNumber) {
        super(amount, "TRANSFER");
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public boolean processPayment(PaymentProcessor processor) {
        if (processor != null) {
            return processor.process(this);
        }
        return false;
    }
}
