/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class Waiter extends Employee {
    public Waiter(String id, String name) {
        super(id, name, "Waiter");
    }

    public void serveTable(Table table) {
        System.out.println(getName() + " is serving table " + table.getId());
    }

    @Override
    public void performOperation() {
        System.out.println(getName() + " is taking orders to tables.");
    }
}

