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
        // Return a new order without printing to console
        return new Order();
    }

    public String getTakeOrderMessage(Customer customer) {
         return String.format(mka.coffeshopmanagementsystem.utils.I18n.getString("model.cashier.take"), getName(), customer.getName());
    }

    @Override
    public String performOperation() {
        return String.format(mka.coffeshopmanagementsystem.utils.I18n.getString("model.cashier.op"), getName());
    }
}
