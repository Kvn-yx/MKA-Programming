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
import mka.coffeshopmanagementsystem.model.persistence.repository.ISingleRepository;
import mka.coffeshopmanagementsystem.model.persistence.repository.JsonSingleRepository;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class InventoryManager {
    private Inventory inventory;
    private ISingleRepository<Inventory> inventoryRepository;

    public InventoryManager() {
        this.inventory = new Inventory();
        this.inventory.setIngredients(new ArrayList<>());
    }

    public InventoryManager(ISingleRepository<Inventory> inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
        this.inventory = new Inventory();
        this.inventory.setIngredients(new ArrayList<>());
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setDataFilePath(String dataFilePath) {
        this.inventoryRepository = new JsonSingleRepository<>(dataFilePath, Inventory.class);
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

    public Ingredient findIngredientByName(String name) {
        if (inventory == null || inventory.getIngredients() == null || name == null) return null;
        return inventory.getIngredients().stream()
                .filter(i -> i.getName().equalsIgnoreCase(name.trim()))
                .findFirst()
                .orElse(null);
    }

    public void addIngredient(Ingredient ingredient) {
        if (ingredient != null) {
            java.util.List<Ingredient> currentList = new ArrayList<>(inventory.getIngredients());
            currentList.add(ingredient);
            inventory.setIngredients(currentList);
        }
    }

    public void updateIngredientStock(String id, BigDecimal actualQuantity) {
        Ingredient inStock = findIngredient(id);
        if (inStock != null) {
            inStock.updateStock(actualQuantity);
        } else {
            throw new IllegalArgumentException(mka.coffeshopmanagementsystem.utils.I18n.getString("inv.notFound"));
        }
    }

    public void removeIngredient(String id) {
        java.util.List<Ingredient> currentList = new ArrayList<>(inventory.getIngredients());
        boolean removed = currentList.removeIf(i -> i.getIngredientId().equals(id.trim()));
        if (removed) {
            inventory.setIngredients(currentList);
        } else {
            throw new IllegalArgumentException(mka.coffeshopmanagementsystem.utils.I18n.getString("inv.notFound"));
        }
    }

    public void loadData() {
        if (inventoryRepository != null) {
            Inventory loadedInventory = inventoryRepository.load();
            if (loadedInventory != null) {
                this.inventory = loadedInventory;
                if (this.inventory.getIngredients() == null) {
                    this.inventory.setIngredients(new ArrayList<>());
                }
            } else {
                this.inventory = new Inventory();
                this.inventory.setIngredients(new ArrayList<>());
            }
        } else {
            throw new IllegalStateException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.repo.err_inventory"));
        }
    }

    public void saveData() {
        if (inventoryRepository != null) {
            inventoryRepository.save(inventory);
        } else {
            throw new IllegalStateException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.repo.err_inventory"));
        }
    }
}
