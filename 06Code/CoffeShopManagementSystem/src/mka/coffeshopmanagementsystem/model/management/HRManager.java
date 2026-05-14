/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.management;

import java.util.ArrayList;
import java.util.List;
import mka.coffeshopmanagementsystem.model.people.*;
import mka.coffeshopmanagementsystem.model.persistence.JsonFileManager;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class HRManager {
    private List<Employee> employees;
    private String dataFilePath;

    public HRManager() {
        this.employees = new ArrayList<>();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String getDataFilePath() {
        return dataFilePath;
    }

    public void setDataFilePath(String dataFilePath) {
        this.dataFilePath = dataFilePath;
    }

    public void assignShift(Employee employee) {
        if (employee != null) {
            System.out.println("Turno asignado al empleado: " + employee.getName() + " (" + employee.getRole() + ")");
        }
    }

    public void loadData() {
        JsonFileManager jfm = getCustomJsonFileManager();
        Type listType = new TypeToken<ArrayList<Employee>>(){}.getType();
        List<Employee> loadedEmployees = jfm.loadFromFile(dataFilePath, listType);
        
        if (loadedEmployees != null) {
            this.employees = loadedEmployees;
            System.out.println("Datos de empleados cargados exitosamente (" + employees.size() + " empleados).");
        } else {
            this.employees = new ArrayList<>();
        }
    }

    public void saveData() {
        JsonFileManager jfm = getCustomJsonFileManager();
        jfm.saveToFile(dataFilePath, employees);
    }

    private JsonFileManager getCustomJsonFileManager() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Employee.class, new EmployeeAdapter())
                .setPrettyPrinting()
                .create();
        return new JsonFileManager(gson);
    }

    /**
     * Adaptador personalizado para manejar el polimorfismo de Employee basado en el campo 'role'.
     */
    private static class EmployeeAdapter implements JsonDeserializer<Employee>, JsonSerializer<Employee> {
        @Override
        public Employee deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonElement roleElement = jsonObject.get("role");
            
            if (roleElement == null) {
                throw new JsonParseException("Campo 'role' no encontrado en el JSON del empleado");
            }
            
            String role = roleElement.getAsString();
            switch (role) {
                case "Cashier":
                    return context.deserialize(json, Cashier.class);
                case "Chef":
                    return context.deserialize(json, Chef.class);
                case "Barista":
                    return context.deserialize(json, Barista.class);
                case "Waiter":
                    return context.deserialize(json, Waiter.class);
                default:
                    throw new JsonParseException("Rol desconocido: " + role);
            }
        }

        @Override
        public JsonElement serialize(Employee src, Type typeOfSrc, JsonSerializationContext context) {
            // El campo 'role' ya existe en las clases, así que la serialización estándar funciona,
            // pero nos aseguramos de usar la clase concreta para no perder campos específicos si los hubiera.
            return context.serialize(src, src.getClass());
        }
    }
}
