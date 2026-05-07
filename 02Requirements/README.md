# Software Requirements Specification (SRS)

## Coffee Shop Management System (Enterprise Edition)

## 1. Introduction

### 1.1 Purpose

The purpose of this Software Requirements Specification (SRS) is to provide a comprehensive, detailed, and unequivocal description of the Coffee Shop Management System. This document serves as the definitive source of truth for all stakeholders, software engineers, and Quality Assurance (QA) personnel regarding the system's functional capabilities, operational constraints, and non-functional guarantees.

### 1.2 Scope

The system is an Point of Sale (POS) and internal management platform architected specifically for physical coffee shop operations. The core modules encompass:

- **Order & Checkout Lifecycle:** End-to-end order creation, modification, dynamic tax/discount calculations, and multi-modal secure payment processing.
- **Production Workflow:** Real-time, event-driven synchronization of orders to Kitchen/Barista Display Systems (KDS).
- **Inventory Control:** Automated, recipe-driven stock deduction to ensure precise inventory parity.
- **Floor & Human Resources Management:** Dynamic table assignment, role-based access control, and shift operations management.

_Exclusions:_ The system currently excludes automated payroll generation, advanced corporate general ledger accounting, and consumer-facing mobile applications.

### 1.3 Glossary & Definitions

| Term             | Definition                                                                                                                                                        |
| :--------------- | :---------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **POS**          | Point of Sale; the hardware/software ecosystem where retail transactions are executed.                                                                            |
| **KDS**          | Kitchen Display System; digital screens used by production staff to track orders.                                                                                 |
| **Product**      | A finalized, sellable item presented to the customer (e.g., "Vanilla Latte").                                                                                     |
| **Ingredient**   | A raw material tracked perpetually in the inventory (e.g., "Whole Milk", "Coffee Beans").                                                                         |
| **Recipe**       | The exact quantitative mapping of Ingredients required to synthesize one unit of a Product.                                                                       |
| **Tokenization** | The cryptographic process of substituting sensitive payment data (e.g., credit card PANs) with non-sensitive equivalents (tokens) for secure transit and storage. |

---

## 2. System Overview

### 2.1 User Characteristics (Actors)

- **Customer:** The initiator of orders. Profiles may be anonymous or fully registered for loyalty tracking and digital receipt delivery.
- **Cashier:** The primary operator of the POS terminal, responsible for order intake, customer interaction, and payment processing.
- **Barista / Chef:** The production staff who monitor the KDS, fulfill product recipes, and trigger order status updates.
- **Waiter:** Floor staff responsible for table management, order delivery, and state updates.
- **Store Manager:** The administrative authority holding privileges for inventory auditing, HR oversight, and financial reporting.
- **Supplier:** An external entity (or integrated B2B interface) supplying inventory replenishment.

### 2.2 Operating Environment

- **Data Persistence:** Phase 1 utilizes file-based persistence mechanisms (e.g., structured JSON or CSV files) for transactional and inventory data storage.

### 2.3 Design & Implementation Constraints

- **Architectural Pattern:** The implementation MUST adhere strictly to the Model-View-Controller (MVC) or Clean Architecture principles, ensuring absolute decoupling via specialized Domain Managers (`OrderManager`, `InventoryManager`, `HRManager`, `FloorManager`).
- **Financial Data Types:** All monetary and quantitative calculations MUST utilize arbitrary-precision decimal types (e.g., `BigDecimal`). The use of floating-point primitives (`double`, `float`) for currency is strictly prohibited to prevent rounding anomalies.
- **Security Standard:** To maintain PCI-DSS compliance, the system MUST NEVER capture, process, or store raw Credit Card numbers. All electronic payments must be routed through compliant third-party Payment Processors, persisting only opaque Payment Tokens.

---

## 3. Functional Requirements

### 3.1 Order Management

| ID        | Title                   | Description                                                                                                                                     | Priority |
| :-------- | :---------------------- | :---------------------------------------------------------------------------------------------------------------------------------------------- | :------- |
| **FR-01** | **Order Creation**      | The Cashier must be able to initiate new orders, add/remove `OrderItem` entities, and link the transaction to an optional Customer profile.     | Critical |
| **FR-02** | **Dynamic Calculation** | The system must calculate the Subtotal, apply conditional discounts, and compute exact taxes (`taxRate`) to formulate the final Total. | Critical |

**Acceptance Criteria (FR-01 & FR-02):**

- Order totals update within < 100ms of any line item modification.
- Mathematical formula: `Final Total = (Subtotal - Discount) + Tax`.
- All financial values render strictly to two decimal places.

### 3.2 Payment Processing

| ID        | Title                    | Description                                                                                                       | Priority |
| :-------- | :----------------------- | :---------------------------------------------------------------------------------------------------------------- | :------- |
| **FR-03** | **Multi-Modal Checkout** | Support for Cash, Tokenized Credit Card, and Bank Transfer payments via an abstract `PaymentProcessor` interface. | Critical |

**Acceptance Criteria (FR-03):**

- Cash payments must prompt for `amountTendered` and display the exact change due.
- Credit transactions must asynchronously verify the `paymentToken`.
- Order state transitions to `PAID` exclusively upon successful callback from the `PaymentProcessor`.

### 3.3 Production Workflow

| ID        | Title                | Description                                                                                                | Priority |
| :-------- | :------------------- | :--------------------------------------------------------------------------------------------------------- | :------- |
| **FR-04** | **KDS Queue**        | Paid orders must instantly manifest on the Barista/Chef KDS dashboards in chronological order.             | High     |
| **FR-05** | **Floor Assignment** | The `FloorManager` must allow Waiters to claim ownership of `Table` entities and monitor occupancy states. | Medium   |

**Acceptance Criteria (FR-04 & FR-05):**

- Production staff can toggle statuses: `PENDING` -> `PREPARING` -> `READY`.
- Waiters receive notifications when table-assigned orders transition to `READY`.

### 3.4 Inventory Automation

| ID        | Title                | Description                                                                                                                        | Priority |
| :-------- | :------------------- | :--------------------------------------------------------------------------------------------------------------------------------- | :------- |
| **FR-06** | **Recipe Deduction** | Transitioning an order to `PREPARING` triggers the `InventoryManager` to deduct the precise `quantityNeeded` of each `Ingredient`. | Critical |

**Acceptance Criteria (FR-06):**

- Pre-check validation: The POS must visibly alert the Cashier if requested items exceed available ingredient stock prior to checkout.

---

## 4. Non-Functional Requirements (NFRs)

| Category        | ID     | Requirement Description                                                   | Target Metric                           |
| :-------------- | :----- | :------------------------------------------------------------------------ | :-------------------------------------- |
| **Performance** | NFR-01 | Latency for primary POS interactions (item addition, UI navigation).      | < 500ms (95th percentile)               |
| **Scalability** | NFR-02 | Concurrent request handling without service degradation.                  | 50+ simultaneous POS/Tablet clients     |
| **Security**    | NFR-03 | Sensitive data protection regarding Employee access and Customer details. | Encrypted at rest; RBAC enforced        |
| **Reliability** | NFR-04 | System availability during operational business hours (06:00 - 22:00).    | 99.9% Uptime                            |
| **Resilience**  | NFR-05 | POS offline capability for cache-based order intake.                      | Support for localized Cash transactions |

---

## 5. Enterprise Business Rules

- **BR-01 (State Enforcement):** An `Order` object is strictly prohibited from entering the `PREPARING` state until the associated `Payment` completes and the status is finalized as `PAID`.
- **BR-02 (Inventory Integrity):** Ingredient quantities are immutable below zero. Reaching 0 automatically triggers an "Out of Stock" lock on dependent Products.
- **BR-03 (Financial Accuracy):** No monetary or inventory-weight variable shall be declared as a floating-point primitive. Arbitrary-precision types are mandatory.

---

## 6. Phase 2 Roadmap & Future Enhancements

- **Database Integration:** Migration from the localized file-based storage architecture to an Enterprise NoSQL Database Management System to facilitate high concurrency and distributed data management.
- **Predictive Analytics:** Implementation of AI-driven supply chain alerts to proactively notify Managers to re-order ingredients based on historical sales velocity.
