/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.inventory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Product() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.product.err_price"));
        }
        this.price = price;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public List<ProductIngredient> getRecipe() {
        if (recipe == null) {
            return java.util.Collections.emptyList();
        }
        return java.util.Collections.unmodifiableList(recipe);
    }

    public void setRecipe(List<ProductIngredient> recipe) {
        this.recipe = recipe;
    }

    public Map<Ingredient, BigDecimal> getRequiredIngredients() {
        Map<Ingredient, BigDecimal> ingredientsMap = new HashMap<>();
        if (recipe != null) {
            for (ProductIngredient productIngredient : recipe) {
                ingredientsMap.put(productIngredient.getIngredient(), productIngredient.getQuantityNeeded());
            }
        }
        return ingredientsMap;
    }
}
