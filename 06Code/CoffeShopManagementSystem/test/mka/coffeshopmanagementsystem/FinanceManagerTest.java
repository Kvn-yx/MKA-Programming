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

    @Test
    public void testGenerateZReportExcludesUnpaidOrders() {
        // Test 5: Only PAID orders should be included in the report
        FinanceManager financeManager = new FinanceManager(new MockZReportRepository());
        LocalDate targetDate = LocalDate.now();
        List<Order> orders = new ArrayList<>();

        Order unpaidOrder = new Order("O-UNPAID");
        unpaidOrder.setDateTime(targetDate.atStartOfDay());
        unpaidOrder.setStatus(OrderStatus.PENDING);
        Product p = new Product(); p.setPrice(new BigDecimal("10.00"));
        OrderItem item = new OrderItem(); item.setProduct(p); item.setQuantity(1);
        unpaidOrder.addItem(item);
        // Even if it has a payment object, if status is not PAID, it should be ignored
        unpaidOrder.setPayment(new Cash(new BigDecimal("10.00"), new BigDecimal("10.00")));
        orders.add(unpaidOrder);

        Map<String, BigDecimal> report = financeManager.generateZReport(targetDate, orders);

        assertEquals(0, BigDecimal.ZERO.compareTo(report.get("ORDERS")));
        assertEquals(0, BigDecimal.ZERO.compareTo(report.get("CASH")));
    }

    @Test
    public void testGenerateZReportExcludesCancelledOrders() {
        // Test 6: CANCELLED orders must be excluded
        FinanceManager financeManager = new FinanceManager(new MockZReportRepository());
        LocalDate targetDate = LocalDate.now();
        List<Order> orders = new ArrayList<>();

        Order cancelledOrder = new Order("O-CANCELLED");
        cancelledOrder.setDateTime(targetDate.atStartOfDay());
        cancelledOrder.setStatus(OrderStatus.SERVED);
        Product p = new Product(); p.setPrice(new BigDecimal("50.00"));
        OrderItem item = new OrderItem(); item.setProduct(p); item.setQuantity(1);
        cancelledOrder.addItem(item);
        orders.add(cancelledOrder);

        Map<String, BigDecimal> report = financeManager.generateZReport(targetDate, orders);

        assertEquals(0, BigDecimal.ZERO.compareTo(report.get("ORDERS")));
        assertEquals(0, BigDecimal.ZERO.compareTo(report.get("SUBTOTAL")));
    }

    @Test
    public void testGenerateZReportMultipleSameTypePaymentsSum() {
        // Test 7: Multiple orders with same payment method should accumulate
        FinanceManager financeManager = new FinanceManager(new MockZReportRepository());
        LocalDate targetDate = LocalDate.now();
        List<Order> orders = new ArrayList<>();

        Product p = new Product(); p.setPrice(new BigDecimal("10.00"));

        Order o1 = new Order("O-7-1");
        o1.setDateTime(targetDate.atStartOfDay());
        o1.setStatus(OrderStatus.PAID);
        OrderItem item1 = new OrderItem(); item1.setProduct(p); item1.setQuantity(1);
        o1.addItem(item1);
        o1.setPayment(new Cash(new BigDecimal("10.00"), new BigDecimal("10.00")));
        orders.add(o1);

        Order o2 = new Order("O-7-2");
        o2.setDateTime(targetDate.atStartOfDay());
        o2.setStatus(OrderStatus.PAID);
        OrderItem item2 = new OrderItem(); item2.setProduct(p); item2.setQuantity(1);
        o2.addItem(item2);
        o2.setPayment(new Cash(new BigDecimal("10.00"), new BigDecimal("10.00")));
        orders.add(o2);

        Map<String, BigDecimal> report = financeManager.generateZReport(targetDate, orders);

        assertEquals(0, new BigDecimal("20.00").compareTo(report.get("CASH")));
        assertEquals(0, new BigDecimal("2").compareTo(report.get("ORDERS")));
    }

    @Test
    public void testGenerateZReportDiscountsImpact() {
        // Test 8: Discounts should reduce the final payment amount
        FinanceManager financeManager = new FinanceManager(new MockZReportRepository());
        LocalDate targetDate = LocalDate.now();
        List<Order> orders = new ArrayList<>();

        Product p = new Product(); p.setPrice(new BigDecimal("100.00"));
        Order order = new Order("O-DISCOUNT");
        order.setDateTime(targetDate.atStartOfDay());
        order.setStatus(OrderStatus.PAID);
        order.setTaxRate(BigDecimal.ZERO);
        OrderItem item = new OrderItem(); item.setProduct(p); item.setQuantity(1);
        order.addItem(item);
        
        // Apply $10 discount. Total should be $90.
        order.setDiscount(new BigDecimal("10.00"));
        BigDecimal finalTotal = order.calculateTotal(); // 100 - 10 = 90
        
        order.setPayment(new Cash(finalTotal, new BigDecimal("100.00")));
        orders.add(order);

        Map<String, BigDecimal> report = financeManager.generateZReport(targetDate, orders);

        // The CASH total in the report should reflect the discounted amount paid
        assertEquals(0, new BigDecimal("90.00").compareTo(report.get("CASH")));
    }

    @Test
    public void testGenerateZReportOrdersCountCorrectness() {
        // Test 9: Verify ORDERS count matches the number of processed PAID orders
        FinanceManager financeManager = new FinanceManager(new MockZReportRepository());
        LocalDate targetDate = LocalDate.now();
        List<Order> orders = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Order o = new Order("O-COUNT-" + i);
            o.setDateTime(targetDate.atStartOfDay());
            o.setStatus(OrderStatus.PAID);
            o.setPayment(new Cash(BigDecimal.TEN, BigDecimal.TEN));
            orders.add(o);
        }

        Map<String, BigDecimal> report = financeManager.generateZReport(targetDate, orders);

        assertEquals(0, new BigDecimal("5").compareTo(report.get("ORDERS")));
    }

    @Test
    public void testGenerateZReportTransferPayments() {
        // Test 10: Validate TRANSFER payment accumulation
        FinanceManager financeManager = new FinanceManager(new MockZReportRepository());
        LocalDate targetDate = LocalDate.now();
        List<Order> orders = new ArrayList<>();

        Order o = new Order("O-TRANSFER");
        o.setDateTime(targetDate.atStartOfDay());
        o.setStatus(OrderStatus.PAID);
        o.setPayment(new Transfer(new BigDecimal("150.00"), "BANK-XYZ-TR-999"));
        orders.add(o);

        Map<String, BigDecimal> report = financeManager.generateZReport(targetDate, orders);

        assertEquals(0, new BigDecimal("150.00").compareTo(report.get("TRANSFER")));
    }
}
