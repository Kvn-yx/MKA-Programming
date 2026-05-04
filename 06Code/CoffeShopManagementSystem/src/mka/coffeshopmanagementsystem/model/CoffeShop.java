/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Anthony Aimacaña
 */
public class CoffeShop {
    private String name;
    private String address;
    private List<Employee> employees;
    private List<Machine> machines;
    private List<Table> tables;
    private List<Order> orders;
    private Inventory inventory;

    public CoffeShop(String name, String address) {
        this.name = name;
        this.address = address;
        this.employees = new ArrayList<>();
        this.machines = new ArrayList<>();
        this.tables = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.inventory = new Inventory();
    }

    public void manageOrders() {
        // Implementation logic
    }

    public void manageInventory() {
        // Implementation logic
    }
}