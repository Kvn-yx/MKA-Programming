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
        this.gson = GsonProvider.createGson();
    }

    
    public JsonFileManager(Gson gson) {
        this.gson = gson;
    }

    
    public void saveToFile(String filePath, Object data) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            throw new RuntimeException(String.format(mka.coffeshopmanagementsystem.utils.I18n.getString("model.json.err_save"), filePath, e.getMessage()), e);
        }
    }

    
    public <T> T loadFromFile(String filePath, Class<T> typeClass) {
        try (Reader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, typeClass);
        } catch (IOException | JsonSyntaxException e) {
            throw new RuntimeException(String.format(mka.coffeshopmanagementsystem.utils.I18n.getString("model.json.err_load"), filePath, e.getMessage()), e);
        }
    }
    
    
    public <T> T loadFromFile(String filePath, Type type) {
        try (Reader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, type);
        } catch (IOException | JsonSyntaxException e) {
            throw new RuntimeException(String.format(mka.coffeshopmanagementsystem.utils.I18n.getString("model.json.err_load_gen"), filePath, e.getMessage()), e);
        }
    }
}
