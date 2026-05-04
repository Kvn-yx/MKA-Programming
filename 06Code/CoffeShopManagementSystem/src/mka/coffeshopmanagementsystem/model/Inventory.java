/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Anthony Aimacaña
 */
public class Inventory {
    private List<Ingredient> ingredients;
    private Date lastReviewDate;

    public Inventory() {
        this.ingredients = new ArrayList<>();
        this.lastReviewDate = new Date();
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public boolean checkAvailability(Product product, int quantity) {
        // Logic to check availability
        return true;
    }

    public void reduceStock(Product product, int quantity) {
        // Logic to reduce stock
    }
}