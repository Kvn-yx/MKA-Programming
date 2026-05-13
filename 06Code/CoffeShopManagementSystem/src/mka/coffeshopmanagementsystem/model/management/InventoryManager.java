/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.management;

import java.math.BigDecimal;
import java.util.Map;
import mka.coffeshopmanagementsystem.model.inventory.Inventory;
import mka.coffeshopmanagementsystem.model.inventory.Ingredient;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class InventoryManager {
    private Inventory inventory;
    private String dataFilePath;

    public InventoryManager() {
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getDataFilePath() {
        return dataFilePath;
    }

    public void setDataFilePath(String dataFilePath) {
        this.dataFilePath = dataFilePath;
    }

    public boolean checkStockFor(Map<Ingredient, BigDecimal> requiredIngredients) {
        // TODO: implement
        return false;
    }

    public void deductStockFor(Map<Ingredient, BigDecimal> requiredIngredients) {
        // TODO: implement
    }

    public void overrideStock(Ingredient ingredient, BigDecimal actualQuantity) {
        // TODO: implement
    }

    public void loadData() {
        // TODO: implement
    }

    public void saveData() {
        // TODO: implement
    }
}
