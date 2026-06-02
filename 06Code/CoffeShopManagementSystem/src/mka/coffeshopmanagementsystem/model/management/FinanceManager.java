package mka.coffeshopmanagementsystem.model.management;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mka.coffeshopmanagementsystem.model.order.Order;
import mka.coffeshopmanagementsystem.model.order.OrderStatus;
import mka.coffeshopmanagementsystem.model.persistence.repository.IRepository;

/**
 * Manages financial reports and daily closures (Z-reports).
 * Supports historical audit tracking.
 * 
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class FinanceManager {
    private final IRepository<ZReportSnapshot> historyRepository;
    private List<ZReportSnapshot> history;

    public FinanceManager() {
        this.historyRepository = null;
        this.history = new java.util.ArrayList<>();
    }

    public FinanceManager(IRepository<ZReportSnapshot> historyRepository) {
        this.historyRepository = historyRepository;
        this.history = new java.util.ArrayList<>();
    }

    public void saveZReport(ZReportSnapshot snapshot) {
        if (snapshot != null) {
            if (this.history == null) {
                this.history = new java.util.ArrayList<>();
            }
            this.history.add(snapshot);
            saveData();
        }
    }

    public List<ZReportSnapshot> getHistory() {
        if (history == null) {
            return java.util.Collections.emptyList();
        }
        return java.util.Collections.unmodifiableList(history);
    }

    public Map<String, BigDecimal> generateZReport(LocalDate date, List<Order> orders) {
        Map<String, BigDecimal> report = new HashMap<>();
        report.put("CASH", BigDecimal.ZERO);
        report.put("CREDIT_CARD", BigDecimal.ZERO);
        report.put("TRANSFER", BigDecimal.ZERO);
        report.put("SUBTOTAL", BigDecimal.ZERO);
        report.put("TAX", BigDecimal.ZERO);
        report.put("ORDERS", BigDecimal.ZERO);
        
        if (orders != null) {
            for (Order order : orders) {
                if (order.getStatus() == OrderStatus.PAID && order.getPayment() != null) {
                    if (order.getDateTime() != null && order.getDateTime().toLocalDate().equals(date)) {
                        String paymentType = order.getPayment().getType();
                        BigDecimal amount = order.getPayment().getAmount();
                        
                        BigDecimal subtotal = order.calculateSubtotal();
                        BigDecimal tax = order.calculateTax();
                        
                        report.put("SUBTOTAL", report.get("SUBTOTAL").add(subtotal));
                        report.put("TAX", report.get("TAX").add(tax));
                        report.put("ORDERS", report.get("ORDERS").add(BigDecimal.ONE));

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
        if (historyRepository != null) {
            this.history = historyRepository.findAll();
        }
    }

    public void saveData() {
        if (historyRepository != null && history != null) {
            historyRepository.saveAll(history);
        }
    }
}
