/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espe.coffeshopp.model;

/**
 *
 * @author Mateo Artieda,MKA Programer, @ESPE
 */


public class Ingredient {

    private String name;
    private double stock;

    public Ingredient(String name, double stock) {
        this.name = name;
        this.stock = stock;
    }

    public void reduceStock(double amount) {
        stock -= amount;
    }

    public String getName() {
        return name;
    }
}
