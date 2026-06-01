/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import mka.coffeshopmanagementsystem.model.inventory.Product;
import mka.coffeshopmanagementsystem.model.persistence.repository.IRepository;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class CatalogManager {
    private Map<String, Product> productMap;
    private IRepository<Product> productRepository;

    public CatalogManager(IRepository<Product> productRepository) {
        this.productRepository = productRepository;
        this.productMap = new HashMap<>();
    }

    public List<Product> getProducts() {
        return new ArrayList<>(productMap.values());
    }

    public void addProduct(Product p) {
        if (p != null && p.getProductId() != null) {
            this.productMap.put(p.getProductId(), p);
        }
    }

    public void removeProduct(String id) {
        if (id != null && productMap.containsKey(id.trim())) {
            productMap.remove(id.trim());
        } else {
            throw new IllegalArgumentException(mka.coffeshopmanagementsystem.utils.I18n.getString("cat.notFound"));
        }
    }

    public void updateProductPrice(String id, java.math.BigDecimal newPrice) {
        Product p = productMap.get(id != null ? id.trim() : null);
        if (p != null) {
            p.setPrice(newPrice);
        } else {
             throw new IllegalArgumentException(mka.coffeshopmanagementsystem.utils.I18n.getString("cat.notFound"));
        }
    }

    public void updateProductRecipe(String id, List<mka.coffeshopmanagementsystem.model.inventory.ProductIngredient> newRecipe) {
        Product p = productMap.get(id != null ? id.trim() : null);
        if (p != null) {
            p.setRecipe(newRecipe);
        } else {
             throw new IllegalArgumentException(mka.coffeshopmanagementsystem.utils.I18n.getString("cat.notFound"));
        }
    }

    public void loadData() {
        if (productRepository != null) {
            List<Product> products = productRepository.findAll();
            this.productMap = products.stream()
                    .collect(Collectors.toMap(Product::getProductId, p -> p, (existing, replacement) -> replacement, HashMap::new));
        } else {
             throw new IllegalStateException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.repo.err_product"));
        }
    }

    public void saveData() {
        if (productRepository != null) {
            productRepository.saveAll(new ArrayList<>(productMap.values()));
        } else {
             throw new IllegalStateException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.repo.err_product"));
        }
    }
}
