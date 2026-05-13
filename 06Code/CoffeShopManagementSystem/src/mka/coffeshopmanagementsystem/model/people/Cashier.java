/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.people;

import mka.coffeshopmanagementsystem.model.order.Order;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class Cashier extends Employee {
    public Cashier() {
        super();
    }

    public Cashier(String id, String name) {
        super(id, name, "Cashier");
    }

    public Order takeOrder(Customer customer) {
        // TODO: implement
        return null;
    }

    @Override
    public void performOperation() {
        // TODO: implement
    }
}
