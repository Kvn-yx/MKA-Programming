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
        if (this.items == null) {
            this.items = new java.util.ArrayList<>();
        }
        this.items.add(item);
    }

    public BigDecimal calculateSubtotal() {
        BigDecimal total = BigDecimal.ZERO;
        if (this.items != null) {
            for (OrderItem item : this.items) {
                BigDecimal itemSubtotal = item.calculateSubtotal();
                if (itemSubtotal != null) {
                    total = total.add(itemSubtotal);
                }
            }
        }
        return total;
    }

    public BigDecimal calculateTax() {
        BigDecimal subtotal = calculateSubtotal();
        if (this.taxRate != null && subtotal != null) {
            return subtotal.multiply(this.taxRate);
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal calculateTotal() {
        BigDecimal subtotal = calculateSubtotal();
        BigDecimal tax = calculateTax();
        BigDecimal total = subtotal.add(tax);
        if (this.discount != null) {
            total = total.subtract(this.discount);
        }
        if (total.compareTo(BigDecimal.ZERO) < 0) {
            total = BigDecimal.ZERO;
        }
        return total;
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

    public void markAsSynced() {
        this.isSynced = true;
    }
}
