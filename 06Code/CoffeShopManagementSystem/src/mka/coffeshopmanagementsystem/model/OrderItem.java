/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

import java.math.BigDecimal;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class OrderItem {
    private String orderItemId;
    private Product product;
    private int quantity;
    private BigDecimal subtotal;

    public OrderItem(String orderItemId, Product product, int quantity) {
        this.orderItemId = orderItemId;
        this.product = product;
        this.quantity = quantity;
        this.subtotal = calculateSubtotal();
    }

    public BigDecimal calculateSubtotal() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
    
    public BigDecimal getSubtotal() {
        return subtotal;
    }
}

