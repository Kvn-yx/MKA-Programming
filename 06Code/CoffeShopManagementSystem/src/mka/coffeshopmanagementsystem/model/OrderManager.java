/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class OrderManager {
    private List<Order> orders;
    private PaymentProcessor defaultProcessor;

    public OrderManager(PaymentProcessor defaultProcessor) {
        this.orders = new ArrayList<>();
        this.defaultProcessor = defaultProcessor;
    }

    public Order createOrder(Customer customer, String orderId) {
        Order order = new Order(orderId);
        order.setCustomer(customer);
        orders.add(order);
        return order;
    }

    public boolean processPayment(Order order, Payment payment) {
        boolean success = payment.processPayment(defaultProcessor);
        if (success) {
            order.setPayment(payment);
            order.updateStatus(OrderStatus.PAID);
        }
        return success;
    }
}

