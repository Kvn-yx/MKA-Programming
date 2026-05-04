/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

/**
 *
 * @author Anthony Aimacaña
 */
public class OrderItem {
    private String orderItemId;
    private int quantity;
    private double subtotal;
    private Product product;

    public OrderItem(String orderItemId, Product product, int quantity) {
        this.orderItemId = orderItemId;
        this.product = product;
        this.quantity = quantity;
        this.subtotal = calculateSubtotal();
    }

    public double calculateSubtotal() {
        if (product != null) {
            return product.getPrice() * quantity;
        }
        return 0;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}