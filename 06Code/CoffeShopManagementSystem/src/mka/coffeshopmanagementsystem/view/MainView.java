package mka.coffeshopmanagementsystem.view;

import com.google.gson.reflect.TypeToken;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import mka.coffeshopmanagementsystem.controller.MainController;
import mka.coffeshopmanagementsystem.model.floor.Machine;
import mka.coffeshopmanagementsystem.model.floor.Table;
import mka.coffeshopmanagementsystem.model.inventory.Inventory;
import mka.coffeshopmanagementsystem.model.inventory.Product;
import mka.coffeshopmanagementsystem.model.management.CatalogManager;
import mka.coffeshopmanagementsystem.model.management.CoffeeShop;
import mka.coffeshopmanagementsystem.model.management.FinanceManager;
import mka.coffeshopmanagementsystem.model.management.FloorManager;
import mka.coffeshopmanagementsystem.model.management.HRManager;
import mka.coffeshopmanagementsystem.model.management.InventoryManager;
import mka.coffeshopmanagementsystem.model.management.OrderManager;
import mka.coffeshopmanagementsystem.model.order.Order;
import mka.coffeshopmanagementsystem.model.order.OrderStatus;
import mka.coffeshopmanagementsystem.model.people.Employee;
import mka.coffeshopmanagementsystem.model.persistence.repository.JsonRepository;
import mka.coffeshopmanagementsystem.model.persistence.repository.JsonSingleRepository;
import mka.coffeshopmanagementsystem.utils.ConsoleTable;
import mka.coffeshopmanagementsystem.utils.I18n;

/**
 * Terminal-based view for the Coffee Shop Management System.
 * Handles all user input and output formatting.
 * 
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class MainView {
    private final Scanner scanner = new Scanner(System.in);

    public void showWelcome() {
        System.out.println("=========================================");
        System.out.println("        NEBULA X COFFEE SYSTEM           ");
        System.out.println("=========================================");
        System.out.println(I18n.getString("msg.init"));
    }

    public String promptLanguage() {
        System.out.println("\nSelect Language / Seleccione Idioma:");
        System.out.println("1. English");
        System.out.println("2. Español");
        System.out.print("> ");
        return scanner.nextLine().trim();
    }

    public String showMainMenu() {
        System.out.println("\n=========================================");
        System.out.println(I18n.getString("menu.title").toUpperCase());
        System.out.println("=========================================");
        for (int i = 1; i <= 7; i++) {
            System.out.println(i + ". " + I18n.getString("menu.opt" + i));
        }
        System.out.println("0. " + I18n.getString("menu.opt0"));
        System.out.println("=========================================");
        System.out.print(I18n.getString("menu.select") + " ");
        return scanner.nextLine().trim();
    }

    public String showPosMenu() {
        System.out.println("\n--- " + I18n.getString("mod.pos").toUpperCase() + " ---");
        System.out.println("1. " + I18n.getString("pos.newOrder"));
        System.out.println("0. " + I18n.getString("menu.back"));
        System.out.print(I18n.getString("menu.select") + " ");
        return scanner.nextLine().trim();
    }

    public String showKitchenMenu() {
        System.out.println("\n--- " + I18n.getString("mod.kitchen").toUpperCase() + " ---");
        System.out.println("1. " + I18n.getString("kit.viewActive"));
        System.out.println("2. " + I18n.getString("kit.changeStatus"));
        System.out.println("0. " + I18n.getString("menu.back"));
        System.out.print(I18n.getString("menu.select") + " ");
        return scanner.nextLine().trim();
    }

    public String showCatalogMenu() {
        System.out.println("\n--- " + I18n.getString("mod.catalog").toUpperCase() + " ---");
        System.out.println("1. " + I18n.getString("cat.list"));
        System.out.println("2. " + I18n.getString("cat.add"));
        System.out.println("3. " + I18n.getString("cat.del"));
        System.out.println("4. " + I18n.getString("cat.edit"));
        System.out.println("5. " + I18n.getString("cat.editRecipe"));
        System.out.println("0. " + I18n.getString("menu.back"));
        System.out.print(I18n.getString("menu.select") + " ");
        return scanner.nextLine().trim();
    }

    public String showInventoryMenu() {
        System.out.println("\n--- " + I18n.getString("mod.inventory").toUpperCase() + " ---");
        System.out.println("1. " + I18n.getString("inv.view"));
        System.out.println("2. " + I18n.getString("inv.add"));
        System.out.println("3. " + I18n.getString("inv.edit"));
        System.out.println("4. " + I18n.getString("inv.del"));
        System.out.println("0. " + I18n.getString("menu.back"));
        System.out.print(I18n.getString("menu.select") + " ");
        return scanner.nextLine().trim();
    }

    public String showHrMenu() {
        System.out.println("\n--- " + I18n.getString("mod.hr").toUpperCase() + " ---");
        System.out.println("1. " + I18n.getString("hr.list"));
        System.out.println("2. " + I18n.getString("hr.hire"));
        System.out.println("3. " + I18n.getString("hr.fire"));
        System.out.println("4. " + I18n.getString("hr.assign"));
        System.out.println("0. " + I18n.getString("menu.back"));
        System.out.print(I18n.getString("menu.select") + " ");
        return scanner.nextLine().trim();
    }

    public String showFloorMenu() {
        System.out.println("\n--- " + I18n.getString("mod.floor").toUpperCase() + " ---");
        System.out.println("1. " + I18n.getString("flr.viewTbl"));
        System.out.println("2. " + I18n.getString("flr.addTbl"));
        System.out.println("3. " + I18n.getString("flr.delTbl"));
        System.out.println("4. " + I18n.getString("flr.viewMac"));
        System.out.println("5. " + I18n.getString("flr.addMac"));
        System.out.println("6. " + I18n.getString("flr.delMac"));
        System.out.println("0. " + I18n.getString("menu.back"));
        System.out.print(I18n.getString("menu.select") + " ");
        return scanner.nextLine().trim();
    }

    public String promptString(String message) {
        System.out.print(message + " ");
        return scanner.nextLine().trim();
    }

    public int promptInt(String message) {
        System.out.print(message + " ");
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public BigDecimal promptBigDecimal(String message) {
        System.out.print(message + " ");
        try {
            return new BigDecimal(scanner.nextLine().trim());
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    public void showErrorMessage(String message) {
        System.out.println("\n[ERROR] " + message);
    }

    public void showMessage(String message) {
        System.out.println("\n" + message);
    }

    public void pause() {
        System.out.println("\n" + I18n.getString("msg.pause"));
        scanner.nextLine();
    }

    public void showProductList(List<Product> products) {
        System.out.println("\n--- " + I18n.getString("pos.menuTitle") + " ---");
        for (int i = 0; i < products.size(); i++) {
            System.out.println((i + 1) + ". " + products.get(i).getName() + " - $" + products.get(i).getPrice());
        }
        System.out.println("0. " + I18n.getString("pos.finish"));
    }

    public void showOrderTotal(BigDecimal total) {
        System.out.println("\n" + I18n.getString("pos.total") + total);
    }

    public String showPaymentMethods() {
        System.out.println(I18n.getString("pos.payMethods"));
        System.out.print(I18n.getString("menu.select") + " ");
        return scanner.nextLine().trim();
    }

    public void showActiveOrders(List<Order> orders) {
        ConsoleTable table = new ConsoleTable("ID", I18n.getString("kit.client"), I18n.getString("kit.state"));
        table.setTitle(I18n.getString("kit.activeTitle"));
        orders.stream()
            .filter(o -> o.getStatus() != OrderStatus.SERVED)
            .forEach(o -> table.addRow(o.getOrderId().substring(0, 8), o.getCustomer().getName(), o.getStatus().toString()));
        table.print();
    }

    public String showStatusOptions() {
        System.out.println(I18n.getString("kit.selStatus"));
        System.out.println("1. PREPARING");
        System.out.println("2. READY");
        System.out.println("3. SERVED");
        System.out.print("> ");
        return scanner.nextLine().trim();
    }

    public void showCatalog(List<Product> products) {
        ConsoleTable table = new ConsoleTable("ID", "NAME", "PRICE");
        table.setTitle(I18n.getString("mod.catalog"));
        products.forEach(p -> table.addRow(p.getProductId(), p.getName(), "$" + p.getPrice()));
        table.print();
    }

    public void showInventory(Inventory inventory) {
        ConsoleTable table = new ConsoleTable("ID", "NAME", "STOCK", "UNIT", "ALERT LIMIT");
        table.setTitle(I18n.getString("mod.inventory"));
        if (inventory != null && inventory.getIngredients() != null) {
            inventory.getIngredients().forEach(i -> 
                table.addRow(i.getIngredientId(), i.getName(), i.getStockQuantity().toString(), i.getUnit(), i.getMinimumAlertQuantity().toString()));
        }
        table.print();
    }

    public void showEmployees(List<Employee> employees) {
        ConsoleTable table = new ConsoleTable("ID", "ROLE", "NAME", "SHIFT");
        table.setTitle(I18n.getString("mod.hr"));
        employees.forEach(e -> 
            table.addRow(e.getId(), e.getRole(), e.getName(), (e.getShift() != null ? e.getShift() : "-")));
        table.print();
    }

    public String showRoleOptions() {
        System.out.println(I18n.getString("hr.role"));
        System.out.println("1. Cashier");
        System.out.println("2. Waiter");
        System.out.println("3. Barista");
        System.out.println("4. Chef");
        System.out.print("> ");
        return scanner.nextLine().trim();
    }

    public void showTables(List<Table> tables) {
        ConsoleTable table = new ConsoleTable("ID", "STATUS");
        table.setTitle(I18n.getString("flr.viewTbl"));
        tables.forEach(t -> 
            table.addRow(t.getId(), (t.isState() ? I18n.getString("flr.occ") : I18n.getString("flr.free"))));
        table.print();
    }

    public void showMachines(List<Machine> machines) {
        ConsoleTable table = new ConsoleTable("BRAND", "STATUS");
        table.setTitle(I18n.getString("flr.viewMac"));
        machines.forEach(m -> 
            table.addRow(m.getBrand(), (m.isState() ? "[ON]" : "[OFF]")));
        table.print();
    }

    public void showZReport(Map<String, BigDecimal> report) {
        ConsoleTable table = new ConsoleTable("ITEM", "VALUE");
        table.setTitle(I18n.getString("mod.finance"));
        
        table.addRow(I18n.getString("fin.orders"), report.getOrDefault("ORDERS", BigDecimal.ZERO).toString());
        table.addRow(I18n.getString("fin.subtotal"), "$" + report.getOrDefault("SUBTOTAL", BigDecimal.ZERO));
        table.addRow(I18n.getString("fin.tax"), "$" + report.getOrDefault("TAX", BigDecimal.ZERO));
        
        report.forEach((k, v) -> {
            if (!k.equals("ORDERS") && !k.equals("SUBTOTAL") && !k.equals("TAX")) {
                table.addRow(k, "$" + v);
            }
        });
        
        BigDecimal total = report.entrySet().stream()
            .filter(e -> !e.getKey().equals("ORDERS") && !e.getKey().equals("SUBTOTAL") && !e.getKey().equals("TAX"))
            .map(Map.Entry::getValue)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        table.addRow("---", "---");
        table.addRow(I18n.getString("fin.total"), "$" + total);
        table.print();
    }

    public void showGoodbye() {
        System.out.println("\n" + I18n.getString("msg.exit"));
        System.out.println("=========================================");
    }

    public static void main(String[] args) {
        CoffeeShop shop = new CoffeeShop();
        shop.setName("NebulaX Coffee");
        
        // Initialize managers with repositories
        shop.setOrderManager(new OrderManager(new JsonRepository<>("data/orders.json", new TypeToken<ArrayList<Order>>(){}.getType())));
        shop.setCatalogManager(new CatalogManager(new JsonRepository<>("data/catalog.json", new TypeToken<ArrayList<Product>>(){}.getType())));
        shop.setInventoryManager(new InventoryManager(new JsonSingleRepository<>("data/inventory.json", Inventory.class)));
        shop.setFloorManager(new FloorManager(new JsonSingleRepository<>("data/floor.json", FloorManager.class)));
        shop.setHrManager(new HRManager(new JsonRepository<>("data/employees.json", new TypeToken<ArrayList<Employee>>(){}.getType())));
        shop.setFinanceManager(new FinanceManager(new JsonRepository<>("data/finance_history.json", new TypeToken<ArrayList<ZReportSnapshot>>(){}.getType())));

        // Load data
        shop.getOrderManager().loadData();
        shop.getCatalogManager().loadData();
        shop.getInventoryManager().loadData();
        shop.getFloorManager().loadData();
        shop.getHrManager().loadData();
        shop.getFinanceManager().loadData();
        shop.linkOrdersAndCatalog();

        MainView view = new MainView();
        MainController controller = new MainController(view, shop);
        controller.start();
    }
}
