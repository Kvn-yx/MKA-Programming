package mka.coffeshopmanagementsystem.model.persistence.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import mka.coffeshopmanagementsystem.model.persistence.repository.IRepository;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 * Generic MongoDB repository implementation.
 * Acts as the base reference for teammates to implement specific model persistence.
 * 
 * @param <T> The type of the entity
 * @author Anthony Aimacaña, MKA programmer, @ESPE
 */
public class MongoRepository<T> implements IRepository<T> {

    protected final MongoCollection<T> collection;
    protected final Class<T> entityClass;

    public MongoRepository(String collectionName, Class<T> entityClass) {
        this.entityClass = entityClass;
        MongoDatabase database = MongoDBConnection.getDatabase();
        this.collection = database.getCollection(collectionName, entityClass);
    }

    @Override
    public List<T> findAll() {
        return collection.find().into(new ArrayList<>());
    }

    @Override
    public void saveAll(List<T> entities) {
        collection.deleteMany(new Document());
        if (entities != null && !entities.isEmpty()) {
            collection.insertMany(entities);
        }
    }

    @Override
    public void add(T entity) {
        if (entity != null) {
            collection.insertOne(entity);
        }
    }

    @Override
    public T findById(String id) {
        return collection.find(Filters.eq("id", id)).first();
    }

    @Override
    public void delete(String id) {
        collection.deleteOne(Filters.eq("id", id));
    }
}
