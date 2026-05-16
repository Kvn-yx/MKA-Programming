/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.inventory;

import java.math.BigDecimal;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class Ingredient {
    private String ingredientId;
    private String name;
    private BigDecimal stockQuantity;
    private String unit;

    public Ingredient() {
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(BigDecimal stockQuantity) {
        if (stockQuantity == null || stockQuantity.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.inventory.err_qty_null"));
        }
        this.stockQuantity = stockQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void reduceStock(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.inventory.err_reduce_null"));
        }
        if (this.stockQuantity == null) {
             throw new IllegalStateException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.inventory.err_stock_null"));
        }
        if (this.stockQuantity.compareTo(amount) < 0) {
            throw new IllegalStateException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.inventory.err_insufficient"));
        }
        this.stockQuantity = this.stockQuantity.subtract(amount);
    }

    public void updateStock(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.inventory.err_new_null"));
        }
        this.stockQuantity = amount;
    }
}
