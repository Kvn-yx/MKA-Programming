# Software Requirements Specification (SRS)
## NebulaX Coffee Shop Management System (Enterprise Edition)

---

## 1. Introduction

### 1.1 Purpose
The purpose of this Software Requirements Specification (SRS) is to provide a comprehensive and detailed description of the NebulaX Coffee Shop Management System. It serves as the single source of truth for stakeholders, developers, and QA engineers regarding the system's functional capabilities, constraints, and non-functional guarantees. 

### 1.2 Scope
The system is an enterprise-grade Point of Sale (POS) and internal management platform designed for physical coffee shop locations. It covers:
* **Order & Checkout Lifecycle:** Order creation, modification, tax/discount calculation, and multi-modal payment processing.
* **Production Workflow:** Real-time synchronization of orders to Barista/Chef queues.
* **Inventory Control:** Automated stock deduction via precise recipe mapping.
* **Floor & HR Management:** Table assignments, employee roles, and shift operations.

The system will *not* currently handle payroll generation, advanced corporate accounting, or a consumer-facing mobile app (planned for Phase 2).

### 1.3 Glossary & Definitions
* **POS (Point of Sale):** Hardware/software system where sales are completed.
* **Product:** A sellable item (e.g., "Latte").
* **Ingredient:** A raw material tracked in the inventory (e.g., "Milk", "Coffee Beans").
* **Recipe (`ProductIngredient`):** The mapping of precise ingredient quantities required to produce one Product.
* **Tokenization:** Process of replacing sensitive payment data (like a credit card number) with a unique identification symbol (token) for secure processing.

---

## 2. Overall Description

### 2.1 User Characteristics (Actors)
* **Customer:** Initiates orders. Can be anonymous or registered (for loyalty points and email receipts).
* **Cashier:** Operates the POS terminal, takes customer orders, and processes payments.
* **Barista / Chef:** Views the preparation queue, prepares products according to recipes, and marks items as ready.
* **Waiter:** Assigned to tables, delivers orders to customers, and updates table statuses.
* **Store Manager:** Has administrative privileges to view inventory, manage employee shifts, and access end-of-day financial reports.
* **Supplier:** External entity (or system interface) that provides stock replenishment.

### 2.2 Operating Environment
* **Frontend/Client:** Modern Web Browser (Chrome/Safari) running on touch-screen POS terminals and tablets (for waiters).
* **Backend:** Cloud-hosted containerized services (Docker/Kubernetes).
* **Database:** File-based storage will be used for the current phase (e.g., JSON or CSV files) for local persistence of transactions and inventory.

### 2.3 Design & Implementation Constraints
* **Architecture:** Must follow an MVC/Clean Architecture pattern with decoupled managers (OrderManager, InventoryManager, HRManager, FloorManager).
* **Data Types:** All financial calculations must use arbitrary-precision decimal types (e.g., `BigDecimal` in Java or similar) to prevent floating-point rounding errors.
* **Security:** The system must not store raw Credit Card numbers, achieving PCI-DSS compliance by utilizing external Payment Processors and saving only Payment Tokens.

---

## 3. System Features (Functional Requirements)

This section uses the Agile-friendly format with detailed Acceptance Criteria.

### 3.1 Order Management
**FR-01: Create and Modify Orders**
* **Description:** The system shall allow a Cashier to create an order, associate it with an optional Customer profile, add/remove `OrderItem`s, and apply overall discounts.
* **Priority:** High / Critical
* **Acceptance Criteria:**
  1. Cashier can add products by category (Hot Beverages, Snacks, etc.).
  2. Order accurately updates its `calculateSubtotal()` in real-time.
  3. System permits assigning the order to an anonymous customer or looking up a registered customer via email.

**FR-02: Automated Tax and Total Calculation**
* **Description:** The system must calculate local taxes (`taxRate`) and deduct any applied `discount` to establish the final `calculateTotal()`.
* **Priority:** High / Critical
* **Acceptance Criteria:**
  1. Final Total = (Subtotal - Discount) + Tax.
  2. Values must be rounded strictly to 2 decimal places using standard financial rounding rules.

### 3.2 Payment Processing
**FR-03: Multi-modal Payment Checkout**
* **Description:** The system must support Cash, Credit Card, and Transfer payments via a standard `PaymentProcessor` interface.
* **Priority:** High / Critical
* **Acceptance Criteria:**
  1. Cashier can select the payment method.
  2. For Cash: System prompts for `amountTendered` and calculates change.
  3. For Credit Card: System captures the `paymentToken` from the external terminal and verifies the transaction.
  4. Order status transitions from `PENDING` to `PAID` only upon successful processor callback.

### 3.3 Production & Floor Workflow
**FR-04: Production Queue Management**
* **Description:** Paid orders must immediately appear on the Barista/Chef dashboard.
* **Priority:** High
* **Acceptance Criteria:**
  1. Orders display chronologically.
  2. Barista can update status to `PREPARING`, then `READY`.
  3. Waiters are notified when an order assigned to their `Table` is `READY`.

**FR-05: Table and Waiter Assignment**
* **Description:** The `FloorManager` must allow assigning a `Waiter` to a `Table` and managing table occupancy states.
* **Priority:** Medium
* **Acceptance Criteria:**
  1. Tables can be marked as `Occupied` or `Free`.
  2. Waiters can be linked to tables for a shift.

### 3.4 Inventory Management
**FR-06: Recipe-based Stock Deduction**
* **Description:** Upon an order transitioning to `PREPARING`, the `InventoryManager` must automatically deduct the exact `quantityNeeded` of each `Ingredient` required by the `Product`'s recipe.
* **Priority:** High / Critical
* **Acceptance Criteria:**
  1. If a Latte requires 200ml of Milk and 15g of Coffee, those exact amounts are subtracted from `stockQuantity`.
  2. **FR-06b (Pre-check):** Before checkout, the system must warn the cashier if an item lacks sufficient inventory.

---

## 4. Non-Functional Requirements (NFRs)

### 4.1 Performance & Scalability
* **NFR-01 (Response Time):** 95% of POS interactions (adding item, changing screen) must complete in under 500ms. Payment processing depends on the external gateway but must timeout after 15 seconds.
* **NFR-02 (Concurrency):** The backend must handle up to 50 concurrent POS/Tablet requests without degradation of service.

### 4.2 Security & Privacy
* **NFR-03 (PCI-DSS):** Raw credit card data must NEVER enter the application backend. Only secure tokens are permitted.
* **NFR-04 (Authentication):** Employee access requires unique PIN codes or credentials. Actions (like applying a 100% discount) must be logged and require Manager override.
* **NFR-05 (Data Privacy):** Customer emails must be encrypted at rest (GDPR compliance).

### 4.3 Reliability & Availability
* **NFR-06 (Uptime):** The system requires 99.9% uptime during defined store business hours (06:00 - 22:00).
* **NFR-07 (Offline Resilience):** The POS client should cache current open orders and allow cash transactions even if the internet connection to the cloud backend is temporarily lost, syncing upon reconnection.

### 4.4 Usability
* **NFR-08 (Training Time):** The UI must be intuitive enough that a new Cashier can complete a standard checkout flow with less than 30 minutes of training.

---

## 5. Business Rules

* **BR-01:** An `Order` cannot be marked as `PREPARING` until the `Payment` is successfully processed and status is `PAID`.
* **BR-02:** Inventory quantities cannot drop below zero. If an ingredient hits 0, all products requiring that ingredient must be automatically marked as "Out of Stock" on the POS.
* **BR-03:** All financial monetary values must use high-precision decimals (never floating-point primitives) to guarantee absolute mathematical accuracy in accounting.
* **BR-04:** An order must have at least one `OrderItem` before a payment can be initiated.

---

## 6. Architecture & System Constraints
* **Language & Framework:** Backend written in a strongly-typed language (e.g., Java/Spring Boot, C#/.NET, or Node.js/TypeScript).
* **Data Management:** File-based data must be structured consistently. No complex schema migrations are needed for the initial file-based phase.
* **Interfaces:** The `PaymentProcessor` must be an abstract interface to allow swapping banks/providers without changing core business logic.

---

## 7. Future Enhancements (Phase 2 Roadmap)
* **Database Integration:** Migración del almacenamiento actual basado en archivos a una base de datos No Relacional (NoSQL) para manejar alta concurrencia y gestión avanzada de datos.