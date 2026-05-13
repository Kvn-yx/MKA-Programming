/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.payment;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public interface PaymentProcessor {
    boolean process(Payment payment);
}
