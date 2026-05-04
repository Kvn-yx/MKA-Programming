/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

/**
 *
 * @author Anthony Aimacaña
 */
public class Cashier extends Employee {
    public Cashier(String id, String name) {
        super(id, name, "Cashier");
    }

    public Order takeOrder(Customer customer) {
        // Implementation logic
        return new Order("dummy-id", customer);
    }

    @Override
    public void performOperation() {
        // Implementation logic
    }
}