package cz.muni.fi.pa165.heroes.dao;

import java.io.Serializable;
import java.util.List;


/**
 * A generic DAO Interface for CRUD operations.
 *
 * @author Vojtech Krajnansky (423126)
 *
 * @param <T> A {@link Serializable} entity.
 */
public interface DAO<T extends Serializable> {

    /**
     * Gets all the entities available.
     *
     * @return A {@link List} of all existing entities.
     */
    List<T> findAll();

    /**
     * Gets a single entity by its ID, or null if such an entity doesn't exist.
     *
     * @param id The ID of the entity being accessed.
     *
     * @return The entity with given ID, or null if not found.
     */
    T findById(Long id);

    /**
     * Updates the entity's attributes.
     *
     * @param entity The updated entity.
     *
     * @return The updated entity.
     */
    T update(T entity);

    /**
     * Persists a new entity.
     *
     * @param entity The entity to be persisted.
     *
     * @return true if the operation succeeded, false otherwise.
     */
    boolean save(T entity);

    /**
     * Deletes an entity.
     *
     * @param entity The entity to be deleted.
     *
     * @return true if the operation succeeded, false otherwise.
     */
    boolean delete(T entity);

    /**
     * Deletes an entity with given ID.
     *
     * @param id The ID of the entity to be deleted.
     *
     * @return true if the operation succeeded, false otherwise.
     */
    boolean deleteById(Long id);
}
