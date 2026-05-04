/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

/**
 *
 * @author Anthony Aimacaña
 */
public class ProductIngredient {
    private double quantityNeeded;
    private Ingredient ingredient;

    public ProductIngredient(Ingredient ingredient, double quantityNeeded) {
        this.ingredient = ingredient;
        this.quantityNeeded = quantityNeeded;
    }

    public double getQuantityNeeded() {
        return quantityNeeded;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }
}