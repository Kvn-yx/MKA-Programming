/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.management;

import java.util.List;
import mka.coffeshopmanagementsystem.model.inventory.Product;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class CatalogManager {
    private List<Product> products;
    private String dataFilePath;

    public CatalogManager() {
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
        // TODO: implement
    }

    public void removeProduct(String id) {
        // TODO: implement
    }

    public void loadData() {
        // TODO: implement
    }

    public void saveData() {
        // TODO: implement
    }
}
