/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class Cashier extends Employee {
    public Cashier(String id, String name) {
        super(id, name, "Cashier");
    }

    public Order takeOrder(Customer customer, String orderId) {
        return new Order(orderId);
    }

    @Override
    public void performOperation() {
        System.out.println(getName() + " is handling the cash register.");
    }
}

