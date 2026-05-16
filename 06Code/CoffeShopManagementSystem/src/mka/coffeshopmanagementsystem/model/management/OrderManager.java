/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.management;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import mka.coffeshopmanagementsystem.model.order.Order;
import mka.coffeshopmanagementsystem.model.order.OrderStatus;
import mka.coffeshopmanagementsystem.model.people.Customer;
import mka.coffeshopmanagementsystem.model.payment.Payment;
import mka.coffeshopmanagementsystem.model.payment.Cash;
import mka.coffeshopmanagementsystem.model.payment.CreditCard;
import mka.coffeshopmanagementsystem.model.payment.Transfer;
import mka.coffeshopmanagementsystem.model.payment.PaymentProcessor;
import mka.coffeshopmanagementsystem.model.persistence.repository.IRepository;
import mka.coffeshopmanagementsystem.model.persistence.repository.JsonRepository;
import java.time.LocalDateTime;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class OrderManager {
    private List<Order> orders;
    private IRepository<Order> orderRepository;

    public OrderManager() {
        this.orders = new ArrayList<>();
    }

    public OrderManager(IRepository<Order> orderRepository) {
        this.orderRepository = orderRepository;
        this.orders = new ArrayList<>();
    }

    public List<Order> getOrders() {
        if (orders == null) {
            return java.util.Collections.emptyList();
        }
        return java.util.Collections.unmodifiableList(orders);
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setDataFilePath(String dataFilePath) {
        Type listType = new TypeToken<ArrayList<Order>>(){}.getType();
        this.orderRepository = new JsonRepository<>(dataFilePath, listType);
    }

    public Order createOrder(Customer customer) {
        Order newOrder = new Order(java.util.UUID.randomUUID().toString());
        newOrder.setCustomer(customer);
        newOrder.setDateTime(LocalDateTime.now());
        newOrder.setStatus(OrderStatus.PENDING);
        newOrder.setSynced(false);
        
        if (this.orders == null) {
            this.orders = new ArrayList<>();
        }
        this.orders.add(newOrder);
        return newOrder;
    }

    public boolean processPayment(Order order, Payment payment) {
        if (order != null && payment != null) {
            PaymentProcessor defaultProcessor = new PaymentProcessor() {
                @Override
                public boolean processCash(Cash cash) {
                    // Logic specific to cash, like verifying amountTendered > total
                    return true;
                }
                
                @Override
                public boolean processCreditCard(CreditCard creditCard) {
                    // Logic specific to credit card, like external API calls
                    return true;
                }
                
                @Override
                public boolean processTransfer(Transfer transfer) {
                    // Logic specific to transfer, like verifying account
                    return true;
                }
            };
            boolean success = payment.processPayment(defaultProcessor);
            if (success) {
                order.setPayment(payment);
                order.updateStatus(OrderStatus.PAID);
            }
            return success;
        }
        return false;
    }

    public void syncOfflineOrders() {
        if (this.orders != null) {
            for (Order order : this.orders) {
                if (!order.isSynced()) {
                    order.markAsSynced();
                }
            }
        }
    }

    public void loadData() {
        if (orderRepository != null) {
            this.orders = orderRepository.findAll();
        } else {
            throw new IllegalStateException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.repo.err_order"));
        }
    }

    public void saveData() {
        if (orderRepository != null) {
            orderRepository.saveAll(orders);
        } else {
             throw new IllegalStateException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.repo.err_order"));
        }
    }
}
