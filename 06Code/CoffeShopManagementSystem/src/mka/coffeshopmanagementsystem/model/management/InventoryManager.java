/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.management;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import mka.coffeshopmanagementsystem.model.inventory.Inventory;
import mka.coffeshopmanagementsystem.model.inventory.Ingredient;
import mka.coffeshopmanagementsystem.model.persistence.JsonFileManager;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class InventoryManager {
    private Inventory inventory;
    private String dataFilePath;
    private final JsonFileManager jsonFileManager;

    public InventoryManager() {
        this.jsonFileManager = new JsonFileManager();
        this.inventory = new Inventory();
        this.inventory.setIngredients(new ArrayList<>());
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
        if (inventory == null || inventory.getIngredients() == null || requiredIngredients == null) return false;
        
        for (Map.Entry<Ingredient, BigDecimal> entry : requiredIngredients.entrySet()) {
            Ingredient required = entry.getKey();
            BigDecimal amountNeeded = entry.getValue();
            
            Ingredient inStock = findIngredient(required.getIngredientId());
            if (inStock == null || inStock.getStockQuantity().compareTo(amountNeeded) < 0) {
                return false;
            }
        }
        return true;
    }

    public void deductStockFor(Map<Ingredient, BigDecimal> requiredIngredients) {
        if (inventory == null || inventory.getIngredients() == null || requiredIngredients == null) return;

        for (Map.Entry<Ingredient, BigDecimal> entry : requiredIngredients.entrySet()) {
            Ingredient required = entry.getKey();
            BigDecimal amountToDeduct = entry.getValue();
            
            Ingredient inStock = findIngredient(required.getIngredientId());
            if (inStock != null) {
                inStock.reduceStock(amountToDeduct);
            }
        }
    }

    private Ingredient findIngredient(String id) {
        if (inventory == null || inventory.getIngredients() == null) return null;
        return inventory.getIngredients().stream()
                .filter(i -> i.getIngredientId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void overrideStock(Ingredient ingredient, BigDecimal actualQuantity) {
        if (ingredient == null) return;
        Ingredient inStock = findIngredient(ingredient.getIngredientId());
        if (inStock != null) {
            inStock.updateStock(actualQuantity);
        } else {
            ingredient.setStockQuantity(actualQuantity);
            if (inventory.getIngredients() == null) {
                inventory.setIngredients(new ArrayList<>());
            }
            inventory.getIngredients().add(ingredient);
        }
    }

    public void loadData() {
        Inventory loadedInventory = jsonFileManager.loadFromFile(dataFilePath, Inventory.class);
        if (loadedInventory != null) {
            this.inventory = loadedInventory;
            if (this.inventory.getIngredients() == null) {
                this.inventory.setIngredients(new ArrayList<>());
            }
        } else {
            this.inventory = new Inventory();
            this.inventory.setIngredients(new ArrayList<>());
        }
    }

    public void saveData() {
        jsonFileManager.saveToFile(dataFilePath, inventory);
    }
}
