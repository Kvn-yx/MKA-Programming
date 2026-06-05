package mka.coffeshopmanagementsystem.model.persistence.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import mka.coffeshopmanagementsystem.model.persistence.repository.ISingleRepository;
import org.bson.Document;

/**
 * Generic MongoDB repository implementation for a single aggregate root entity.
 * Uses a single document with a constant ID to manage global states like Inventory.
 * 
 * @param <T> The type of the entity
 * @author Anthony Aimacaña, MKA programmer, @ESPE
 */
public class MongoSingleRepository<T> implements ISingleRepository<T> {

    private final MongoCollection<T> collection;
    private final Class<T> entityClass;
    private static final String SINGLETON_ID = "GLOBAL_STATE";

    public MongoSingleRepository(String collectionName, Class<T> entityClass) {
        this.entityClass = entityClass;
        MongoDatabase database = MongoDBConnection.getDatabase();
        this.collection = database.getCollection(collectionName, entityClass);
    }

    @Override
    public T load() {
        return collection.find(new Document("_id", SINGLETON_ID)).first();
    }

    @Override
    public void save(T entity) {
        if (entity != null) {
            Document filter = new Document("_id", SINGLETON_ID);
            collection.replaceOne(filter, entity, new ReplaceOptions().upsert(true));
        }
    }
}
