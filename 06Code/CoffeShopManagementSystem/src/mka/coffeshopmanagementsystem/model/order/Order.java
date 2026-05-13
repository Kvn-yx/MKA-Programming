/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import mka.coffeshopmanagementsystem.model.people.Customer;
import mka.coffeshopmanagementsystem.model.payment.Payment;

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
    private boolean isSynced;

    public Order() {
    }

    public Order(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(boolean isSynced) {
        this.isSynced = isSynced;
    }

    public void addItem(OrderItem item) {
        // TODO: implement
    }

    public BigDecimal calculateSubtotal() {
        // TODO: implement
        return null;
    }

    public BigDecimal calculateTax() {
        // TODO: implement
        return null;
    }

    public BigDecimal calculateTotal() {
        // TODO: implement
        return null;
    }

    public void updateStatus(OrderStatus status) {
        // TODO: implement
    }

    public void markAsSynced() {
        // TODO: implement
    }
}
