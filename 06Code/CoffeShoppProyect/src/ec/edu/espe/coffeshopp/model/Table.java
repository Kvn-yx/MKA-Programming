/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espe.coffeshopp.model;

/**
 *
 * @author Mateo Artieda,MKA Programer, @ESPE
 */


public class Table {

    private int tableNumber;
    private boolean occupied;

    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
        this.occupied = false;
    }

    public void assign() {
        occupied = true;
    }
}
