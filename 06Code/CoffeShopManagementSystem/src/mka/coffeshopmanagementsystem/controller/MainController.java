package mka.coffeshopmanagementsystem.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mka.coffeshopmanagementsystem.model.floor.Table;
import mka.coffeshopmanagementsystem.model.inventory.Ingredient;
import mka.coffeshopmanagementsystem.model.inventory.Inventory;
import mka.coffeshopmanagementsystem.model.inventory.Product;
import mka.coffeshopmanagementsystem.model.inventory.ProductIngredient;
import mka.coffeshopmanagementsystem.model.management.CoffeeShop;
import mka.coffeshopmanagementsystem.model.management.ZReportSnapshot;
import mka.coffeshopmanagementsystem.model.order.Order;
import mka.coffeshopmanagementsystem.model.order.OrderItem;
import mka.coffeshopmanagementsystem.model.order.OrderStatus;
import mka.coffeshopmanagementsystem.model.payment.Cash;
import mka.coffeshopmanagementsystem.model.payment.CreditCard;
import mka.coffeshopmanagementsystem.model.payment.Transfer;
import mka.coffeshopmanagementsystem.model.people.Barista;
import mka.coffeshopmanagementsystem.model.people.Cashier;
import mka.coffeshopmanagementsystem.model.people.Chef;
import mka.coffeshopmanagementsystem.model.people.Customer;
import mka.coffeshopmanagementsystem.model.people.Employee;
import mka.coffeshopmanagementsystem.model.people.Waiter;
import mka.coffeshopmanagementsystem.utils.I18n;
import mka.coffeshopmanagementsystem.utils.UnitConverter;
import mka.coffeshopmanagementsystem.view.MainView;

/**
 * Main controller for the Coffee Shop Management System.
 * Coordinates interaction between MainView and CoffeeShop model.
 * 
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class MainController {
    private final MainView view;
    private final CoffeeShop shop;

    public MainController(MainView view, CoffeeShop shop) {
        this.view = view;
        this.shop = shop;
    }

    public void start() {
        view.showWelcome();
        setupLanguage();
        
        boolean exit = false;
        while (!exit) {
            String option = view.showMainMenu();
            switch (option) {
                case "1": posModule(); break;
                case "2": kitchenModule(); break;
                case "3": catalogModule(); break;
                case "4": inventoryModule(); break;
                case "5": hrModule(); break;
                case "6": floorModule(); break;
                case "7": financeModule(); break;
                case "0": 
                    exit = true; 
                    saveData();
                    break;
                default: 
                    view.showErrorMessage(I18n.getString("msg.invalid"));
                    view.pause();
            }
        }
        view.showGoodbye();
    }

    private void setupLanguage() {
        String lang = view.promptLanguage();
        I18n.setLocale(new java.util.Locale(lang.equals("2") ? "es" : "en"));
    }

    private void saveData() {
        view.showMessage(I18n.getString("msg.saving"));
        shop.getOrderManager().saveData();
        shop.getCatalogManager().saveData();
        shop.getInventoryManager().saveData();
        shop.getFloorManager().saveData();
        shop.getHrManager().saveData();
        shop.getFinanceManager().saveData();
    }

    // --- POS Module ---
    private void posModule() {
        boolean back = false;
        while (!back) {
            String opt = view.showPosMenu();
            if (opt.equals("0")) back = true;
            else if (opt.equals("1")) processNewOrder();
        }
    }

    private void processNewOrder() {
        List<Product> products = shop.getCatalogManager().getProducts();
        if (products.isEmpty()) {
            view.showErrorMessage(I18n.getString("pos.emptyCatalog"));
            return;
        }

        String customerName = view.promptString(I18n.getString("pos.customerName"));
        Customer customer = new Customer(UUID.randomUUID().toString().substring(0, 8), customerName, "n/a");
        Order order = shop.getOrderManager().createOrder(customer);

        boolean adding = true;
        while (adding) {
            view.showProductList(products);
            int sel = view.promptInt(I18n.getString("pos.selectProduct"));
            if (sel == 0) {
                adding = false;
            } else if (sel > 0 && sel <= products.size()) {
                OrderItem item = new OrderItem();
                item.setProduct(products.get(sel - 1));
                item.setQuantity(view.promptInt(I18n.getString("pos.qty")));
                String mod = view.promptString(I18n.getString("pos.mods"));
                if (!mod.isEmpty()) item.addModifier(mod);
                order.addItem(item);
            } else {
                view.showErrorMessage(I18n.getString("msg.invalid"));
            }
        }

        if (order.getItems().isEmpty()) {
            view.showMessage(I18n.getString("pos.emptyCancel"));
            return;
        }

        handlePayment(order);
    }

    private void handlePayment(Order order) {
        BigDecimal total = order.calculateTotal();
        view.showOrderTotal(total);
        String payOpt = view.showPaymentMethods();
        
        mka.coffeshopmanagementsystem.model.payment.Payment payment = null;
        try {
            switch (payOpt) {
                case "1":
                    BigDecimal tendered = view.promptBigDecimal(I18n.getString("pos.tendered"));
                    payment = new Cash(total, tendered);
                    if (tendered.compareTo(total) >= 0) {
                        view.showMessage(I18n.getString("pos.change") + tendered.subtract(total));
                    }
                    break;
                case "2":
                    payment = new CreditCard(total, UUID.randomUUID().toString());
                    break;
                case "3":
                    payment = new Transfer(total, "ACC-SIM");
                    break;
                default:
                    view.showErrorMessage(I18n.getString("msg.invalid"));
                    return;
            }
        } catch (Exception e) {
            view.showErrorMessage(I18n.getString("msg.invalid"));
            return;
        }

        if (payment != null) {
            try {
                shop.finalizeAndPayOrder(order, payment);
                view.showMessage(I18n.getString("pos.invUpdated"));
                
                // Alertas de stock mínimo bajo
                for (OrderItem item : order.getItems()) {
                    if (item.getProduct() != null) {
                        item.getProduct().getRequiredIngredients().keySet().forEach(ing -> {
                            Ingredient inStock = shop.getInventoryManager().findIngredient(ing.getIngredientId());
                            if (inStock != null && inStock.getStockQuantity().compareTo(inStock.getMinimumAlertQuantity()) <= 0) {
                                view.showMessage("[ALERT] " + I18n.getString("inv.alertWarning") + " " + inStock.getName() + " (Stock: " + inStock.getStockQuantity() + " " + inStock.getUnit() + ")");
                            }
                        });
                    }
                }
            } catch (Exception e) {
                view.showErrorMessage(I18n.getString("msg.invalid") + " " + e.getMessage());
            }
        }
    }

    // --- Kitchen Module ---
    private void kitchenModule() {
        boolean back = false;
        while (!back) {
            String opt = view.showKitchenMenu();
            if (opt.equals("0")) back = true;
            else if (opt.equals("1")) {
                view.showActiveOrders(shop.getOrderManager().getOrders());
                view.pause();
            }
            else if (opt.equals("2")) {
                updateOrderStatus();
                view.pause();
            }
        }
    }

    private void updateOrderStatus() {
        view.showActiveOrders(shop.getOrderManager().getOrders());
        String id = view.promptString(I18n.getString("kit.enterId"));
        shop.getOrderManager().getOrders().stream()
            .filter(o -> o.getOrderId().startsWith(id))
            .findFirst()
            .ifPresentOrElse(order -> {
                String s = view.showStatusOptions();
                switch (s) {
                    case "1": order.updateStatus(OrderStatus.PREPARING); break;
                    case "2": order.updateStatus(OrderStatus.READY); break;
                    case "3": order.updateStatus(OrderStatus.SERVED); break;
                    default: view.showErrorMessage(I18n.getString("msg.invalid"));
                }
            }, () -> view.showErrorMessage(I18n.getString("kit.notFound")));
    }

    // --- Catalog Module ---
    private void catalogModule() {
        boolean back = false;
        while (!back) {
            String opt = view.showCatalogMenu();
            switch (opt) {
                case "0": back = true; break;
                case "1": view.showCatalog(shop.getCatalogManager().getProducts()); break;
                case "2": addNewProduct(); break;
                case "3": deleteProduct(); break;
                case "4": editProduct(); break;
                case "5": editProductRecipe(); break;
                default: view.showErrorMessage(I18n.getString("msg.invalid"));
            }
            if (!back) view.pause();
        }
    }

    private void addNewProduct() {
        Product p = new Product();
        p.setName(view.promptString(I18n.getString("cat.name")));
        p.setPrice(view.promptBigDecimal(I18n.getString("cat.price")));
        
        String askRecipe = view.promptString(I18n.getString("cat.askRecipe"));
        if (askRecipe.toLowerCase().matches("s|y")) {
            p.setRecipe(promptRecipe());
        }
        shop.getCatalogManager().addProduct(p);
    }

    private List<ProductIngredient> promptRecipe() {
        List<ProductIngredient> recipe = new ArrayList<>();
        while (true) {
            Ingredient ing = new Ingredient();
            ing.setName(view.promptString(I18n.getString("cat.ingName")));
            String unit = view.promptString(I18n.getString("cat.ingUnit"));
            BigDecimal qty = view.promptBigDecimal(I18n.getString("cat.ingQty") + unit + "): ");
            
            UnitConverter.ConversionResult norm = UnitConverter.normalize(unit, qty);
            ing.setUnit(norm.unit);
            
            ProductIngredient pi = new ProductIngredient();
            pi.setIngredient(ing);
            pi.setQuantityNeeded(norm.quantity);
            recipe.add(pi);
            
            if (!view.promptString(I18n.getString("cat.askAnother")).toLowerCase().matches("s|y")) break;
        }
        return recipe;
    }

    private void deleteProduct() {
        String id = view.promptString(I18n.getString("cat.delId"));
        try {
            shop.getCatalogManager().removeProduct(id);
            view.showMessage(I18n.getString("inv.deleted"));
        } catch (Exception e) {
            view.showErrorMessage(e.getMessage());
        }
    }

    private void editProduct() {
        String id = view.promptString(I18n.getString("cat.enterId"));
        try {
            BigDecimal price = view.promptBigDecimal(I18n.getString("cat.price"));
            shop.getCatalogManager().updateProductPrice(id, price);
            view.showMessage(I18n.getString("cat.updated"));
        } catch (Exception e) {
            view.showErrorMessage(e.getMessage());
        }
    }

    private void editProductRecipe() {
        String id = view.promptString(I18n.getString("cat.enterId"));
        view.showMessage(I18n.getString("cat.editRecipeMsg"));
        List<ProductIngredient> recipe = promptRecipe();
        try {
            shop.getCatalogManager().updateProductRecipe(id, recipe);
            view.showMessage(I18n.getString("cat.recipeUpdated"));
        } catch (Exception e) {
            view.showErrorMessage(e.getMessage());
        }
    }

    // --- Inventory Module ---
    private void inventoryModule() {
        boolean back = false;
        while (!back) {
            String opt = view.showInventoryMenu();
            switch (opt) {
                case "0": back = true; break;
                case "1": view.showInventory(shop.getInventoryManager().getInventory()); break;
                case "2": addNewIngredient(); break;
                case "3": editIngredientStock(); break;
                case "4": deleteIngredient(); break;
                default: view.showErrorMessage(I18n.getString("msg.invalid"));
            }
            if (!back) view.pause();
        }
    }

    private void addNewIngredient() {
        Ingredient ing = new Ingredient();
        ing.setIngredientId(UUID.randomUUID().toString().substring(0, 8));
        ing.setName(view.promptString(I18n.getString("cat.ingName")));
        String unit = view.promptString(I18n.getString("inv.unit"));
        BigDecimal qty = view.promptBigDecimal(I18n.getString("inv.qty"));
        BigDecimal alertQty = view.promptBigDecimal(I18n.getString("inv.alertQty"));
        
        UnitConverter.ConversionResult norm = UnitConverter.normalize(unit, qty);
        UnitConverter.ConversionResult normAlert = UnitConverter.normalize(unit, alertQty);
        ing.setUnit(norm.unit);
        ing.setStockQuantity(norm.quantity);
        ing.setMinimumAlertQuantity(normAlert.quantity);
        
        shop.getInventoryManager().addIngredient(ing);
        view.showMessage(I18n.getString("inv.added"));
    }

    private void editIngredientStock() {
        String id = view.promptString(I18n.getString("inv.enterId"));
        Ingredient ing = shop.getInventoryManager().findIngredient(id);
        if (ing == null) {
            view.showErrorMessage(I18n.getString("inv.notFound"));
            return;
        }
        String unit = view.promptString(I18n.getString("inv.unit"));
        BigDecimal qty = view.promptBigDecimal(I18n.getString("inv.qty"));
        BigDecimal alertQty = view.promptBigDecimal(I18n.getString("inv.alertQty"));
        
        try {
            UnitConverter.ConversionResult norm = UnitConverter.normalize(unit, qty);
            UnitConverter.ConversionResult normAlert = UnitConverter.normalize(unit, alertQty);
            shop.getInventoryManager().updateIngredientStock(id, norm.quantity);
            ing.setMinimumAlertQuantity(normAlert.quantity);
            view.showMessage(I18n.getString("inv.updated"));
        } catch (Exception e) {
            view.showErrorMessage(e.getMessage());
        }
    }

    private void deleteIngredient() {
        String id = view.promptString(I18n.getString("inv.delId"));
        try {
            shop.getInventoryManager().removeIngredient(id);
            view.showMessage(I18n.getString("inv.deleted"));
        } catch (Exception e) {
            view.showErrorMessage(e.getMessage());
        }
    }

    // --- HR Module ---
    private void hrModule() {
        boolean back = false;
        while (!back) {
            String opt = view.showHrMenu();
            switch (opt) {
                case "0": back = true; break;
                case "1": view.showEmployees(shop.getHrManager().getEmployees()); break;
                case "2": hireEmployee(); break;
                case "3": fireEmployee(); break;
                case "4": assignShift(); break;
                default: view.showErrorMessage(I18n.getString("msg.invalid"));
            }
            if (!back) view.pause();
        }
    }

    private void hireEmployee() {
        String name = view.promptString(I18n.getString("hr.name"));
        String roleOpt = view.showRoleOptions();
        String id = UUID.randomUUID().toString().substring(0, 8);
        Employee e = null;
        switch (roleOpt) {
            case "1": e = new Cashier(id, name); break;
            case "2": e = new Waiter(id, name); break;
            case "3": e = new Barista(id, name); break;
            case "4": e = new Chef(id, name); break;
        }
        
        if (e != null) {
            shop.getHrManager().addEmployee(e);
            view.showMessage(I18n.getString("hr.registered") + name);
        } else {
            view.showErrorMessage(I18n.getString("msg.invalid"));
        }
    }

    private void fireEmployee() {
        String id = view.promptString(I18n.getString("hr.enterId"));
        try {
            shop.getHrManager().removeEmployee(id);
            view.showMessage(I18n.getString("hr.fired"));
        } catch (Exception e) {
            view.showErrorMessage(e.getMessage());
        }
    }

    private void assignShift() {
        String id = view.promptString(I18n.getString("hr.enterId"));
        String shift = view.promptString(I18n.getString("hr.enterShift"));
        try {
            shop.getHrManager().assignShift(id, shift);
            view.showMessage(I18n.getString("hr.shift"));
        } catch (Exception e) {
            view.showErrorMessage(e.getMessage());
        }
    }

    // --- Floor Module ---
    private void floorModule() {
        boolean back = false;
        while (!back) {
            String opt = view.showFloorMenu();
            if (opt.equals("0")) {
                back = true;
            } else {
                handleFloorOption(opt);
                view.pause();
            }
        }
    }

    private void handleFloorOption(String opt) {
        try {
            switch (opt) {
                case "1": view.showTables(shop.getFloorManager().getTables()); break;
                case "2": 
                    Table t = new Table(); 
                    t.setId("Mesa-" + (shop.getFloorManager().getTables().size() + 1));
                    shop.getFloorManager().addTable(t);
                    view.showMessage(I18n.getString("flr.tblAdded"));
                    break;
                case "3":
                    String tid = view.promptString(I18n.getString("flr.enterId"));
                    shop.getFloorManager().removeTable(tid);
                    view.showMessage(I18n.getString("flr.deleted"));
                    break;
                case "4": view.showMachines(shop.getFloorManager().getMachines()); break;
                case "5":
                    mka.coffeshopmanagementsystem.model.floor.Machine m = new mka.coffeshopmanagementsystem.model.floor.Machine();
                    m.setBrand(view.promptString(I18n.getString("flr.brand")));
                    shop.getFloorManager().addMachine(m);
                    view.showMessage(I18n.getString("flr.macAdded"));
                    break;
                case "6":
                    String brand = view.promptString(I18n.getString("flr.brand"));
                    shop.getFloorManager().removeMachine(brand);
                    view.showMessage(I18n.getString("flr.deleted"));
                    break;
                default: view.showErrorMessage(I18n.getString("msg.invalid"));
            }
        } catch (Exception e) {
            view.showErrorMessage(e.getMessage());
        }
    }

    // --- Finance Module ---
    private void financeModule() {
        Map<String, BigDecimal> report = shop.getFinanceManager().generateZReport(LocalDate.now(), shop.getOrderManager().getOrders());
        view.showZReport(report);
        
        String confirm = view.promptString(I18n.getString("fin.askClose"));
        if (confirm.toLowerCase().matches("s|y")) {
            BigDecimal subtotal = report.getOrDefault("SUBTOTAL", BigDecimal.ZERO);
            BigDecimal tax = report.getOrDefault("TAX", BigDecimal.ZERO);
            
            BigDecimal total = report.entrySet().stream()
                .filter(e -> !e.getKey().equals("ORDERS") && !e.getKey().equals("SUBTOTAL") && !e.getKey().equals("TAX"))
                .map(Map.Entry::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            int totalOrders = report.getOrDefault("ORDERS", BigDecimal.ZERO).intValue();
            
            // Desglose de pagos sin metadatos
            java.util.Map<String, BigDecimal> breakdown = new java.util.HashMap<>(report);
            breakdown.remove("ORDERS");
            breakdown.remove("SUBTOTAL");
            breakdown.remove("TAX");
            
            ZReportSnapshot snapshot = new ZReportSnapshot(
                UUID.randomUUID().toString().substring(0, 8),
                LocalDate.now().toString(),
                totalOrders,
                subtotal,
                tax,
                total,
                breakdown
            );
            
            shop.getFinanceManager().saveZReport(snapshot);
            view.showMessage(I18n.getString("fin.closeSuccess"));
        }
        view.pause();
    }
}
