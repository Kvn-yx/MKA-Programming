/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espe.coffeshopp.model;

/**
 *
 * @author Mateo Artieda,MKA Programer, @ESPE
 */


public class Customer extends Person {

    private int loyaltyPoints;

    public Customer(int id, String name, String email, int loyaltyPoints) {
        super(id, name, email);
        this.loyaltyPoints = loyaltyPoints;
    }
}