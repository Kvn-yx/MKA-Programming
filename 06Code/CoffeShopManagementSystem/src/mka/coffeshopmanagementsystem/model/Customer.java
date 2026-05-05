/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class Customer extends Person {
    private String email;
    private int loyaltyPoints;

    public Customer(String id, String name, String email) {
        super(id, name);
        this.email = email;
        this.loyaltyPoints = 0;
    }

    public String getEmail() {
        return email;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void addLoyaltyPoints(int points) {
        this.loyaltyPoints += points;
    }
}

