/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import mka.coffeshopmanagementsystem.model.floor.Table;
import mka.coffeshopmanagementsystem.model.inventory.*;
import mka.coffeshopmanagementsystem.model.management.*;
import mka.coffeshopmanagementsystem.model.order.*;
import mka.coffeshopmanagementsystem.model.payment.Cash;
import mka.coffeshopmanagementsystem.model.payment.CreditCard;
import mka.coffeshopmanagementsystem.model.payment.Transfer;
import mka.coffeshopmanagementsystem.model.people.*;
import mka.coffeshopmanagementsystem.utils.I18n;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class MainView {
    private final Scanner scanner = new Scanner(System.in);
    private final CoffeeShop shop = new CoffeeShop();

    public MainView() {
        shop.setName("NebulaX Coffee");
        I18n.setLocale(new Locale("en"));
        initSystem();
        loadAllData();
    }

    private void initSystem() {
        shop.setOrderManager(new OrderManager());
        shop.getOrderManager().setDataFilePath("data/orders.json");
        
        shop.setCatalogManager(new CatalogManager());
        shop.getCatalogManager().setDataFilePath("data/catalog.json");
        
        shop.setInventoryManager(new InventoryManager());
        shop.getInventoryManager().setDataFilePath("data/inventory.json");
        
        shop.setFloorManager(new FloorManager());
        shop.getFloorManager().setDataFilePath("data/floor.json");
        
        shop.setHrManager(new HRManager());
        
        shop.getHrManager().setDataFilePath("data/employees.json");
        
        shop.setFinanceManager(new FinanceManager());
        shop.getFinanceManager().setDataFilePath("data/finance.json");
    }

    private void loadAllData() {
        System.out.println("=========================================");
        System.out.println(I18n.getString("msg.init"));
        System.out.println("=========================================");
        shop.getOrderManager().loadData();
        shop.getCatalogManager().loadData();
        shop.getInventoryManager().loadData();
        shop.getFloorManager().loadData();
        shop.getHrManager().loadData();
        shop.getFinanceManager().loadData();
        System.out.println(I18n.getString("msg.ok"));
    }

    private void saveAllData() {
        System.out.println("\n" + I18n.getString("msg.saving"));
        shop.getOrderManager().saveData();
        shop.getCatalogManager().saveData();
        shop.getInventoryManager().saveData();
        shop.getFloorManager().saveData();
        shop.getHrManager().saveData();
        shop.getFinanceManager().saveData();
    }

    public void start() {
        System.out.println(I18n.getString("lang.en") + "\n" + I18n.getString("lang.es"));
        System.out.print(I18n.getString("lang.prompt") + " ");
        String lang = scanner.nextLine().trim();
        I18n.setLocale(new Locale(lang.equals("2") ? "es" : "en"));

        boolean exit = false;
        while (!exit) {
            displayMainMenu();
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1": posModule(); break;
                case "2": kitchenModule(); break;
                case "3": catalogModule(); break;
                case "4": inventoryModule(); break;
                case "5": hrModule(); break;
                case "6": floorModule(); break;
                case "7": financeModule(); break;
                case "0": exit = true; saveAllData(); break;
                default: System.out.println(I18n.getString("msg.invalid")); pause();
            }
        }
        System.out.println(I18n.getString("msg.exit"));
    }

    private void displayMainMenu() {
        System.out.println("\n\n=========================================");
        System.out.println(I18n.getString("menu.title"));
        System.out.println("=========================================");
        for (int i = 1; i <= 7; i++) System.out.println(I18n.getString("menu.opt" + i));
        System.out.println(I18n.getString("menu.opt0"));
        System.out.println("=========================================");
        System.out.print(I18n.getString("menu.select"));
    }

    private void pause() {
        System.out.println("\n" + I18n.getString("msg.pause"));
        scanner.nextLine();
    }

    private void posModule() {
        while (true) {
            System.out.println("\n" + I18n.getString("mod.pos"));
            System.out.println(I18n.getString("pos.newOrder"));
            System.out.println(I18n.getString("menu.back"));
            System.out.print(I18n.getString("menu.select"));
            String opt = scanner.nextLine().trim();
            if (opt.equals("0")) break;
            if (opt.equals("1")) processNewOrder();
        }
    }

    private void processNewOrder() {
        if (shop.getCatalogManager().getProducts().isEmpty()) {
            System.out.println(I18n.getString("pos.emptyCatalog"));
            return;
        }
        System.out.print(I18n.getString("pos.customerName"));
        Customer c = new Customer(UUID.randomUUID().toString().substring(0,8), scanner.nextLine(), "n/a");
        Order o = shop.getOrderManager().createOrder(c);

        boolean adding = true;
        while (adding) {
            System.out.println(I18n.getString("pos.menuTitle"));
            List<Product> products = shop.getCatalogManager().getProducts();
            for (int i = 0; i < products.size(); i++) 
                System.out.println((i + 1) + ". " + products.get(i).getName() + " - $" + products.get(i).getPrice());
            
            System.out.print(I18n.getString("pos.selectProduct"));
            try {
                int sel = Integer.parseInt(scanner.nextLine().trim());
                if (sel == 0) adding = false;
                else if (sel > 0 && sel <= products.size()) {
                    OrderItem it = new OrderItem();
                    it.setProduct(products.get(sel - 1));
                    System.out.print(I18n.getString("pos.qty"));
                    it.setQuantity(Integer.parseInt(scanner.nextLine().trim()));
                    System.out.print(I18n.getString("pos.mods"));
                    String mod = scanner.nextLine().trim();
                    if (!mod.isEmpty()) it.addModifier(mod);
                    o.addItem(it);
                }
            } catch (Exception e) { System.out.println(I18n.getString("msg.invalid") + " " + (e.getMessage() != null ? e.getMessage() : "")); }
        }
        if (o.getItems().isEmpty()) { System.out.println(I18n.getString("pos.emptyCancel")); return; }
        handlePayment(o);
    }

    private void handlePayment(Order o) {
        BigDecimal total = o.calculateTotal();
        System.out.println(I18n.getString("pos.total") + total);
        System.out.println(I18n.getString("pos.payMethods"));
        System.out.print(I18n.getString("menu.select"));
        String payOpt = scanner.nextLine().trim();
        mka.coffeshopmanagementsystem.model.payment.Payment p = null;
        try {
            if (payOpt.equals("1")) {
                System.out.print(I18n.getString("pos.tendered"));
                BigDecimal ten = new BigDecimal(scanner.nextLine().trim());
                p = new Cash(total, ten);
                if (ten.compareTo(total) >= 0) System.out.println(I18n.getString("pos.change") + ten.subtract(total));
            } else if (payOpt.equals("2")) p = new CreditCard(total, UUID.randomUUID().toString());
            else if (payOpt.equals("3")) p = new Transfer(total, "ACC-SIM");
        } catch (Exception e) { System.out.println(I18n.getString("msg.invalid")); return; }

        if (p != null) {
            shop.getOrderManager().processPayment(o, p);
            o.getItems().forEach(it -> {
                if (it.getProduct().getRecipe() != null) {
                    it.getProduct().getRequiredIngredients().forEach((ing, qty) -> {
                        Ingredient s = shop.getInventoryManager().findIngredientByName(ing.getName());
                        if (s != null) s.reduceStock(qty.multiply(new BigDecimal(it.getQuantity())));
                    });
                }
            });
            System.out.println(I18n.getString("pos.invUpdated"));
        }
    }

    private void kitchenModule() {
        while (true) {
            System.out.println("\n" + I18n.getString("mod.kitchen"));
            System.out.println(I18n.getString("kit.viewActive"));
            System.out.println(I18n.getString("kit.changeStatus"));
            System.out.println(I18n.getString("menu.back"));
            System.out.print(I18n.getString("menu.select"));
            String opt = scanner.nextLine().trim();
            if (opt.equals("0")) break;
            if (opt.equals("1")) listActiveOrders();
            else if (opt.equals("2")) updateOrderStatus();
            pause();
        }
    }

    private void listActiveOrders() {
        System.out.println(I18n.getString("kit.activeTitle"));
        shop.getOrderManager().getOrders().stream()
            .filter(o -> o.getStatus() != OrderStatus.SERVED)
            .forEach(o -> System.out.println(" - [ID: " + o.getOrderId().substring(0,8) + I18n.getString("kit.client") + o.getCustomer().getName() + I18n.getString("kit.state") + o.getStatus()));
    }

    private void updateOrderStatus() {
        listActiveOrders();
        System.out.print(I18n.getString("kit.enterId"));
        String id = scanner.nextLine().trim();
        shop.getOrderManager().getOrders().stream().filter(o -> o.getOrderId().startsWith(id)).findFirst().ifPresentOrElse(o -> {
            System.out.println(I18n.getString("kit.selStatus"));
            String s = scanner.nextLine().trim();
            if (s.equals("1")) o.updateStatus(OrderStatus.PREPARING);
            else if (s.equals("2")) o.updateStatus(OrderStatus.READY);
            else if (s.equals("3")) o.updateStatus(OrderStatus.SERVED);
        }, () -> System.out.println(I18n.getString("kit.notFound")));
    }

    private void catalogModule() {
        while (true) {
            System.out.println("\n" + I18n.getString("mod.catalog"));
            System.out.println(I18n.getString("cat.list"));
            System.out.println(I18n.getString("cat.add"));
            System.out.println(I18n.getString("cat.del"));
            System.out.println(I18n.getString("cat.edit"));
            System.out.println(I18n.getString("cat.editRecipe"));
            System.out.println(I18n.getString("menu.back"));
            System.out.print(I18n.getString("menu.select"));
            String opt = scanner.nextLine().trim();
            if (opt.equals("0")) break;
            if (opt.equals("1")) shop.getCatalogManager().getProducts().forEach(p -> System.out.println(" - [" + p.getProductId() + "] " + p.getName() + " | $" + p.getPrice()));
            else if (opt.equals("2")) addNewProduct();
            else if (opt.equals("3")) deleteProduct();
            else if (opt.equals("4")) editProduct();
            else if (opt.equals("5")) editProductRecipe();
            else System.out.println(I18n.getString("msg.invalid"));
            pause();
        }
    }

    private void editProductRecipe() {
        System.out.print(I18n.getString("cat.enterId")); String id = scanner.nextLine().trim();
        System.out.println(I18n.getString("cat.editRecipeMsg"));
        List<ProductIngredient> recipe = new ArrayList<>();
        while (true) {
            Ingredient ing = new Ingredient();
            System.out.print(I18n.getString("cat.ingName")); ing.setName(scanner.nextLine().trim());
            System.out.print(I18n.getString("cat.ingUnit")); String rawUnit = scanner.nextLine().trim();
            System.out.print(I18n.getString("cat.ingQty") + rawUnit + "): ");
            try {
                BigDecimal rawQty = new BigDecimal(scanner.nextLine().trim());
                mka.coffeshopmanagementsystem.utils.UnitConverter.ConversionResult norm = mka.coffeshopmanagementsystem.utils.UnitConverter.normalize(rawUnit, rawQty);
                ing.setUnit(norm.unit);
                ProductIngredient pi = new ProductIngredient();
                pi.setIngredient(ing);
                pi.setQuantityNeeded(norm.quantity);
                recipe.add(pi);
            } catch (Exception e) {
                System.out.println(I18n.getString("msg.invalid") + " " + (e.getMessage() != null ? e.getMessage() : ""));
            }
            System.out.print(I18n.getString("cat.askAnother"));
            if (!scanner.nextLine().trim().toLowerCase().matches("s|y")) break;
        }
        try {
            shop.getCatalogManager().updateProductRecipe(id, recipe);
            System.out.println(I18n.getString("cat.recipeUpdated"));
        } catch (Exception e) {
            System.out.println(I18n.getString("msg.invalid") + " " + (e.getMessage() != null ? e.getMessage() : ""));
        }
    }

    private void editProduct() {
        System.out.print(I18n.getString("cat.enterId")); String id = scanner.nextLine().trim();
        System.out.print(I18n.getString("cat.price"));
        try {
            shop.getCatalogManager().updateProductPrice(id, new BigDecimal(scanner.nextLine().trim()));
            System.out.println(I18n.getString("cat.updated"));
        } catch (Exception e) {
            System.out.println(I18n.getString("msg.invalid") + " " + (e.getMessage() != null ? e.getMessage() : ""));
        }
    }

    private void deleteProduct() {
        System.out.print(I18n.getString("cat.delId")); String id = scanner.nextLine().trim();
        try {
            shop.getCatalogManager().removeProduct(id);
            System.out.println(I18n.getString("inv.deleted"));
        } catch (Exception e) {
            System.out.println(I18n.getString("msg.invalid") + " " + (e.getMessage() != null ? e.getMessage() : ""));
        }
    }

    private void addNewProduct() {
        Product p = new Product();
        System.out.print(I18n.getString("cat.name")); p.setName(scanner.nextLine());
        System.out.print(I18n.getString("cat.price")); p.setPrice(new BigDecimal(scanner.nextLine().trim()));
        System.out.print(I18n.getString("cat.askRecipe"));
        if (scanner.nextLine().trim().toLowerCase().matches("s|y")) {
            List<ProductIngredient> recipe = new ArrayList<>();
            while (true) {
                Ingredient ing = new Ingredient();
                System.out.print(I18n.getString("cat.ingName")); ing.setName(scanner.nextLine().trim());
                System.out.print(I18n.getString("cat.ingUnit")); ing.setUnit(scanner.nextLine().trim());
                System.out.print(I18n.getString("cat.ingQty") + ing.getUnit() + "): ");
                ProductIngredient pi = new ProductIngredient();
                pi.setIngredient(ing);
                pi.setQuantityNeeded(new BigDecimal(scanner.nextLine().trim()));
                recipe.add(pi);
                System.out.print(I18n.getString("cat.askAnother"));
                if (!scanner.nextLine().trim().toLowerCase().matches("s|y")) break;
            }
            p.setRecipe(recipe);
        }
        shop.getCatalogManager().addProduct(p);
    }

    private void inventoryModule() {
        while (true) {
            System.out.println("\n" + I18n.getString("mod.inventory"));
            System.out.println(I18n.getString("inv.view"));
            System.out.println(I18n.getString("inv.add"));
            System.out.println(I18n.getString("inv.edit"));
            System.out.println(I18n.getString("inv.del"));
            System.out.println(I18n.getString("menu.back"));
            System.out.print(I18n.getString("menu.select"));
            String opt = scanner.nextLine().trim();
            if (opt.equals("0")) break;
            if (opt.equals("1")) listInventory();
            else if (opt.equals("2")) addNewIngredient();
            else if (opt.equals("3")) editIngredientStock();
            else if (opt.equals("4")) deleteIngredient();
            else System.out.println(I18n.getString("msg.invalid"));
            pause();
        }
    }

    private void listInventory() {
        Inventory inv = shop.getInventoryManager().getInventory();
        if (inv != null && inv.getIngredients() != null && !inv.getIngredients().isEmpty()) {
            inv.getIngredients().forEach(i -> System.out.println(" - [" + i.getIngredientId() + "] " + i.getName() + I18n.getString("inv.avail") + i.getStockQuantity() + " " + i.getUnit()));
        } else System.out.println(I18n.getString("inv.empty"));
    }

    private void addNewIngredient() {
        Ingredient ing = new Ingredient();
        ing.setIngredientId(UUID.randomUUID().toString().substring(0,8));
        System.out.print(I18n.getString("cat.ingName")); ing.setName(scanner.nextLine().trim());
        System.out.print(I18n.getString("inv.unit")); ing.setUnit(scanner.nextLine().trim());
        System.out.print(I18n.getString("inv.qty"));
        try {
            ing.setStockQuantity(new BigDecimal(scanner.nextLine().trim()));
            shop.getInventoryManager().addIngredient(ing);
            System.out.println(I18n.getString("inv.added"));
        } catch (Exception e) { System.out.println(I18n.getString("msg.invalid") + " " + (e.getMessage() != null ? e.getMessage() : "")); }
    }

    private void editIngredientStock() {
        System.out.print(I18n.getString("inv.enterId")); String id = scanner.nextLine().trim();
        System.out.print(I18n.getString("inv.unit")); String rawUnit = scanner.nextLine().trim();
        System.out.print(I18n.getString("inv.qty"));
        try {
            BigDecimal rawQty = new BigDecimal(scanner.nextLine().trim());
            mka.coffeshopmanagementsystem.utils.UnitConverter.ConversionResult norm = mka.coffeshopmanagementsystem.utils.UnitConverter.normalize(rawUnit, rawQty);
            shop.getInventoryManager().updateIngredientStock(id, norm.quantity);
            System.out.println(I18n.getString("inv.updated"));
        } catch (Exception e) { System.out.println(I18n.getString("msg.invalid") + " " + (e.getMessage() != null ? e.getMessage() : "")); }
    }

    private void deleteIngredient() {
        System.out.print(I18n.getString("inv.delId")); String id = scanner.nextLine().trim();
        try {
            shop.getInventoryManager().removeIngredient(id);
            System.out.println(I18n.getString("inv.deleted"));
        } catch (Exception e) { System.out.println(I18n.getString("msg.invalid") + " " + (e.getMessage() != null ? e.getMessage() : "")); }
    }

    private void hrModule() {
        while (true) {
            System.out.println("\n" + I18n.getString("mod.hr"));
            System.out.println(I18n.getString("hr.list"));
            System.out.println(I18n.getString("hr.hire"));
            System.out.println(I18n.getString("hr.fire"));
            System.out.println(I18n.getString("hr.assign"));
            System.out.println(I18n.getString("menu.back"));
            System.out.print(I18n.getString("menu.select"));
            String opt = scanner.nextLine().trim();
            if (opt.equals("0")) break;
            if (opt.equals("1")) shop.getHrManager().getEmployees().forEach(e -> System.out.println(" - [" + e.getId() + "] " + e.getRole() + ": " + e.getName() + (e.getShift() != null ? " (Turno: " + e.getShift() + ")" : "")));
            else if (opt.equals("2")) hireEmployee();
            else if (opt.equals("3")) fireEmployee();
            else if (opt.equals("4")) assignShift();
            else System.out.println(I18n.getString("msg.invalid"));
            pause();
        }
    }

    private void hireEmployee() {
        System.out.print(I18n.getString("hr.name")); String n = scanner.nextLine().trim();
        System.out.print(I18n.getString("hr.role")); String r = scanner.nextLine().trim();
        String id = UUID.randomUUID().toString().substring(0,8);
        Employee e = r.equals("1") ? new Cashier(id, n) : r.equals("2") ? new Waiter(id, n) : r.equals("3") ? new Barista(id, n) : r.equals("4") ? new Chef(id, n) : null;
        if (e != null) { shop.getHrManager().addEmployee(e); System.out.println(I18n.getString("hr.registered") + n); }
        else System.out.println(I18n.getString("msg.invalid"));
    }

    private void fireEmployee() {
        System.out.print(I18n.getString("hr.enterId")); String id = scanner.nextLine().trim();
        try {
            shop.getHrManager().removeEmployee(id);
            System.out.println(I18n.getString("hr.fired"));
        } catch (Exception e) {
            System.out.println(I18n.getString("msg.invalid") + " " + (e.getMessage() != null ? e.getMessage() : ""));
        }
    }

    private void assignShift() {
        System.out.print(I18n.getString("hr.enterId")); String id = scanner.nextLine().trim();
        System.out.print(I18n.getString("hr.enterShift")); String shift = scanner.nextLine().trim();
        try {
            shop.getHrManager().assignShift(id, shift);
            System.out.println(I18n.getString("hr.shift"));
        } catch (Exception e) {
            System.out.println(I18n.getString("msg.invalid") + " " + (e.getMessage() != null ? e.getMessage() : ""));
        }
    }

    private void floorModule() {
        while (true) {
            System.out.println("\n" + I18n.getString("mod.floor"));
            System.out.println(I18n.getString("flr.viewTbl"));
            System.out.println(I18n.getString("flr.addTbl"));
            System.out.println(I18n.getString("flr.delTbl"));
            System.out.println(I18n.getString("flr.viewMac"));
            System.out.println(I18n.getString("flr.addMac"));
            System.out.println(I18n.getString("flr.delMac"));
            System.out.println(I18n.getString("menu.back"));
            System.out.print(I18n.getString("menu.select"));
            String opt = scanner.nextLine().trim();
            if (opt.equals("0")) break;
            
            try {
                if (opt.equals("1")) {
                    shop.getFloorManager().getTables().forEach(t -> System.out.println(" - " + t.getId() + " " + (t.isState() ? I18n.getString("flr.occ") : I18n.getString("flr.free"))));
                }
                else if (opt.equals("2")) {
                    Table t = new Table(); t.setId("Mesa-" + (shop.getFloorManager().getTables().size() + 1));
                    shop.getFloorManager().addTable(t); System.out.println(I18n.getString("flr.tblAdded"));
                }
                else if (opt.equals("3")) {
                    System.out.print(I18n.getString("flr.enterId")); String id = scanner.nextLine().trim();
                    shop.getFloorManager().removeTable(id); System.out.println(I18n.getString("flr.deleted"));
                }
                else if (opt.equals("4")) {
                    shop.getFloorManager().getMachines().forEach(m -> System.out.println(" - " + m.getBrand() + " " + (m.isState() ? "[ON]" : "[OFF]")));
                }
                else if (opt.equals("5")) {
                    mka.coffeshopmanagementsystem.model.floor.Machine m = new mka.coffeshopmanagementsystem.model.floor.Machine();
                    System.out.print(I18n.getString("flr.brand")); m.setBrand(scanner.nextLine().trim());
                    shop.getFloorManager().addMachine(m); System.out.println(I18n.getString("flr.macAdded"));
                }
                else if (opt.equals("6")) {
                    System.out.print(I18n.getString("flr.brand")); String brand = scanner.nextLine().trim();
                    shop.getFloorManager().removeMachine(brand); System.out.println(I18n.getString("flr.deleted"));
                }
                else {
                    System.out.println(I18n.getString("msg.invalid"));
                }
            } catch (Exception e) {
                System.out.println(I18n.getString("msg.invalid") + " " + (e.getMessage() != null ? e.getMessage() : ""));
            }
            pause();
        }
    }

    private void financeModule() {
        System.out.println("\n" + I18n.getString("mod.finance"));
        Map<String, BigDecimal> r = shop.getFinanceManager().generateZReport(LocalDate.now(), shop.getOrderManager().getOrders());
        System.out.println(I18n.getString("fin.breakdown"));
        
        System.out.println(I18n.getString("fin.orders") + r.getOrDefault("ORDERS", BigDecimal.ZERO).intValue());
        System.out.println(I18n.getString("fin.subtotal") + r.getOrDefault("SUBTOTAL", BigDecimal.ZERO));
        System.out.println(I18n.getString("fin.tax") + r.getOrDefault("TAX", BigDecimal.ZERO));
        System.out.println("---------------------------------");
        
        r.forEach((k, v) -> {
            if (!k.equals("ORDERS") && !k.equals("SUBTOTAL") && !k.equals("TAX")) {
                System.out.println("   " + k + " : $" + v);
            }
        });
        
        BigDecimal total = r.entrySet().stream()
            .filter(e -> !e.getKey().equals("ORDERS") && !e.getKey().equals("SUBTOTAL") && !e.getKey().equals("TAX"))
            .map(Map.Entry::getValue)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        System.out.println("---------------------------------");
        System.out.println(I18n.getString("fin.total") + total);
        pause();
    }

    public static void main(String[] args) {
        new MainView().start();
    }
}
