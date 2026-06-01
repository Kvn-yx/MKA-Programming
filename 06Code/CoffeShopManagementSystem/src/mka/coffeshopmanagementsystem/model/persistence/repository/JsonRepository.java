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

    @Override
    public T findById(String id) {
        // Generic implementation would need reflection or a common interface.
        // For now, throwing exception as the managers will use internal Maps for optimization.
        throw new UnsupportedOperationException("Not implemented yet. Managers use internal cache.");
    }

    @Override
    public void delete(String id) {
        // Generic implementation would need reflection or a common interface.
        throw new UnsupportedOperationException("Not implemented yet. Managers use internal cache.");
    }
}