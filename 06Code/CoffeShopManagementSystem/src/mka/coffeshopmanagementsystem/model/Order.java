/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class Order {
    private String orderId;
    private LocalDateTime dateTime;
    private OrderStatus status;
    private List<OrderItem> items;
    private Customer customer;
    private Payment payment;
    private BigDecimal taxRate;
    private BigDecimal discount;

    public Order(String orderId) {
        this.orderId = orderId;
        this.dateTime = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
        this.items = new ArrayList<>();
        this.taxRate = BigDecimal.ZERO;
        this.discount = BigDecimal.ZERO;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
    }

    public BigDecimal calculateSubtotal() {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (OrderItem item : items) {
            subtotal = subtotal.add(item.getSubtotal());
        }
        return subtotal;
    }

    public BigDecimal calculateTax() {
        return calculateSubtotal().subtract(discount).multiply(taxRate);
    }

    public BigDecimal calculateTotal() {
        return calculateSubtotal().subtract(discount).add(calculateTax());
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}

