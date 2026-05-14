/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.people;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class Barista extends Employee {
    public Barista() {
        super();
    }

    public Barista(String id, String name) {
        super(id, name, "Barista");
    }

    public void prepareDrink() {
        System.out.println("Barista " + getName() + " está preparando una bebida de especialidad.");
    }

    @Override
    public void performOperation() {
        System.out.println("Barista " + getName() + " está operando la máquina de café y sirviendo bebidas.");
    }
}
