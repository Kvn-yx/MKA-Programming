/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class InventoryManager {
    private Inventory inventory;

    public InventoryManager(Inventory inventory) {
        this.inventory = inventory;
    }

    public boolean checkStockFor(Map<Ingredient, BigDecimal> requiredIngredients) {
        for (Map.Entry<Ingredient, BigDecimal> entry : requiredIngredients.entrySet()) {
            if (entry.getKey().getStockQuantity().compareTo(entry.getValue()) < 0) {
                return false;
            }
        }
        return true;
    }

    public void deductStockFor(Map<Ingredient, BigDecimal> requiredIngredients) {
        for (Map.Entry<Ingredient, BigDecimal> entry : requiredIngredients.entrySet()) {
            entry.getKey().reduceStock(entry.getValue());
        }
    }
}

