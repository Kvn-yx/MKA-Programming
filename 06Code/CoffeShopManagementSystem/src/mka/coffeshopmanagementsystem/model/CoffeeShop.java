/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class CoffeeShop {
    private String name;
    private String address;
    
    private OrderManager orderManager;
    private HRManager hrManager;
    private FloorManager floorManager;
    private InventoryManager inventoryManager;

    public CoffeeShop(String name, String address, OrderManager om, HRManager hrm, FloorManager fm, InventoryManager im) {
        this.name = name;
        this.address = address;
        this.orderManager = om;
        this.hrManager = hrm;
        this.floorManager = fm;
        this.inventoryManager = im;
    }
}

