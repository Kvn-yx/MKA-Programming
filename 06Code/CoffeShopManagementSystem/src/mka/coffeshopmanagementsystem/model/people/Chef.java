/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.people;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class Chef extends Employee {
    public Chef() {
        super();
    }

    public Chef(String id, String name) {
        super(id, name, "Chef");
    }

    @Override
    public void performOperation() {
        System.out.println("Chef " + getName() + " está preparando los platillos del menú.");
    }
}
