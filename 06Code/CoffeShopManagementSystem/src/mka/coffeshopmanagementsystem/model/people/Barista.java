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
        System.out.println(String.format(mka.coffeshopmanagementsystem.utils.I18n.getString("model.barista.prep"), getName()));
    }

    @Override
    public void performOperation() {
        System.out.println(String.format(mka.coffeshopmanagementsystem.utils.I18n.getString("model.barista.op"), getName()));
    }
}
