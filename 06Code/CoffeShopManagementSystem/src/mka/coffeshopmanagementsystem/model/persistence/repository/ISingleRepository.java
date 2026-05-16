package mka.coffeshopmanagementsystem.model.persistence.repository;

/**
 * Generic repository interface for managing a single aggregate root entity.
 * @param <T> The type of the entity
 */
public interface ISingleRepository<T> {
    T load();
    void save(T entity);
}