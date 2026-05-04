/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

/**
 *
 * @author Anthony Aimacaña
 */
public class Ingredient {
    private String ingredientId;
    private String name;
    private double stockQuantity;

    public Ingredient(String ingredientId, String name, double stockQuantity) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.stockQuantity = stockQuantity;
    }

    public double getStockQuantity() {
        return stockQuantity;
    }

    public void reduceStock(double amount) {
        this.stockQuantity -= amount;
    }
}