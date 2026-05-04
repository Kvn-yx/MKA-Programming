# System Requirements Specification

## Coffee Shop Management System

---

## 1. Introduction

### 1.1 Purpose
This document describes the functional and non-functional requirements of the management system for a coffee shop. Its objective is to clearly define the expected behavior of the system for development, validation, and maintenance.

### 1.2 Scope
The system will allow:
* Order management
* Payment processing
* Inventory control
* Interaction between employees, customers, and suppliers

### 1.3 Definitions
* **Order:** Set of products requested by a customer.
* **Item:** Individual product within an order.
* **Inventory:** Product stock control.

---

## 2. General Description

### 2.1 System Perspective
Centralized system that interacts with:
* Customers
* Employees (Cashier, Barista)
* Suppliers

### 2.2 System Actors
* Customer
* Cashier
* Barista
* Supplier

### 2.3 Assumptions
* The system will be used in a physical coffee shop.
* Payments can be made in cash, by card, or with a gift card.

---

## 3. Functional Requirements

### 3.1 Order Management
* **FR-01: Create order** - The system must allow the customer to place an order.
* **FR-02: Add products** - The system must allow adding products to an order, specifying the quantity.
* **FR-03: Calculate total** - The system must automatically calculate the total of the order.
* **FR-04: Check status** - The customer can view the status of the order.

### 3.2 Payment Management
* **FR-05: Process payment** - The cashier must be able to process payments.
* **FR-06: Validate payment** - The system must validate the payment method.
* **FR-07: Generate receipt** - The system must generate a payment receipt.
* **FR-08: Apply discount (optional)** - The system can apply discounts to orders.

### 3.3 Order Preparation
* **FR-09: View orders** - The barista must visualize pending orders.
* **FR-10: Prepare product** - The barista must prepare the requested products.
* **FR-11: Update status** - The system must update the order status.

### 3.4 Inventory
* **FR-12: Update inventory** - The system must update stock when selling products.
* **FR-13: Check stock** - The system must allow checking availability.

### 3.5 Suppliers
* **FR-14: Register supply** - The supplier can supply products.
* **FR-15: Update stock** - The system must increase inventory upon receiving products.

---

## 4. Non-Functional Requirements

* **4.1 Performance:** The system must respond in less than 2 seconds for common operations.
* **4.2 Usability:** Simple, intuitive interface, accessible to users without technical knowledge.
* **4.3 Security:** Payment validation and customer data protection.
* **4.4 Availability:** The system must be available during operating hours.
* **4.5 Scalability:** Must support multiple concurrent orders.

---

## 5. Business Rules

* **BR-01:** An order must have at least one product.
* **BR-02:** An order must be paid before completion.
* **BR-03:** Inventory cannot be negative.
* **BR-04:** Each order belongs to a single customer.
* **BR-05:** A product must have a price greater than 0.

---

## 6. Use Cases (Summary)

| Actor | Use Case |
| :--- | :--- |
| Customer | Place order |
| Customer | Check status |
| Cashier | Process payment |
| Barista | Prepare order |
| Supplier | Supply products |

---

## 7. Constraints
* The system must run in a local or web environment.
* It must be compatible with basic devices (PC or tablet).

## 8. Future Enhancements
* Integration with a mobile app.
* Notification system.
* Advanced loyalty program.
