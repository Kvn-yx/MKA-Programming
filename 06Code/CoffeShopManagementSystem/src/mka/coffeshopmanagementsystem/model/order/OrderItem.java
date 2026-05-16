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
        if (quantity <= 0) {
            throw new IllegalArgumentException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.orderitem.err_qty"));
        }
        this.quantity = quantity;
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
        if (this.product != null && this.product.getPrice() != null) {
            return this.product.getPrice().multiply(new BigDecimal(this.quantity));
        }
        return BigDecimal.ZERO;
    }

    public void addModifier(String modifier) {
        if (modifier == null || modifier.trim().isEmpty()) {
            throw new IllegalArgumentException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.orderitem.err_mod"));
        }
        if (this.modifiers == null) {
            this.modifiers = new java.util.ArrayList<>();
        }
        this.modifiers.add(modifier);
    }
}
