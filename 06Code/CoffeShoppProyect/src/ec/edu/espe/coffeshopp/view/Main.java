/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espe.coffeshopp.view;

/**
 *
 * @author Mateo Artieda,MKA Programer, @ESPE
 */
import ec.edu.espe.coffeshopp.model.Barista;
import ec.edu.espe.coffeshopp.model.Cash;
import ec.edu.espe.coffeshopp.model.Cashier;
import ec.edu.espe.coffeshopp.model.Chef;
import ec.edu.espe.coffeshopp.model.CreditCard;
import ec.edu.espe.coffeshopp.model.Customer;
import ec.edu.espe.coffeshopp.model.Employee;
import ec.edu.espe.coffeshopp.model.Order;
import ec.edu.espe.coffeshopp.model.OrderItem;
import ec.edu.espe.coffeshopp.model.Payment;
import ec.edu.espe.coffeshopp.model.PaymentProcessor;
import ec.edu.espe.coffeshopp.model.Product;
import ec.edu.espe.coffeshopp.model.Transfer;
import ec.edu.espe.coffeshopp.model.Waiter;
import java.util.ArrayList;
import java.util.Scanner;


import java.util.ArrayList;
import java.util.Scanner;
import ec.edu.espe.coffeshopp.model.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Employee> employees = new ArrayList<>();
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<Order> orders = new ArrayList<>();

        int option;

        do {

            System.out.println("\n===== COFFEE SHOP SYSTEM =====");
            System.out.println("1. Crear Customer");
            System.out.println("2. Crear Employee");
            System.out.println("3. Crear Product");
            System.out.println("4. Crear Order");
            System.out.println("5. Mostrar Customers");
            System.out.println("6. Mostrar Employees");
            System.out.println("7. Mostrar Products");
            System.out.println("8. Mostrar Orders");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opcion: ");

            option = sc.nextInt();
            sc.nextLine();

            switch(option) {

                case 1:

                    System.out.print("Ingrese ID: ");
                    int customerId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Ingrese nombre: ");
                    String customerName = sc.nextLine();

                    System.out.print("Ingrese email: ");
                    String customerEmail = sc.nextLine();

                    Customer customer = new Customer(
                            customerId,
                            customerName,
                            customerEmail,
                            0
                    );

                    customers.add(customer);

                    System.out.println("Customer creado correctamente");
                    break;

                case 2:

                    System.out.println("1. Chef");
                    System.out.println("2. Cashier");
                    System.out.println("3. Barista");
                    System.out.println("4. Waiter");
                    System.out.print("Seleccione tipo: ");

                    int type = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Ingrese ID: ");
                    int empId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Ingrese nombre: ");
                    String empName = sc.nextLine();

                    System.out.print("Ingrese email: ");
                    String empEmail = sc.nextLine();

                    System.out.print("Ingrese salario: ");
                    double salary = sc.nextDouble();

                    Employee employee = null;

                    if(type == 1) {
                        employee = new Chef(empId, empName, empEmail, salary);
                    }
                    else if(type == 2) {
                        employee = new Cashier(empId, empName, empEmail, salary);
                    }
                    else if(type == 3) {
                        employee = new Barista(empId, empName, empEmail, salary);
                    }
                    else if(type == 4) {
                        employee = new Waiter(empId, empName, empEmail, salary);
                    }

                    employees.add(employee);

                    System.out.println("Employee creado correctamente");
                    break;

                case 3:

                    System.out.print("Ingrese nombre del producto: ");
                    String productName = sc.nextLine();

                    System.out.print("Ingrese precio: ");
                    double productPrice = sc.nextDouble();

                    Product product = new Product(productName, productPrice);

                    products.add(product);

                    System.out.println("Producto creado correctamente");
                    break;

                case 4:

                    if(customers.isEmpty()) {
                        System.out.println("Debe crear un customer primero");
                        break;
                    }

                    if(products.isEmpty()) {
                        System.out.println("Debe crear un producto primero");
                        break;
                    }

                    System.out.println("Customers disponibles:");

                    for(int i = 0; i < customers.size(); i++) {
                        System.out.println(i + ". " + customers.get(i).getName());
                    }

                    System.out.print("Seleccione customer: ");
                    int customerIndex = sc.nextInt();

                    Customer selectedCustomer = customers.get(customerIndex);

                    Order order = new Order(orders.size() + 1, selectedCustomer);

                    System.out.println("Productos disponibles:");

                    for(int i = 0; i < products.size(); i++) {
                        System.out.println(i + ". " + products.get(i).getName());
                    }

                    System.out.print("Seleccione producto: ");
                    int productIndex = sc.nextInt();

                    System.out.print("Cantidad: ");
                    int quantity = sc.nextInt();

                    OrderItem item = new OrderItem(
                            products.get(productIndex),
                            quantity
                    );

                    order.addItem(item);

                    orders.add(order);

                    System.out.println("Orden creada");
                    System.out.println("Total: $" + order.calculateTotal());

                    System.out.println("1. Cash");
                    System.out.println("2. Credit Card");
                    System.out.println("3. Transfer");
                    System.out.print("Seleccione metodo de pago: ");

                    int paymentOption = sc.nextInt();
                    sc.nextLine();

                    Payment payment = null;

                    if(paymentOption == 1) {
                        payment = new Cash(order.calculateTotal());
                    }
                    else if(paymentOption == 2) {

                        System.out.print("Ingrese numero de tarjeta: ");
                        String card = sc.nextLine();

                        payment = new CreditCard(
                                order.calculateTotal(),
                                card
                        );
                    }
                    else if(paymentOption == 3) {

                        System.out.print("Ingrese banco: ");
                        String bank = sc.nextLine();

                        payment = new Transfer(
                                order.calculateTotal(),
                                bank
                        );
                    }

                    PaymentProcessor processor = new PaymentProcessor();
                    processor.processPayment(payment);

                    break;

                case 5:

                    for(Customer c : customers) {
                        System.out.println("Customer: " + c.getName());
                    }

                    break;

                case 6:

                    for(Employee e : employees) {
                        System.out.println("Employee: " + e.getName());
                    }

                    break;

                case 7:

                    for(Product p : products) {
                        System.out.println(
                                "Producto: "
                                + p.getName()
                                + " - $"
                                + p.getPrice()
                        );
                    }

                    break;

                case 8:

                    for(Order o : orders) {
                        System.out.println(
                                "Order total: $"
                                + o.calculateTotal()
                        );
                    }

                    break;

                case 9:

                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opcion invalida");
            }

        } while(option != 9);
    }
}


