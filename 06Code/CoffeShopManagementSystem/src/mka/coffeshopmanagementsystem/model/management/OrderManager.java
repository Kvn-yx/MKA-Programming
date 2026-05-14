/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.management;

import com.google.gson.*;
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
import mka.coffeshopmanagementsystem.model.persistence.JsonFileManager;
import java.time.LocalDateTime;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class OrderManager {
    private List<Order> orders;
    private String dataFilePath;
    private JsonFileManager jsonFileManager;

    public OrderManager() {
        this.jsonFileManager = getCustomJsonFileManager();
        this.orders = new ArrayList<>();
    }

    private JsonFileManager getCustomJsonFileManager() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Payment.class, new PaymentAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();
        return new JsonFileManager(gson);
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
                public boolean process(Payment p) {
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
        if (jsonFileManager == null) {
            jsonFileManager = getCustomJsonFileManager();
        }
        Type listType = new TypeToken<ArrayList<Order>>(){}.getType();
        List<Order> loadedOrders = jsonFileManager.loadFromFile(dataFilePath, listType);
        if (loadedOrders != null) {
            this.orders = loadedOrders;
        } else {
            this.orders = new ArrayList<>();
        }
    }

    public void saveData() {
        if (jsonFileManager == null) {
            jsonFileManager = getCustomJsonFileManager();
        }
        jsonFileManager.saveToFile(dataFilePath, orders);
    }

    
    private static class PaymentAdapter implements JsonDeserializer<Payment>, JsonSerializer<Payment> {
        @Override
        public Payment deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonElement typeElement = jsonObject.get("type");
            
            if (typeElement == null) {
                throw new JsonParseException("Campo 'type' no encontrado en el JSON de payment");
            }
            
            String paymentType = typeElement.getAsString();
            switch (paymentType) {
                case "CASH":
                    return context.deserialize(json, Cash.class);
                case "CREDIT_CARD":
                    return context.deserialize(json, CreditCard.class);
                case "TRANSFER":
                    return context.deserialize(json, Transfer.class);
                default:
                    throw new JsonParseException("Tipo de pago desconocido: " + paymentType);
            }
        }

        @Override
        public JsonElement serialize(Payment src, Type typeOfSrc, JsonSerializationContext context) {
            return context.serialize(src, src.getClass());
        }
    }

    
    private static class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
        @Override
        public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }

        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return LocalDateTime.parse(json.getAsString());
        }
    }
}
