/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espe.coffeshopp.model;

/**
 *
 * @author Mateo Artieda,MKA Programer, @ESPE
 */


public class FloorManager {

    public void assignTable(Waiter waiter, Table table) {
        table.assign();
        System.out.println("Mesa asignada al waiter");
    }
}