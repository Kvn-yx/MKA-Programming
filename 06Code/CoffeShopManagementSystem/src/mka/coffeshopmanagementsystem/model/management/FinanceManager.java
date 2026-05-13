/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.management;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import mka.coffeshopmanagementsystem.model.order.Order;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class FinanceManager {
    private String dataFilePath;

    public FinanceManager() {
    }

    public String getDataFilePath() {
        return dataFilePath;
    }

    public void setDataFilePath(String dataFilePath) {
        this.dataFilePath = dataFilePath;
    }

    public Map<String, BigDecimal> generateZReport(LocalDate date, List<Order> orders) {
        // TODO: implement
        return null;
    }

    public void loadData() {
        // TODO: implement
    }

    public void saveData() {
        // TODO: implement
    }
}
