package mka.coffeshopmanagementsystem.model.persistence.repository;

import java.util.List;

/**
 * Generic repository interface for basic CRUD operations.
 * @param <T> The type of the entity
 */
public interface IRepository<T> {
    List<T> findAll();
    void saveAll(List<T> entities);
    void add(T entity);
    // You can add findById, remove, etc. as needed.
}