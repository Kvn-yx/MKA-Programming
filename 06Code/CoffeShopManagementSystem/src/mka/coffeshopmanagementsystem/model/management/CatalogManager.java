/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.management;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import mka.coffeshopmanagementsystem.model.inventory.Product;
import mka.coffeshopmanagementsystem.model.persistence.repository.IRepository;
import mka.coffeshopmanagementsystem.model.persistence.repository.JsonRepository;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class CatalogManager {
    private List<Product> products;
    private IRepository<Product> productRepository;

    public CatalogManager() {
        this.products = new ArrayList<>();
    }

    public CatalogManager(IRepository<Product> productRepository) {
        this.productRepository = productRepository;
        this.products = new ArrayList<>();
    }

    public List<Product> getProducts() {
        if (products == null) {
            return java.util.Collections.emptyList();
        }
        return java.util.Collections.unmodifiableList(products);
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setDataFilePath(String dataFilePath) {
        Type listType = new TypeToken<ArrayList<Product>>(){}.getType();
        this.productRepository = new JsonRepository<>(dataFilePath, listType);
    }

    public void addProduct(Product p) {
        if (p != null) {
            List<Product> currentProducts = new ArrayList<>(getProducts());
            currentProducts.add(p);
            this.products = currentProducts;
        }
    }

    public void removeProduct(String id) {
        List<Product> currentProducts = new ArrayList<>(getProducts());
        boolean removed = currentProducts.removeIf(p -> p.getProductId().equals(id.trim()));
        if (removed) {
            this.products = currentProducts;
        } else {
            throw new IllegalArgumentException(mka.coffeshopmanagementsystem.utils.I18n.getString("cat.notFound"));
        }
    }

    public void updateProductPrice(String id, java.math.BigDecimal newPrice) {
        Product p = getProducts().stream().filter(pr -> pr.getProductId().equals(id.trim())).findFirst().orElse(null);
        if (p != null) {
            p.setPrice(newPrice);
        } else {
             throw new IllegalArgumentException(mka.coffeshopmanagementsystem.utils.I18n.getString("cat.notFound"));
        }
    }

    public void updateProductRecipe(String id, List<mka.coffeshopmanagementsystem.model.inventory.ProductIngredient> newRecipe) {
        Product p = getProducts().stream().filter(pr -> pr.getProductId().equals(id.trim())).findFirst().orElse(null);
        if (p != null) {
            p.setRecipe(newRecipe);
        } else {
             throw new IllegalArgumentException(mka.coffeshopmanagementsystem.utils.I18n.getString("cat.notFound"));
        }
    }

    public void loadData() {
        if (productRepository != null) {
            this.products = productRepository.findAll();
        } else {
             throw new IllegalStateException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.repo.err_product"));
        }
    }

    public void saveData() {
        if (productRepository != null) {
            productRepository.saveAll(products);
        } else {
             throw new IllegalStateException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.repo.err_product"));
        }
    }
}
