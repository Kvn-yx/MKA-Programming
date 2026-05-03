# Especificación de Requisitos del Sistema

## Coffee Shop Management System

---

## 1. Introducción

### 1.1 Propósito
Este documento describe los requisitos funcionales y no funcionales del sistema de gestión para una cafetería. Su objetivo es definir claramente el comportamiento esperado del sistema para desarrollo, validación y mantenimiento.

### 1.2 Alcance
El sistema permitirá:
* Gestión de pedidos
* Procesamiento de pagos
* Control de inventario
* Interacción entre empleados, clientes y proveedores

### 1.3 Definiciones
* **Pedido (Order):** Conjunto de productos solicitados por un cliente.
* **Item:** Producto individual dentro de un pedido.
* **Inventario:** Control de stock de productos.

---

## 2. Descripción General

### 2.1 Perspectiva del sistema
Sistema centralizado que interactúa con:
* Clientes
* Empleados (Cajero, Barista)
* Proveedores

### 2.2 Actores del sistema
* Cliente
* Cajero
* Barista
* Proveedor

### 2.3 Suposiciones
* El sistema será usado en una cafetería física.
* Los pagos pueden ser en efectivo, tarjeta o gift card.

---

## 3. Requisitos Funcionales

### 3.1 Gestión de pedidos
* **RF-01: Crear pedido** - El sistema debe permitir al cliente realizar un pedido.
* **RF-02: Agregar productos** - El sistema debe permitir añadir productos a un pedido especificando la cantidad.
* **RF-03: Calcular total** - El sistema debe calcular automáticamente el total del pedido.
* **RF-04: Consultar estado** - El cliente puede ver el estado del pedido.

### 3.2 Gestión de pagos
* **RF-05: Procesar pago** - El cajero debe poder procesar pagos.
* **RF-06: Validar pago** - El sistema debe validar el método de pago.
* **RF-07: Generar recibo** - El sistema debe generar un comprobante del pago.
* **RF-08: Aplicar descuento (opcional)** - El sistema puede aplicar descuentos a pedidos.

### 3.3 Preparación de pedidos
* **RF-09: Ver pedidos** - El barista debe visualizar pedidos pendientes.
* **RF-10: Preparar producto** - El barista debe preparar los productos solicitados.
* **RF-11: Actualizar estado** - El sistema debe actualizar el estado del pedido.

### 3.4 Inventario
* **RF-12: Actualizar inventario** - El sistema debe actualizar stock al vender productos.
* **RF-13: Consultar stock** - El sistema debe permitir consultar disponibilidad.

### 3.5 Proveedores
* **RF-14: Registrar suministro** - El proveedor puede suministrar productos.
* **RF-15: Actualizar stock** - El sistema debe incrementar inventario al recibir productos.

---

## 4. Requisitos No Funcionales

* **4.1 Rendimiento:** El sistema debe responder en menos de 2 segundos en operaciones comunes.
* **4.2 Usabilidad:** Interfaz simple, intuitiva y accesible para usuarios sin conocimientos técnicos.
* **4.3 Seguridad:** Validación de pagos y protección de datos de clientes.
* **4.4 Disponibilidad:** El sistema debe estar disponible durante el horario de operación.
* **4.5 Escalabilidad:** Debe soportar múltiples pedidos simultáneamente.

---

## 5. Reglas de Negocio

* **RN-01:** Un pedido debe tener al menos un producto.
* **RN-02:** Un pedido debe pagarse antes de completarse.
* **RN-03:** El inventario no puede ser negativo.
* **RN-04:** Cada pedido pertenece a un solo cliente.
* **RN-05:** Un producto debe tener precio mayor a 0.

---

## 6. Casos de Uso (Resumen)

| Actor | Caso de uso |
| :--- | :--- |
| Cliente | Realizar pedido |
| Cliente | Consultar estado |
| Cajero | Procesar pago |
| Barista | Preparar pedido |
| Proveedor | Suministrar productos |

---

## 7. Restricciones
* El sistema debe ejecutarse en entorno local o web.
* Debe ser compatible con dispositivos básicos (PC o tablet).

## 8. Futuras mejoras
* Integración con app móvil.
* Sistema de notificaciones.
* Programa de fidelización avanzado.
