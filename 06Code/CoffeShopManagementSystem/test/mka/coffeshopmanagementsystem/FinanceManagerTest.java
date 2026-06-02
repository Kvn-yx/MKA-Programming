package mka.coffeshopmanagementsystem;

import static org.junit.Assert.*;
import org.junit.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mka.coffeshopmanagementsystem.model.management.FinanceManager;
import mka.coffeshopmanagementsystem.model.order.Order;
import mka.coffeshopmanagementsystem.model.order.OrderItem;
import mka.coffeshopmanagementsystem.model.order.OrderStatus;
import mka.coffeshopmanagementsystem.model.payment.Cash;
import mka.coffeshopmanagementsystem.model.payment.CreditCard;
import mka.coffeshopmanagementsystem.model.payment.Payment;
import mka.coffeshopmanagementsystem.model.payment.Transfer;
import mka.coffeshopmanagementsystem.model.persistence.repository.IRepository;
import mka.coffeshopmanagementsystem.model.management.ZReportSnapshot;
import mka.coffeshopmanagementsystem.model.inventory.Product;
import mka.coffeshopmanagementsystem.model.people.Customer;

/**
 * Unit tests for FinanceManager.
 * Focuses on generateZReport logic.
 * Contains 4 base test cases implemented.
 * 
 * NOTE TO THE DEVELOPMENT TEAM: 
 * Please implement the remaining 6 test cases to complete the 10 tests requirement.
 * 
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class FinanceManagerTest {

    // Helper in-memory repository to prevent disk I/O during tests
    private static class MockZReportRepository implements IRepository<ZReportSnapshot> {
        private final List<ZReportSnapshot> storage = new ArrayList<>();

        @Override public List<ZReportSnapshot> findAll() { return new ArrayList<>(storage); }
        @Override public void saveAll(List<ZReportSnapshot> entities) { storage.clear(); storage.addAll(entities); }
        @Override public void add(ZReportSnapshot entity) { storage.add(entity); }
        @Override public ZReportSnapshot findById(String id) { return null; }
        @Override public void delete(String id) {}
    }

    // === IMPLEMENTED BASE TESTS (4 cases) ===

    @Test
    public void testGenerateZReportEmptyOrders() {
        // Test 1: An empty list of orders should produce zeroed values
        FinanceManager financeManager = new FinanceManager(new MockZReportRepository());
        LocalDate today = LocalDate.now();
        List<Order> orders = new ArrayList<>();

        Map<String, BigDecimal> report = financeManager.generateZReport(today, orders);

        assertEquals(0, BigDecimal.ZERO.compareTo(report.getOrDefault("ORDERS", BigDecimal.ZERO)));
        assertEquals(0, BigDecimal.ZERO.compareTo(report.getOrDefault("SUBTOTAL", BigDecimal.ZERO)));
        assertEquals(0, BigDecimal.ZERO.compareTo(report.getOrDefault("TAX", BigDecimal.ZERO)));
    }

    @Test
    public void testGenerateZReportFiltersByTargetDate() {
        // Test 2: The report must ignore orders from other dates
        FinanceManager financeManager = new FinanceManager(new MockZReportRepository());
        LocalDate targetDate = LocalDate.of(2026, 6, 1);
        List<Order> orders = new ArrayList<>();

        // Order today (should be included)
        Order orderToday = new Order("O-1");
        orderToday.setDateTime(targetDate.atStartOfDay());
        orderToday.setStatus(OrderStatus.PAID);
        orderToday.setTaxRate(new BigDecimal("0.15"));
        Product p = new Product(); p.setPrice(new BigDecimal("10.00"));
        OrderItem item1 = new OrderItem(); item1.setProduct(p); item1.setQuantity(1);
        orderToday.addItem(item1);
        orderToday.setPayment(new Cash(new BigDecimal("11.50"), new BigDecimal("20.00")));
        orders.add(orderToday);

        // Order yesterday (should be filtered out)
        Order orderYesterday = new Order("O-2");
        orderYesterday.setDateTime(targetDate.minusDays(1).atStartOfDay());
        orderYesterday.setStatus(OrderStatus.PAID);
        orderYesterday.setTaxRate(new BigDecimal("0.15"));
        OrderItem item2 = new OrderItem(); item2.setProduct(p); item2.setQuantity(1);
        orderYesterday.addItem(item2);
        orderYesterday.setPayment(new Cash(new BigDecimal("11.50"), new BigDecimal("20.00")));
        orders.add(orderYesterday);

        Map<String, BigDecimal> report = financeManager.generateZReport(targetDate, orders);

        // Only 1 order should be counted, and subtotal should be 10.00 (from orderToday)
        assertEquals(0, new BigDecimal("1").compareTo(report.get("ORDERS")));
        assertEquals(0, new BigDecimal("10.00").compareTo(report.get("SUBTOTAL")));
    }

    @Test
    public void testGenerateZReportFinancialSums() {
        // Test 3: Validate subtotal, tax (VAT), and totals summation accuracy
        FinanceManager financeManager = new FinanceManager(new MockZReportRepository());
        LocalDate targetDate = LocalDate.now();
        List<Order> orders = new ArrayList<>();

        Product prod = new Product(); prod.setPrice(new BigDecimal("20.00"));

        Order order = new Order("O-3");
        order.setDateTime(targetDate.atStartOfDay());
        order.setStatus(OrderStatus.PAID);
        order.setTaxRate(new BigDecimal("0.15")); // 15% VAT
        OrderItem item = new OrderItem(); item.setProduct(prod); item.setQuantity(1); // subtotal: 20.00
        order.addItem(item);
        order.setPayment(new Cash(new BigDecimal("23.00"), new BigDecimal("30.00")));
        orders.add(order);

        Map<String, BigDecimal> report = financeManager.generateZReport(targetDate, orders);

        assertEquals(0, new BigDecimal("20.00").compareTo(report.get("SUBTOTAL")));
        assertEquals(0, new BigDecimal("3.00").compareTo(report.get("TAX"))); // 15% of 20.00
    }

    @Test
    public void testGenerateZReportPaymentBreakdown() {
        // Test 4: Validate payment method categorization (CASH, CREDIT, TRANSFER)
        FinanceManager financeManager = new FinanceManager(new MockZReportRepository());
        LocalDate targetDate = LocalDate.now();
        List<Order> orders = new ArrayList<>();

        Product p = new Product(); p.setPrice(new BigDecimal("10.00"));

        // CASH order: Total 11.50
        Order o1 = new Order("O-4");
        o1.setDateTime(targetDate.atStartOfDay());
        o1.setStatus(OrderStatus.PAID);
        o1.setTaxRate(new BigDecimal("0.15"));
        OrderItem item1 = new OrderItem(); item1.setProduct(p); item1.setQuantity(1);
        o1.addItem(item1);
        o1.setPayment(new Cash(new BigDecimal("11.50"), new BigDecimal("15.00")));
        orders.add(o1);

        // CREDIT order: Total 11.50
        Order o2 = new Order("O-5");
        o2.setDateTime(targetDate.atStartOfDay());
        o2.setStatus(OrderStatus.PAID);
        o2.setTaxRate(new BigDecimal("0.15"));
        OrderItem item2 = new OrderItem(); item2.setProduct(p); item2.setQuantity(1);
        o2.addItem(item2);
        o2.setPayment(new CreditCard(new BigDecimal("11.50"), "TX-100"));
        orders.add(o2);

        Map<String, BigDecimal> report = financeManager.generateZReport(targetDate, orders);

        assertEquals(0, new BigDecimal("11.50").compareTo(report.get("CASH")));
        assertEquals(0, new BigDecimal("11.50").compareTo(report.get("CREDIT_CARD")));
        assertEquals(0, BigDecimal.ZERO.compareTo(report.getOrDefault("TRANSFER", BigDecimal.ZERO)));
    }

    // === PLACEHOLDERS FOR TEAM MEMBERS (6 cases remaining) ===

    // TODO: Member 1 - Implement Test 5: testGenerateZReportExcludesUnpaidOrders
    // public void testGenerateZReportExcludesUnpaidOrders() { ... }

    // TODO: Member 2 - Implement Test 6: testGenerateZReportExcludesCancelledOrders
    // public void testGenerateZReportExcludesCancelledOrders() { ... }

    // TODO: Member 3 - Implement Test 7: testGenerateZReportMultipleSameTypePaymentsSum
    // public void testGenerateZReportMultipleSameTypePaymentsSum() { ... }

    // TODO: Member 4 - Implement Test 8: testGenerateZReportDiscountsImpact
    // public void testGenerateZReportDiscountsImpact() { ... }

    // TODO: Member 5 - Implement Test 9: testGenerateZReportOrdersCountCorrectness
    // public void testGenerateZReportOrdersCountCorrectness() { ... }

    // TODO: Member 6 - Implement Test 10: testGenerateZReportTransferPayments
    // public void testGenerateZReportTransferPayments() { ... }
}
