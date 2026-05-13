/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espe.coffeshopp.model;

/**
 *
 * @author Mateo Artieda,MKA Programer, @ESPE
 */


public class Machine {

    private String brand;
    private boolean status;

    public Machine(String brand) {
        this.brand = brand;
        this.status = true;
    }

    public void turnOn() {
        status = true;
    }

    public void turnOff() {
        status = false;
    }
}
