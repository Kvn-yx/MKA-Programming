/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.floor;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class Machine {
    private String id;
    private String brand;
    private boolean state;

    public Machine() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void turnOn() {
        this.state = true;
        System.out.println(String.format(mka.coffeshopmanagementsystem.utils.I18n.getString("model.machine.on"), brand, id));
    }

    public void operate() {
        if (state) {
            System.out.println(String.format(mka.coffeshopmanagementsystem.utils.I18n.getString("model.machine.operating"), brand));
        } else {
            System.out.println(String.format(mka.coffeshopmanagementsystem.utils.I18n.getString("model.machine.err_off"), brand));
        }
    }
}
