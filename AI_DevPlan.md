# 📋 PLAN DE DESARROLLO ASISTIDO POR IA - COFFEE SHOP MANAGEMENT SYSTEM

**Contexto del Proyecto para el Agente IA:**
*   **Lenguaje:** Java (Proyecto basado en Ant para NetBeans).
*   **Arquitectura:** MVC Estricto (Model-View-Controller) con persistencia basada en archivos JSON.
*   **Estado Actual:** Toda la estructura de clases del Modelo y los Managers ya ha sido generada en el paquete `mka.coffeshopmanagementsystem.model.*`. Las clases cuentan con atributos, constructores, getters y setters, pero la lógica de los métodos está marcada con `// TODO: implement`.
*   **Capa de Persistencia:** La clase `JsonFileManager` (ubicada en `model.persistence`) **ya está completamente implementada** usando la librería `Gson (v2.14.0)`.

---

## 🎯 FASE 1: IMPLEMENTACIÓN DE LÓGICA DE NEGOCIO (MANAGERS)

El equipo de 3 desarrolladores se dividirá la implementación de los paquetes del modelo de la siguiente manera. **Instrucción para la IA:** Cuando se te asigne un Desarrollador, enfócate ÚNICAMENTE en implementar los métodos vacíos de sus clases asignadas.

### 👨‍💻 Desarrollador 1: Módulo de Inventario y Catálogo (Inventory & Catalog)
**Objetivo:** Permitir que el sistema administre el menú de productos y controle el stock de ingredientes físicos.
**Clases a implementar:**
1.  **`CatalogManager`**:
    *   Implementar `loadData()` y `saveData()` para leer/escribir `List<Product>` en `data/catalog.json`. *(Nota para la IA: Usar `TypeToken<ArrayList<Product>>(){}.getType()` con el `JsonFileManager`)*.
    *   Implementar `addProduct(Product)` y `removeProduct(String)`.
2.  **`InventoryManager`**:
    *   Implementar lectura/escritura del objeto `Inventory` en `data/inventory.json`.
    *   Implementar `checkStockFor(...)` (verificar si hay suficientes ingredientes para una receta) y `deductStockFor(...)` (restar las cantidades exactas tras un pedido).
    *   Implementar `overrideStock(...)` para auditorías manuales (requerimiento F4.4).
3.  **Clases de Entidad asociadas**: Programar lógica interna si es necesaria en `Product` (ej. `getRequiredIngredients()`) e `Ingredient` (ej. `reduceStock()`).

### 👨‍💻 Desarrollador 2: Módulo de Operaciones de Piso y RRHH (Floor & HR)
**Objetivo:** Administrar los empleados, las mesas del local y la maquinaria.
**Clases a implementar:**
1.  **`HRManager`**:
    *   Implementar lectura/escritura de `List<Employee>` en `data/employees.json`. *(Nota para la IA: Al deserializar clases abstractas o con herencia usando Gson, asegúrate de proveer o sugerir un RuntimeTypeAdapterFactory si es necesario, basándote en el campo `role`)*.
    *   Implementar `assignShift()`.
2.  **`FloorManager`**:
    *   Implementar lectura/escritura de `List<Table>` y `List<Machine>` en `data/floor.json`.
    *   Implementar lógica para `assignTable(Waiter, Table)`.
3.  **Clases de Entidad asociadas**:
    *   Implementar los métodos `occupy()` y `free()` en `Table`.
    *   Implementar `turnOn()` y `operate()` en `Machine`.
    *   Definir una salida básica por consola o retorno de String para el método abstracto `performOperation()` en `Cashier`, `Chef`, `Barista` y `Waiter`.

### 👨‍💻 Desarrollador 3: Motor de POS, Pedidos y Finanzas (Order, Payment & Finance)
**Objetivo:** El núcleo del sistema. Cobros, manejo de carritos de compra y reportes de finanzas.
**Clases a implementar:**
1.  **`OrderManager`**:
    *   Implementar lectura/escritura de `List<Order>` en `data/orders.json`.
    *   Implementar `createOrder()` y `processPayment()`.
    *   Implementar `syncOfflineOrders()` (cambiar el estado `isSynced` a true para los pedidos pendientes de sincronización).
2.  **`FinanceManager`**:
    *   Implementar `generateZReport()`: Debe iterar sobre las órdenes (recibidas por parámetro), filtrar las que tienen estado `PAID` y que coincidan con la fecha, y retornar un `Map<String, BigDecimal>` con el total ganado por cada tipo de pago (CASH, TRANSFER, CREDIT_CARD).
3.  **Clases de Entidad asociadas**:
    *   **`Order`**: Implementar `calculateSubtotal()` (suma de los items), `calculateTax()`, y `calculateTotal()`. 
    *   **`OrderItem`**: Implementar `calculateSubtotal()` (precio del producto * cantidad) y manejo de `modifiers` (ej. "Sin azúcar").
    *   **`Payment` (y sus hijas)**: Programar `processPayment()` usando la interfaz `PaymentProcessor`.

---

## 🖥️ FASE 2: INTEGRACIÓN (CONTROLADOR Y VISTA)

*Esta fase inicia SOLO cuando la Fase 1 esté 100% testeada.*

1.  **Vista (`MainView`)**: Crear un menú interactivo en consola (usando `Scanner`) que exponga las opciones del sistema.
2.  **Controlador (`MainController`)**:
    *   Instanciar la clase principal `CoffeeShop`.
    *   En su constructor, inicializar todos los Managers, asignarles su `dataFilePath` correspondiente (ej. `"data/orders.json"`) y llamar a su método `loadData()`.
    *   Crear un ciclo de vida que escuche la selección de la Vista y orqueste a los Managers. Antes de salir, asegurarse de llamar a `saveData()` en todos.

---

## ⚠️ REGLAS ESTRICTAS PARA LOS AGENTES IA
*Al actuar sobre este proyecto, debes adherirte incondicionalmente a estas normas:*

1.  **Persistencia Congelada (FROZEN):** La clase `JsonFileManager.java` y su dependencia con la librería Gson (v2.14.0) **NO DEBEN SER MODIFICADAS**. Está prohibido alterar esta clase o sugerir cambios de dependencias para persistencia, a menos que un error técnico bloqueante e insalvable haga **estrictamente necesario** alterar esta capa de infraestructura.
2.  **No Modificar Firmas Existentes:** No cambies los nombres de los atributos, los constructores o las firmas de los métodos ya declarados en los modelos, a menos que un error arquitectónico crítico te obligue a hacerlo.
3.  **Reutilizar `JsonFileManager`:** Bajo ninguna circunstancia escribas código nativo `FileWriter` o `FileReader` dentro de los Managers. Usa siempre la instancia existente de `JsonFileManager`.
4.  **BigDecimal Obligatorio:** Todos los cálculos de dinero, impuestos y cantidades de inventario deben procesarse estrictamente con `BigDecimal`. No uses `double` o `float`. Usa `BigDecimal.ZERO` o `.add()`, `.multiply()`.
5.  **Manejo de Errores Seguro:** Las lecturas de JSON pueden devolver `null`. En el método `loadData()` de los Managers, si la lectura falla o el archivo no existe, debes inicializar las listas vacías de forma segura (ej. `this.orders = new ArrayList<>();`) en lugar de permitir que la app crashee con un `NullPointerException`.
6.  **Preservar Comentarios:** No elimines los encabezados de NetBeans (`/* Click nbfs... */`) ni los créditos de los autores (ej. `@author Anthony Aimacaña...`) al modificar los archivos.