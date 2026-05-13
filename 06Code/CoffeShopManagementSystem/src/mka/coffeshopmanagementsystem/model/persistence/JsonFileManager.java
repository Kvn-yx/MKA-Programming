/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

/**
 * Utility class to handle all JSON data persistence for the application.
 * Note for Developers: Requires the Gson library (gson-2.14.0.jar) to be added 
 * to the project Libraries in NetBeans. 
 * Instructions:
 * 1. In NetBeans, right-click on your project's "Libraries" folder.
 * 2. Select "Add JAR/Folder...".
 * 3. Navigate to the "lib" folder inside the project and select "gson-2.14.0.jar".
 * 
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class JsonFileManager {
    
    private final Gson gson;

    public JsonFileManager() {
        // Configuramos Gson con PrettyPrinting para que los JSON sean legibles por humanos
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Guarda cualquier objeto (o lista de objetos) en un archivo JSON.
     * 
     * @param filePath ruta o nombre del archivo (ej. "data/orders.json")
     * @param data el objeto o la colección a guardar
     */
    public void saveToFile(String filePath, Object data) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
            System.out.println("Datos guardados exitosamente en: " + filePath);
        } catch (IOException e) {
            System.err.println("Error al intentar guardar en " + filePath + ": " + e.getMessage());
        }
    }

    /**
     * Carga un objeto simple desde un archivo JSON (por ejemplo, un objeto Inventory).
     * 
     * @param <T> el tipo de clase
     * @param filePath ruta o nombre del archivo a leer
     * @param typeClass la clase del objeto esperado (ej. Inventory.class)
     * @return el objeto cargado, o null si el archivo no existe o falla la lectura
     */
    public <T> T loadFromFile(String filePath, Class<T> typeClass) {
        try (Reader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, typeClass);
        } catch (IOException | JsonSyntaxException e) {
            System.err.println("Error al intentar leer de " + filePath + ": " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Carga una lista o colección con tipos genéricos desde un archivo JSON.
     * 
     * Ejemplo de uso en los Managers:
     * Type listType = new TypeToken<ArrayList<Order>>(){}.getType();
     * List<Order> orders = jsonFileManager.loadFromFile("orders.json", listType);
     * 
     * @param <T> el tipo genérico
     * @param filePath ruta o nombre del archivo a leer
     * @param type el Type que representa la estructura genérica (usando TypeToken)
     * @return el objeto genérico cargado, o null si el archivo no existe o falla la lectura
     */
    public <T> T loadFromFile(String filePath, Type type) {
        try (Reader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, type);
        } catch (IOException | JsonSyntaxException e) {
            System.err.println("Error al leer datos genéricos de " + filePath + ": " + e.getMessage());
            return null;
        }
    }
}
