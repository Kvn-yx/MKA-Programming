/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espe.coffeshopp.model;

/**
 *
 * @author Mateo Artieda,MKA Programer, @ESPE
 */


import java.util.ArrayList;

public class Product {

    private String name;
    private double price;
    private ArrayList<ProductIngredient> ingredients;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
        this.ingredients = new ArrayList<>();
    }

    public void addIngredient(ProductIngredient ingredient) {
        ingredients.add(ingredient);
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
