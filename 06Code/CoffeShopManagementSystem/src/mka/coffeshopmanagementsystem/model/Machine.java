/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

/**
 *
 * @author Anthony Aimacaña
 */
public class Machine {
    private String id;
    private String brand;
    private String model;
    private boolean state;

    public Machine(String id, String brand, String model) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.state = false;
    }

    public void turnOn() {
        this.state = true;
    }

    public void turnOff() {
        this.state = false;
    }

    public void operate() {
        // Implementation logic
    }
}