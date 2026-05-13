/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espe.coffeshopp.model;

/**
 *
 * @author Mateo Artieda,MKA Programer, @ESPE
 */


public class Transfer extends Payment {

    private String bank;

    public Transfer(double amount, String bank) {
        super(amount);
        this.bank = bank;
    }

    @Override
    public void processPayment() {
        System.out.println("Transferencia realizada: $" + amount);
    }
}
