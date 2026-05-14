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
        System.out.println("Cajero " + getName() + " está tomando el pedido de " + customer.getName());
        return new Order(); // Retorna una orden vacía por ahora
    }

    @Override
    public void performOperation() {
        System.out.println("Cajero " + getName() + " está procesando pagos y atendiendo la caja.");
    }
}
