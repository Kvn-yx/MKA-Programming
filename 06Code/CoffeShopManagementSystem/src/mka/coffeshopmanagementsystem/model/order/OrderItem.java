/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.order;

import java.math.BigDecimal;
import java.util.List;
import mka.coffeshopmanagementsystem.model.inventory.Product;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class OrderItem {
    private String orderItemId;
    private int quantity;
    private BigDecimal subtotal;
    private Product product;
    private List<String> modifiers;

    public OrderItem() {
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<String> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<String> modifiers) {
        this.modifiers = modifiers;
    }

    public BigDecimal calculateSubtotal() {
        // TODO: implement
        return null;
    }

    public void addModifier(String modifier) {
        // TODO: implement
    }
}
