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
public class ProductIngredient {
    private Ingredient ingredient;
    private BigDecimal quantityNeeded;

    public ProductIngredient(Ingredient ingredient, BigDecimal quantityNeeded) {
        this.ingredient = ingredient;
        this.quantityNeeded = quantityNeeded;
    }

    public BigDecimal getQuantityNeeded() {
        return quantityNeeded;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }
}

