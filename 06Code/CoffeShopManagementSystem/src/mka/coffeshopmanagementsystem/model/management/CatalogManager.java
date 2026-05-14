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
import mka.coffeshopmanagementsystem.model.persistence.JsonFileManager;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class CatalogManager {
    private List<Product> products;
    private String dataFilePath;
    private final JsonFileManager jsonFileManager;

    public CatalogManager() {
        this.jsonFileManager = new JsonFileManager();
        this.products = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getDataFilePath() {
        return dataFilePath;
    }

    public void setDataFilePath(String dataFilePath) {
        this.dataFilePath = dataFilePath;
    }

    public void addProduct(Product p) {
        if (this.products == null) {
            this.products = new ArrayList<>();
        }
        this.products.add(p);
    }

    public void removeProduct(String id) {
        if (this.products != null) {
            this.products.removeIf(p -> p.getProductId().equals(id));
        }
    }

    public void loadData() {
        Type listType = new TypeToken<ArrayList<Product>>(){}.getType();
        List<Product> loadedProducts = jsonFileManager.loadFromFile(dataFilePath, listType);
        if (loadedProducts != null) {
            this.products = loadedProducts;
        } else {
            this.products = new ArrayList<>();
        }
    }

    public void saveData() {
        jsonFileManager.saveToFile(dataFilePath, products);
    }
}
