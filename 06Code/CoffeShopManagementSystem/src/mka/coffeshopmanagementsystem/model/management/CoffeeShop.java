/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.management;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import mka.coffeshopmanagementsystem.model.inventory.Ingredient;
import mka.coffeshopmanagementsystem.model.order.Order;
import mka.coffeshopmanagementsystem.model.order.OrderItem;
import mka.coffeshopmanagementsystem.model.payment.Payment;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class CoffeeShop {
    private String name;
    private String address;
    private OrderManager orderManager;
    private HRManager hrManager;
    private FloorManager floorManager;
    private InventoryManager inventoryManager;
    private CatalogManager catalogManager;
    private FinanceManager financeManager;

    public CoffeeShop() {
    }

    public void finalizeAndPayOrder(Order order, Payment payment) {
        if (order == null || payment == null) {
            throw new IllegalArgumentException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.shop.err_null_order_payment"));
        }

        // 1. Collect required ingredients for the entire order
        Map<Ingredient, BigDecimal> totalRequired = new HashMap<>();
        for (OrderItem item : order.getItems()) {
            if (item.getProduct() != null) {
                Map<Ingredient, BigDecimal> itemIngredients = item.getProduct().getRequiredIngredients();
                BigDecimal itemQty = new BigDecimal(item.getQuantity());
                itemIngredients.forEach((ingredient, amount) -> {
                    totalRequired.merge(ingredient, amount.multiply(itemQty), BigDecimal::add);
                });
            }
        }

        // 2. Pre-check inventory
        if (!inventoryManager.checkStockFor(totalRequired)) {
            throw new IllegalStateException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.inventory.err_insufficient"));
        }

        // 3. Process payment via OrderManager
        boolean success = orderManager.processPayment(order, payment);
        if (!success) {
            throw new IllegalStateException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.payment.err_failed"));
        }

        // 4. Deduct stock if payment was successful
        inventoryManager.deductStockFor(totalRequired);
    }

    public void linkOrdersAndCatalog() {
        if (this.orderManager == null || this.catalogManager == null) return;
        for (Order order : this.orderManager.getOrders()) {
            for (OrderItem item : order.getItems()) {
                if (item.getProductId() != null) {
                    Product p = this.catalogManager.findProductById(item.getProductId());
                    if (p != null) {
                        // setProduct también setea los snapshots pero de forma segura
                        item.setProduct(p);
                    }
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OrderManager getOrderManager() {
        return orderManager;
    }

    public void setOrderManager(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public HRManager getHrManager() {
        return hrManager;
    }

    public void setHrManager(HRManager hrManager) {
        this.hrManager = hrManager;
    }

    public FloorManager getFloorManager() {
        return floorManager;
    }

    public void setFloorManager(FloorManager floorManager) {
        this.floorManager = floorManager;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public void setInventoryManager(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    public CatalogManager getCatalogManager() {
        return catalogManager;
    }

    public void setCatalogManager(CatalogManager catalogManager) {
        this.catalogManager = catalogManager;
    }

    public FinanceManager getFinanceManager() {
        return financeManager;
    }

    public void setFinanceManager(FinanceManager financeManager) {
        this.financeManager = financeManager;
    }
}
