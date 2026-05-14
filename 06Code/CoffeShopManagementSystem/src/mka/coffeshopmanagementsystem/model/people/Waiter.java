/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.people;

import mka.coffeshopmanagementsystem.model.floor.Table;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class Waiter extends Employee {
    public Waiter() {
        super();
    }

    public Waiter(String id, String name) {
        super(id, name, "Waiter");
    }

    public void serveTable(Table table) {
        System.out.println(String.format(mka.coffeshopmanagementsystem.utils.I18n.getString("model.waiter.serve"), getName(), table.getId()));
    }

    @Override
    public void performOperation() {
        System.out.println(String.format(mka.coffeshopmanagementsystem.utils.I18n.getString("model.waiter.op"), getName()));
    }
}
