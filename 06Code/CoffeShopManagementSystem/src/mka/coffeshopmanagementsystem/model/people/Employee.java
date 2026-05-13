/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.people;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public abstract class Employee extends Person {
    private String role;

    public Employee() {
        super();
    }

    public Employee(String id, String name, String role) {
        super(id, name);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public abstract void performOperation();
}
