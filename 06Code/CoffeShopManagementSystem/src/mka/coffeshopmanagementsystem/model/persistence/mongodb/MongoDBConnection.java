package mka.coffeshopmanagementsystem.model.persistence.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.concurrent.TimeUnit;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * Singleton database connection manager for MongoDB.
 * Integrates the POJO Codec Registry to allow direct mapping of Java classes to BSON.
 * Features a fast connection timeout to fail fast if MongoDB is not running.
 * 
 * @author Anthony Aimacaña, MKA programmer, @ESPE
 */
public class MongoDBConnection {
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static final String CONNECTION_STRING = "mongodb://coffeeshop:coffeeshopMKA@157.137.223.54:27017/coffeeshop?authSource=coffeeshop";
    private static final String DATABASE_NAME = "coffeeshop";

    private MongoDBConnection() {}

    public static synchronized MongoDatabase getDatabase() {
        if (database == null) {
            CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
            );

            MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(CONNECTION_STRING))
                .codecRegistry(pojoCodecRegistry)
                .applyToClusterSettings(builder -> 
                    builder.serverSelectionTimeout(2000, TimeUnit.MILLISECONDS)
                )
                .build();

            mongoClient = MongoClients.create(settings);
            database = mongoClient.getDatabase(DATABASE_NAME);
        }
        return database;
    }

    public static boolean isMongoRunning() {
        try {
            MongoDatabase db = getDatabase();
            db.runCommand(new Document("ping", 1));
            return true;
        } catch (Exception e) {
            close();
            return false;
        }
    }

    public static synchronized void close() {
        if (mongoClient != null) {
            try {
                mongoClient.close();
            } catch (Exception ignored) {}
            mongoClient = null;
            database = null;
        }
    }
}
