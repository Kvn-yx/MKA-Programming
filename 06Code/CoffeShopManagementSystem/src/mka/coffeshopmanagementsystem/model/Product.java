/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Anthony Aimacaña
 */
public class Product {
    private String productId;
    private String name;
    private double price;
    private List<ProductIngredient> recipe;

    public Product(String productId, String name, double price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.recipe = new ArrayList<>();
    }

    public double getPrice() {
        return price;
    }

    public void addIngredient(ProductIngredient pi) {
        this.recipe.add(pi);
    }

    public List<ProductIngredient> getRecipe() {
        return recipe;
    }
}