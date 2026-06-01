/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.management;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import mka.coffeshopmanagementsystem.model.inventory.Inventory;
import mka.coffeshopmanagementsystem.model.inventory.Ingredient;
import mka.coffeshopmanagementsystem.model.persistence.repository.ISingleRepository;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class InventoryManager {
    private Inventory inventory;
    private Map<String, Ingredient> ingredientMap;
    private ISingleRepository<Inventory> inventoryRepository;

    public InventoryManager(ISingleRepository<Inventory> inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
        this.inventory = new Inventory();
        this.inventory.setIngredients(new ArrayList<>());
        this.ingredientMap = new HashMap<>();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
        syncMapFromInventory();
    }

    private void syncMapFromInventory() {
        if (inventory != null && inventory.getIngredients() != null) {
            this.ingredientMap = inventory.getIngredients().stream()
                    .collect(Collectors.toMap(Ingredient::getIngredientId, i -> i, (existing, replacement) -> replacement, HashMap::new));
        } else {
            this.ingredientMap = new HashMap<>();
        }
    }

    public boolean checkStockFor(Map<Ingredient, BigDecimal> requiredIngredients) {
        if (requiredIngredients == null) return false;
        
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
        if (requiredIngredients == null) return;

        for (Map.Entry<Ingredient, BigDecimal> entry : requiredIngredients.entrySet()) {
            Ingredient required = entry.getKey();
            BigDecimal amountToDeduct = entry.getValue();
            
            Ingredient inStock = findIngredient(required.getIngredientId());
            if (inStock != null) {
                inStock.reduceStock(amountToDeduct);
            }
        }
    }

    public Ingredient findIngredient(String id) {
        return ingredientMap.get(id);
    }

    public Ingredient findIngredientByName(String name) {
        if (name == null) return null;
        String trimmedName = name.trim();
        return ingredientMap.values().stream()
                .filter(i -> i.getName().equalsIgnoreCase(trimmedName))
                .findFirst()
                .orElse(null);
    }

    public void addIngredient(Ingredient ingredient) {
        if (ingredient != null && ingredient.getIngredientId() != null) {
            ingredientMap.put(ingredient.getIngredientId(), ingredient);
            updateInventoryList();
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
        if (id != null && ingredientMap.containsKey(id.trim())) {
            ingredientMap.remove(id.trim());
            updateInventoryList();
        } else {
            throw new IllegalArgumentException(mka.coffeshopmanagementsystem.utils.I18n.getString("inv.notFound"));
        }
    }

    private void updateInventoryList() {
        if (this.inventory != null) {
            this.inventory.setIngredients(new ArrayList<>(ingredientMap.values()));
        }
    }

    public void loadData() {
        if (inventoryRepository != null) {
            Inventory loadedInventory = inventoryRepository.load();
            if (loadedInventory != null) {
                this.inventory = loadedInventory;
            } else {
                this.inventory = new Inventory();
                this.inventory.setIngredients(new ArrayList<>());
            }
            syncMapFromInventory();
        } else {
            throw new IllegalStateException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.repo.err_inventory"));
        }
    }

    public void saveData() {
        if (inventoryRepository != null) {
            updateInventoryList();
            inventoryRepository.save(inventory);
        } else {
            throw new IllegalStateException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.repo.err_inventory"));
        }
    }
}
