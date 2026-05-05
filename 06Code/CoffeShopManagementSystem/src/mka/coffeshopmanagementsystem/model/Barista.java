/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class Barista extends Employee {
    public Barista(String id, String name) {
        super(id, name, "Barista");
    }

    public void prepareDrink() {
        System.out.println(getName() + " is preparing a drink.");
    }

    @Override
    public void performOperation() {
        prepareDrink();
    }
}

