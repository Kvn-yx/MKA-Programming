package mka.coffeshopmanagementsystem.model.persistence.repository;

import mka.coffeshopmanagementsystem.model.persistence.JsonFileManager;

/**
 * A generic JSON repository implementation for a single aggregate root.
 * @param <T> The type of the entity
 */
public class JsonSingleRepository<T> implements ISingleRepository<T> {

    private final String filePath;
    private final Class<T> typeClass;
    private final JsonFileManager jsonFileManager;

    public JsonSingleRepository(String filePath, Class<T> typeClass) {
        this.filePath = filePath;
        this.typeClass = typeClass;
        this.jsonFileManager = new JsonFileManager();
    }

    @Override
    public T load() {
        return jsonFileManager.loadFromFile(filePath, typeClass);
    }

    @Override
    public void save(T entity) {
        jsonFileManager.saveToFile(filePath, entity);
    }
}