package mka.coffeshopmanagementsystem;

import static org.junit.Assert.*;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import mka.coffeshopmanagementsystem.model.management.CoffeeShop;
import mka.coffeshopmanagementsystem.model.management.CatalogManager;
import mka.coffeshopmanagementsystem.model.management.InventoryManager;
import mka.coffeshopmanagementsystem.model.management.OrderManager;
import mka.coffeshopmanagementsystem.model.order.Order;
import mka.coffeshopmanagementsystem.model.order.OrderItem;
import mka.coffeshopmanagementsystem.model.order.OrderStatus;
import mka.coffeshopmanagementsystem.model.payment.Cash;
import mka.coffeshopmanagementsystem.model.payment.Payment;
import mka.coffeshopmanagementsystem.model.inventory.Ingredient;
import mka.coffeshopmanagementsystem.model.inventory.Inventory;
import mka.coffeshopmanagementsystem.model.inventory.Product;
import mka.coffeshopmanagementsystem.model.inventory.ProductIngredient;
import mka.coffeshopmanagementsystem.model.persistence.repository.IRepository;
import mka.coffeshopmanagementsystem.model.persistence.repository.ISingleRepository;
import mka.coffeshopmanagementsystem.model.people.Customer;

/**
 * Unit tests for CoffeeShop.finalizeAndPayOrder transactional logic.
 * Focuses on stock reservation, payment validation, and inventory deduction.
 * Contains 4 base test cases implemented.
 * 
 * NOTE TO THE DEVELOPMENT TEAM: 
 * Please implement the remaining 6 test cases to complete the 10 tests requirement.
 * 
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class CoffeeShopTransactionTest {

    // === IN-MEMORY MOCKS TO PREVENT DISK WRITE DURING TESTS ===

    private static class MockRepository<E> implements IRepository<E> {
        private final List<E> data = new ArrayList<>();
        @Override public List<E> findAll() { return new ArrayList<>(data); }
        @Override public void saveAll(List<E> entities) { data.clear(); data.addAll(entities); }
        @Override public void add(E entity) { data.add(entity); }
        @Override public E findById(String id) { return null; }
        @Override public void delete(String id) {}
    }

    private static class MockSingleRepository<E> implements ISingleRepository<E> {
        private E state;
        public MockSingleRepository(E initialState) { this.state = initialState; }
        @Override public E load() { return state; }
        @Override public void save(E entity) { this.state = entity; }
    }

    // Helper method to setup a fully mocked CoffeeShop in memory
    private CoffeeShop createMockedShop(Inventory inventory) {
        CoffeeShop shop = new CoffeeShop();
        shop.setName("Test NebulaX Shop");
        
        CatalogManager catalog = new CatalogManager(new MockRepository<Product>());
        OrderManager orders = new OrderManager(new MockRepository<Order>());
        InventoryManager inv = new InventoryManager(new MockSingleRepository<Inventory>(inventory));
        
        shop.setCatalogManager(catalog);
        shop.setOrderManager(orders);
        shop.setInventoryManager(inv);
        
        shop.getCatalogManager().loadData();
        shop.getOrderManager().loadData();
        shop.getInventoryManager().loadData();
        
        return shop;
    }

    // === IMPLEMENTED BASE TESTS (4 cases) ===

    @Test
    public void testFinalizeNullOrderOrPaymentThrowsException() {
        // Test 1: Passing null to order or payment should throw IllegalArgumentException
        CoffeeShop shop = createMockedShop(new Inventory());
        Order order = new Order("O-100");
        Payment payment = new Cash(BigDecimal.TEN, BigDecimal.TEN);

        try {
            shop.finalizeAndPayOrder(null, payment);
            fail("Expected IllegalArgumentException for null Order");
        } catch (IllegalArgumentException expected) {}

        try {
            shop.finalizeAndPayOrder(order, null);
            fail("Expected IllegalArgumentException for null Payment");
        } catch (IllegalArgumentException expected) {}
    }

    @Test
    public void testFinalizeSuccessDeductsStock() {
        // Test 2: A successful purchase must reduce stock accurately
        Inventory inventory = new Inventory();
        List<Ingredient> ingredientsList = new ArrayList<>();
        Ingredient coffee = new Ingredient();
        coffee.setIngredientId("ING-1");
        coffee.setName("Coffee Beans");
        coffee.setStockQuantity(new BigDecimal("1000")); // 1000g in stock
        coffee.setUnit("g");
        ingredientsList.add(coffee);
        inventory.setIngredients(ingredientsList);

        CoffeeShop shop = createMockedShop(inventory);

        // Recipe: 1 Espresso needs 15g coffee
        Product espresso = new Product();
        espresso.setProductId("P-1");
        espresso.setName("Espresso");
        espresso.setPrice(new BigDecimal("2.50"));
        List<ProductIngredient> recipeList = new ArrayList<>();
        ProductIngredient pi = new ProductIngredient();
        pi.setIngredient(coffee);
        pi.setQuantityNeeded(new BigDecimal("15"));
        recipeList.add(pi);
        espresso.setRecipe(recipeList);

        shop.getCatalogManager().addProduct(espresso);

        Order order = new Order("O-200");
        order.setCustomer(new Customer("C-1", "Client A", ""));
        OrderItem item = new OrderItem();
        item.setProduct(espresso);
        item.setQuantity(2); // Needs 30g total
        order.addItem(item);

        Payment payment = new Cash(new BigDecimal("5.00"), new BigDecimal("10.00"));

        shop.finalizeAndPayOrder(order, payment);

        // Verify ingredient stock decreased by 30g
        Ingredient stockItem = shop.getInventoryManager().findIngredient("ING-1");
        assertEquals(0, new BigDecimal("970").compareTo(stockItem.getStockQuantity()));
        assertEquals(OrderStatus.PAID, order.getStatus());
    }

    @Test
    public void testFinalizeInsufficientStockThrowsException() {
        // Test 3: Insufficient inventory stock must throw exception and NOT charge or change stock
        Inventory inventory = new Inventory();
        List<Ingredient> ingredientsList = new ArrayList<>();
        Ingredient milk = new Ingredient();
        milk.setIngredientId("ING-2");
        milk.setName("Milk");
        milk.setStockQuantity(new BigDecimal("100")); // 100ml in stock
        milk.setUnit("ml");
        ingredientsList.add(milk);
        inventory.setIngredients(ingredientsList);

        CoffeeShop shop = createMockedShop(inventory);

        // Recipe: 1 Latte needs 200ml milk
        Product latte = new Product();
        latte.setProductId("P-2");
        latte.setName("Latte");
        latte.setPrice(new BigDecimal("3.50"));
        List<ProductIngredient> recipeList = new ArrayList<>();
        ProductIngredient pi = new ProductIngredient();
        pi.setIngredient(milk);
        pi.setQuantityNeeded(new BigDecimal("200")); // Needs 200ml (we only have 100ml)
        recipeList.add(pi);
        latte.setRecipe(recipeList);

        shop.getCatalogManager().addProduct(latte);

        Order order = new Order("O-300");
        order.setCustomer(new Customer("C-2", "Client B", ""));
        OrderItem item = new OrderItem();
        item.setProduct(latte);
        item.setQuantity(1);
        order.addItem(item);

        Payment payment = new Cash(new BigDecimal("3.50"), new BigDecimal("5.00"));

        try {
            shop.finalizeAndPayOrder(order, payment);
            fail("Expected IllegalStateException due to insufficient stock");
        } catch (IllegalStateException expected) {
            // Stock should remain unchanged (100ml)
            Ingredient stockItem = shop.getInventoryManager().findIngredient("ING-2");
            assertEquals(0, new BigDecimal("100").compareTo(stockItem.getStockQuantity()));
            // Order status should not change to PAID
            assertNotEquals(OrderStatus.PAID, order.getStatus());
        }
    }

    @Test
    public void testFinalizeRecipeAggregation() {
        // Test 4: Multiple items in an order requiring the same ingredient must sum their requirements accurately
        Inventory inventory = new Inventory();
        List<Ingredient> ingredientsList = new ArrayList<>();
        Ingredient sugar = new Ingredient();
        sugar.setIngredientId("ING-3");
        sugar.setName("Sugar");
        sugar.setStockQuantity(new BigDecimal("15")); // 15g sugar in stock
        sugar.setUnit("g");
        ingredientsList.add(sugar);
        inventory.setIngredients(ingredientsList);

        CoffeeShop shop = createMockedShop(inventory);

        // Product A needs 5g sugar
        Product pA = new Product();
        pA.setProductId("P-A"); pA.setPrice(BigDecimal.ONE);
        List<ProductIngredient> recipeListA = new ArrayList<>();
        ProductIngredient piA = new ProductIngredient();
        piA.setIngredient(sugar); piA.setQuantityNeeded(new BigDecimal("5"));
        recipeListA.add(piA);
        pA.setRecipe(recipeListA);

        // Product B needs 7g sugar
        Product pB = new Product();
        pB.setProductId("P-B"); pB.setPrice(BigDecimal.ONE);
        List<ProductIngredient> recipeListB = new ArrayList<>();
        ProductIngredient piB = new ProductIngredient();
        piB.setIngredient(sugar); piB.setQuantityNeeded(new BigDecimal("7"));
        recipeListB.add(piB);
        pB.setRecipe(recipeListB);

        Order order = new Order("O-400");
        order.setCustomer(new Customer("C-3", "Client C", ""));
        
        OrderItem item1 = new OrderItem(); item1.setProduct(pA); item1.setQuantity(2); // 10g sugar
        OrderItem item2 = new OrderItem(); item2.setProduct(pB); item2.setQuantity(1); // 7g sugar
        // Total required: 17g sugar (stock is only 15g, should fail)
        
        order.addItem(item1);
        order.addItem(item2);

        Payment payment = new Cash(new BigDecimal("3.00"), new BigDecimal("5.00"));

        try {
            shop.finalizeAndPayOrder(order, payment);
            fail("Expected IllegalStateException due to aggregated stock insufficiency");
        } catch (IllegalStateException expected) {
            // Success: stock was evaluated in aggregate and failed cleanly
            Ingredient stockItem = shop.getInventoryManager().findIngredient("ING-3");
            assertEquals(0, new BigDecimal("15").compareTo(stockItem.getStockQuantity()));
        }
    }

    // === PLACEHOLDERS FOR TEAM MEMBERS (6 cases remaining) ===

    // TODO: Member 1 - Implement Test 5: testFinalizeFailedPaymentDoesNotDeductStock
    // public void testFinalizeFailedPaymentDoesNotDeductStock() { ... }

    // TODO: Member 2 - Implement Test 6: testFinalizeProductWithoutRecipeSucceeds
    // public void testFinalizeProductWithoutRecipeSucceeds() { ... }

    // TODO: Member 3 - Implement Test 7: testFinalizeQuantityMultiplierEspresso
    // public void testFinalizeQuantityMultiplierEspresso() { ... }

    // TODO: Member 4 - Implement Test 8: testFinalizeZeroQuantityItemsDoesNotChangeStock
    // public void testFinalizeZeroQuantityItemsDoesNotChangeStock() { ... }

    // TODO: Member 5 - Implement Test 9: testFinalizeNullCustomerInOrderSucceeds
    // public void testFinalizeNullCustomerInOrderSucceeds() { ... }

    // TODO: Member 6 - Implement Test 10: testFinalizeIntegrityOnRollbackDuringDeduction
    // public void testFinalizeIntegrityOnRollbackDuringDeduction() { ... }
}
