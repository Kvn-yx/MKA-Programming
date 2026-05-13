/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espe.coffeshopp.model;

/**
 *
 * @author Mateo Artieda,MKA Programer, @ESPE
 */


public class Cash extends Payment {

    public Cash(double amount) {
        super(amount);
    }

    @Override
    public void processPayment() {
        System.out.println("Pago en efectivo: $" + amount);
    }
}
