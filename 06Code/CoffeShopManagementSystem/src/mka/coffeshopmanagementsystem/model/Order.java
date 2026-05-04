/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Anthony Aimacaña
 */
public class Order {
    private String orderId;
    private Date dateTime;
    private OrderStatus status;
    private List<OrderItem> items;
    private Customer customer;
    private Payment payment;

    public Order(String orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.dateTime = new Date();
        this.status = OrderStatus.PENDING;
        this.items = new ArrayList<>();
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
    }

    public double calculateTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.calculateSubtotal();
        }
        return total;
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}