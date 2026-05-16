package mka.coffeshopmanagementsystem.model.persistence.repository;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import mka.coffeshopmanagementsystem.model.persistence.JsonFileManager;

/**
 * A generic JSON repository implementation.
 * @param <T> The type of the entity
 */
public class JsonRepository<T> implements IRepository<T> {

    private final String filePath;
    private final Type listType;
    private final JsonFileManager jsonFileManager;

    public JsonRepository(String filePath, Type listType) {
        this.filePath = filePath;
        this.listType = listType;
        this.jsonFileManager = new JsonFileManager();
    }

    @Override
    public List<T> findAll() {
        List<T> data = jsonFileManager.loadFromFile(filePath, listType);
        return data != null ? data : new ArrayList<>();
    }

    @Override
    public void saveAll(List<T> entities) {
        jsonFileManager.saveToFile(filePath, entities);
    }

    @Override
    public void add(T entity) {
        List<T> entities = findAll();
        entities.add(entity);
        saveAll(entities);
    }
}