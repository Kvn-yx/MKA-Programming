/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espe.coffeshopp.model;

/**
 *
 * @author Mateo Artieda,MKA Programer, @ESPE
 */


public class Employee extends Person {

    protected double salary;

    public Employee(int id, String name, String email, double salary) {
        super(id, name, email);
        this.salary = salary;
    }

    public void performOperation() {
        System.out.println(name + " trabajando...");
    }
}
