/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espe.coffeshopp.model;

/**
 *
 * @author Mateo Artieda,MKA Programer, @ESPE
 */

public class Cashier extends Employee {

    public Cashier(int id, String name, String email, double salary) {
        super(id, name, email, salary);
    }

    @Override
    public void performOperation() {
        System.out.println("Cashier cobrando orden");
    }
}
