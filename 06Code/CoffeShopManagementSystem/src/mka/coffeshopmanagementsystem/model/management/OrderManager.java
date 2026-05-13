/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.management;

import java.util.List;
import mka.coffeshopmanagementsystem.model.order.Order;
import mka.coffeshopmanagementsystem.model.people.Customer;
import mka.coffeshopmanagementsystem.model.payment.Payment;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class OrderManager {
    private List<Order> orders;
    private String dataFilePath;

    public OrderManager() {
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getDataFilePath() {
        return dataFilePath;
    }

    public void setDataFilePath(String dataFilePath) {
        this.dataFilePath = dataFilePath;
    }

    public Order createOrder(Customer customer) {
        // TODO: implement
        return null;
    }

    public boolean processPayment(Order order, Payment payment) {
        // TODO: implement
        return false;
    }

    public void syncOfflineOrders() {
        // TODO: implement
    }

    public void loadData() {
        // TODO: implement
    }

    public void saveData() {
        // TODO: implement
    }
}
