# Enterprise Features Roster

## Coffee Shop Management System

This document outlines the systematic breakdown of capabilities (Features) engineered into the platform. This matrix is structurally aligned with the Software Requirements Specification (SRS) and forms the foundation for all UML Use Case modeling.

---

### Module 1: Point of Sale (POS) & Order Engine

| Feature ID | Nomenclature               | Technical Description                                                                    |
| :--------- | :------------------------- | :--------------------------------------------------------------------------------------- |
| **F1.1**   | **Rapid Order Creation**   | Command-Line Interface (CLI) in Phase 1, architected for rapid product addition.         |
| **F1.2**   | **Product Modifiers**      | Dynamic payload attachment for custom instructions (e.g., "No Sugar", "Oat Milk").       |
| **F1.3**   | **CRM Integration**        | Modular association of transactions to registered Customer profiles or anonymous tokens. |
| **F1.4**   | **Real-Time Financials**   | Synchronous computation of Subtotals, localized Tax protocols (IVA), and Final Totals.   |
| **F1.5**   | **Discount Authorization** | Manager-authorized logic for applying percentage or fixed-amount price reductions.       |
| **F1.6**   | **Offline Resilience**     | Local-cache fallbacks allowing uninterrupted order intake during network partitions.     |

### Module 2: Checkout & Payment Gateway

| Feature ID | Nomenclature              | Technical Description                                                                  |
| :--------- | :------------------------ | :------------------------------------------------------------------------------------- |
| **F2.1**   | **Multi-Modal Parsing**   | Algorithmic routing for Cash, Credit/Debit, and direct Bank Transfer protocols.        |
| **F2.2**   | **Visitor Pattern (Double Dispatch)** | Advanced polymorphic payment resolution, preventing generic type checks. |
| **F2.3**   | **Tender Calculation**    | Automated precision calculation for cash change issuance.                              |
| **F2.4**   | **Tokenized Security**    | PCI-DSS compliant interactions via opaque Payment Tokens; zero raw card retention.     |
| **F2.5**   | **Receipt Orchestration** | Generation of standardized physical prints and digital (email) transactional receipts. |

### Module 3: Kitchen Display System (KDS)

| Feature ID | Nomenclature            | Technical Description                                                                      |
| :--------- | :---------------------- | :----------------------------------------------------------------------------------------- |
| **F3.1**   | **State Transitions**   | Interactive toggles shifting entities through `PENDING`, `PREPARING`, and `READY` states.  |
| **F3.2**   | **SLA Monitoring**      | Visual degradation/alerting for tickets exceeding Service Level Agreement time thresholds. |

### Module 4: Perpetual Inventory Control

| Feature ID | Nomenclature               | Technical Description                                                                    |
| :--------- | :------------------------- | :--------------------------------------------------------------------------------------- |
| **F4.1**   | **Unit Standardization**   | Autonomous conversion of user-input units (e.g., kg to g, L to ml) to prevent recipe mismatches. |
| **F4.2**   | **Recipe Deduction**       | Algorithmic, micro-measurement subtraction of raw `Ingredients` upon order preparation.  |
| **F4.3**   | **Defensive Validation**   | Built-in safeguards preventing negative inventory states, null updates, or sub-zero pricing. |
| **F4.4**   | **Audit & Reconciliation** | Administrative ID-based CRUD interface for creating, editing, and deleting inventory items. |

### Module 5: Floor & Human Capital Management

| Feature ID | Nomenclature              | Technical Description                                                                  |
| :--------- | :------------------------ | :------------------------------------------------------------------------------------- |
| **F5.1**   | **Spatial Mapping**       | State-machine tracking of physical table statuses (`FREE`, `OCCUPIED`).                |
| **F5.2**   | **Staff Allocation**      | ID-based assignment of Waiters to Tables, and management of Employee shifts.           |
| **F5.3**   | **Machine Management**    | Real-time state toggling (ON/OFF) and ID-based tracking for coffee machinery.          |

### Module 6: Administrative Backoffice

| Feature ID | Nomenclature                 | Technical Description                                                                      |
| :--------- | :--------------------------- | :----------------------------------------------------------------------------------------- |
| **F6.1**   | **Menu Schema Manager**      | ID-based CRUD interfaces for products, including dynamic recipe updates.                   |
| **F6.2**   | **Financial Reconciliation** | End-of-Day (Z-Report) synthesis, aggregating total sales, orders, and taxes (IVA).         |
| **F6.3**   | **Repository Pattern**       | Total decoupling of JSON persistence infrastructure from core business logic (Managers).   |
| **F6.4**   | **Internationalization**     | 100% localization of all UI prompts and system exceptions (English/Spanish).               |
