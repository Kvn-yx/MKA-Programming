/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class Product {
    private String productId;
    private String name;
    private BigDecimal price;
    private ProductCategory category;
    private List<ProductIngredient> recipe;

    public Product(String productId, String name, BigDecimal price, ProductCategory category, List<ProductIngredient> recipe) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.recipe = recipe;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Map<Ingredient, BigDecimal> getRequiredIngredients() {
        Map<Ingredient, BigDecimal> map = new HashMap<>();
        for (ProductIngredient pi : recipe) {
            map.put(pi.getIngredient(), pi.getQuantityNeeded());
        }
        return map;
    }
}

