# Listado de Funcionalidades (Features List)
## NebulaX Coffee Shop Management System

Este documento desglosa las características principales (Features) del sistema, agrupadas por módulos. Este listado sirve como puente entre los Requerimientos (SRS) y los futuros Diagramas de Casos de Uso.

---

### Módulo 1: Punto de Venta (POS) y Gestión de Órdenes
* **F1.1 Creación de Órdenes Rápidas:** Interfaz táctil para agregar productos rápidamente al carrito.
* **F1.2 Modificadores de Producto:** Capacidad de personalizar productos (ej. "Sin azúcar", "Leche de almendras").
* **F1.3 Gestión de Clientes (CRM Básico):** Opción de vincular una orden a un cliente registrado (por email/teléfono) o procesar como cliente anónimo.
* **F1.4 Cálculo Automático:** Cálculo en tiempo real de Subtotal, Impuestos (Tax) y Total.
* **F1.5 Aplicación de Descuentos:** Capacidad para que el cajero aplique descuentos predefinidos o manuales (con autorización).
* **F1.6 Modo Offline (Resiliencia):** Capacidad de seguir tomando órdenes y guardarlas en caché local si se pierde la conexión a internet.

### Módulo 2: Procesamiento de Pagos (Checkout)
* **F2.1 Pago Multi-modal:** Soporte nativo para registrar pagos en Efectivo, Tarjeta de Crédito/Débito y Transferencia.
* **F2.2 Cálculo de Cambio:** Para pagos en efectivo, el sistema calcula automáticamente el cambio a devolver.
* **F2.3 Integración de Pasarela Segura:** Generación y almacenamiento de *Tokens de Pago* (sin guardar números de tarjeta) para cumplir con PCI-DSS.
* **F2.4 Generación de Recibos:** Emisión de tickets digitales (enviados por email) o impresos físicos tras un pago exitoso.

### Módulo 3: Pantalla de Producción (Kitchen/Barista Display System - KDS)
* **F3.1 Cola de Órdenes en Tiempo Real:** Visualización instantánea de las órdenes pagadas en las pantallas de preparación (Barista/Chef).
* **F3.2 Estados de Preparación:** Botones para que el staff marque los ítems como "Preparando" (`PREPARING`) y "Listo" (`READY`).
* **F3.3 Alertas de Retraso:** Indicadores visuales (colores) para órdenes que han superado el tiempo límite de preparación (SLA).

### Módulo 4: Control de Inventario
* **F4.1 Descuento Automático por Receta:** Al iniciar la preparación de un ítem, el sistema descuenta las cantidades exactas de ingredientes según su receta.
* **F4.2 Alertas de Stock Bajo:** Notificaciones automáticas cuando un ingrediente cruza el umbral mínimo configurado.
* **F4.3 Bloqueo de Venta por Falta de Stock:** Si un ingrediente llega a cero, los productos que lo requieren se marcan automáticamente como "Agotados" en el POS.
* **F4.4 Auditoría de Inventario:** Interfaz para que el Manager ajuste el stock manualmente (mermas, desperdicios, conteo físico).

### Módulo 5: Gestión de Sala y Personal (Floor & HR)
* **F5.1 Mapa de Mesas:** Representación visual del estado de las mesas (Libre, Ocupada, Reservada).
* **F5.2 Asignación de Meseros:** Vinculación de un mesero activo a mesas específicas.
* **F5.3 Autenticación de Empleados:** Acceso al sistema mediante PIN único o tarjeta RFID.
* **F5.4 Control de Roles (RBAC):** Restricción de funciones según el rol (Ej. Solo un Manager puede anular una orden pagada o hacer devoluciones).

### Módulo 6: Administración y Reportes (Backoffice)
* **F6.1 Gestión de Menú:** Interfaz (CRUD) para crear, editar o eliminar Productos, Categorías y sus Recetas.
* **F6.2 Corte de Caja (End of Day):** Reporte automatizado de ventas del día, separado por método de pago y por empleado.
* **F6.3 Reporte de Productos Estrella:** Análisis de los productos más y menos vendidos.

---
*Nota: Este listado es la base exacta para modelar los Actores y las relaciones `«include»` y `«extend»` en el Diagrama de Casos de Uso.*