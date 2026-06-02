package mka.coffeshopmanagementsystem.model.management;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Snapshot representation of a financial Z-report.
 * Used for historical audit tracking.
 * 
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class ZReportSnapshot {
    private String reportId;
    private String date;
    private int totalOrders;
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal total;
    private Map<String, BigDecimal> paymentBreakdown;

    public ZReportSnapshot() {
    }

    public ZReportSnapshot(String reportId, String date, int totalOrders, BigDecimal subtotal, BigDecimal tax, BigDecimal total, Map<String, BigDecimal> paymentBreakdown) {
        this.reportId = reportId;
        this.date = date;
        this.totalOrders = totalOrders;
        this.subtotal = subtotal;
        this.tax = tax;
        this.total = total;
        this.paymentBreakdown = paymentBreakdown;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Map<String, BigDecimal> getPaymentBreakdown() {
        return paymentBreakdown;
    }

    public void setPaymentBreakdown(Map<String, BigDecimal> paymentBreakdown) {
        this.paymentBreakdown = paymentBreakdown;
    }
}
