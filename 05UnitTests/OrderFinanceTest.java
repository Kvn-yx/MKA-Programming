package mka.coffeshopmanagementsystem;

import static org.junit.Assert.*;
import org.junit.Test;
import java.math.BigDecimal;
import mka.coffeshopmanagementsystem.model.order.Order;
import mka.coffeshopmanagementsystem.model.order.OrderItem;
import mka.coffeshopmanagementsystem.model.inventory.Product;

/**
 * Unit tests for Order financial calculations.
 * Validates subtotal, tax (VAT), and discount calculations.
 * 
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class OrderFinanceTest {

    @Test
    public void testOrderCalculations() {
        Order order = new Order("TEST-001");
        order.setTaxRate(new BigDecimal("0.15")); // 15% tax
        
        Product product = new Product();
        product.setPrice(new BigDecimal("10.00"));
        
        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(2); // Subtotal: 20.00
        
        order.addItem(item);
        
        // Subtotal should be 20.00
        assertEquals(0, new BigDecimal("20.00").compareTo(order.calculateSubtotal()));
        
        // Tax should be 3.00 (15% of 20.00)
        assertEquals(0, new BigDecimal("3.00").compareTo(order.calculateTax()));
        
        // Total should be 23.00
        assertEquals(0, new BigDecimal("23.00").compareTo(order.calculateTotal()));
        
        // Apply discount of 5.00
        order.setDiscount(new BigDecimal("5.00"));
        
        // Total should be 18.00 (23.00 - 5.00)
        assertEquals(0, new BigDecimal("18.00").compareTo(order.calculateTotal()));
    }

    @Test
    public void testNegativeTotalPrevention() {
        Order order = new Order("TEST-002");
        Product product = new Product();
        product.setPrice(new BigDecimal("5.00"));
        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(1);
        order.addItem(item);
        
        order.setDiscount(new BigDecimal("10.00"));
        
        // Total should be 0, not -5.00
        assertEquals(0, BigDecimal.ZERO.compareTo(order.calculateTotal()));
    }
}
