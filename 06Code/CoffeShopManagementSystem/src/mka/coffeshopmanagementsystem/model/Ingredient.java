/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

import java.math.BigDecimal;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class Ingredient {
    private String ingredientId;
    private String name;
    private BigDecimal stockQuantity;

    public Ingredient(String ingredientId, String name, BigDecimal stockQuantity) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.stockQuantity = stockQuantity;
    }

    public void reduceStock(BigDecimal amount) {
        this.stockQuantity = this.stockQuantity.subtract(amount);
    }

    public BigDecimal getStockQuantity() {
        return stockQuantity;
    }
}

