/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.management;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mka.coffeshopmanagementsystem.model.order.Order;
import mka.coffeshopmanagementsystem.model.order.OrderStatus;
import mka.coffeshopmanagementsystem.model.persistence.JsonFileManager;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class FinanceManager {
    private String dataFilePath;
    private final JsonFileManager jsonFileManager;

    public FinanceManager() {
        this.jsonFileManager = new JsonFileManager();
    }

    public String getDataFilePath() {
        return dataFilePath;
    }

    public void setDataFilePath(String dataFilePath) {
        this.dataFilePath = dataFilePath;
    }

    public Map<String, BigDecimal> generateZReport(LocalDate date, List<Order> orders) {
        Map<String, BigDecimal> report = new HashMap<>();
        report.put("CASH", BigDecimal.ZERO);
        report.put("CREDIT_CARD", BigDecimal.ZERO);
        report.put("TRANSFER", BigDecimal.ZERO);
        
        if (orders != null) {
            for (Order order : orders) {
                if (order.getStatus() == OrderStatus.PAID && order.getPayment() != null) {
                    if (order.getDateTime() != null && order.getDateTime().toLocalDate().equals(date)) {
                        String paymentType = order.getPayment().getType();
                        BigDecimal amount = order.getPayment().getAmount();
                        if (amount != null && paymentType != null) {
                            BigDecimal currentTotal = report.getOrDefault(paymentType, BigDecimal.ZERO);
                            report.put(paymentType, currentTotal.add(amount));
                        }
                    }
                }
            }
        }
        return report;
    }

    public void loadData() {
    }

    public void saveData() {
    }
}
