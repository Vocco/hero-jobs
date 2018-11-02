package cz.muni.fi.pa165.heroes.dao.impl;

import cz.muni.fi.pa165.heroes.dao.DAO;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * Base JPA {@link DAO} implementation.
 *
 * @author Vojtech Krajnansky (423126)
 *
 * @param <T> A {@link Serializable} entity.
 */
public abstract class JpaDAO<T extends Serializable> implements DAO<T> {


    // CLASS ATTRIBUTES

    protected Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;


    // CONSTRUCTORS

    public JpaDAO() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[1];
    }


    // DAO INTERFACE IMPLEMENTATION

    @Override
    public List<T> findAll() {
        entityManager.getTransaction().begin();

        List<T> all = entityManager
            .createQuery("SELECT t FROM " + entityClass.getSimpleName() + " t")
            .getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        return all;
    }

    @Override
    public T findById(Long id) {
        entityManager.getTransaction().begin();

        T entity = entityManager.find(entityClass, id);

        entityManager.getTransaction().commit();
        entityManager.close();

        return entity;
    }

    @Override
    public T update(T entity) {
        entityManager.getTransaction().begin();

        T updatedEntity = entityManager.merge(entity);

        entityManager.getTransaction().commit();
        entityManager.close();

        return updatedEntity;
    }

    @Override
    public boolean save(T entity) {
        boolean wasSuccess = true;

        entityManager.getTransaction().begin();

        try {
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        } catch (EntityExistsException ex) {
            wasSuccess = false;
        } finally {
            entityManager.close();
        }

        return wasSuccess;
    }

    @Override
    public boolean delete(T entity) {
        boolean wasSuccess = true;

        entityManager.getTransaction().begin();

        try {
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
        } catch (IllegalArgumentException ex) {
            wasSuccess = false;
        } finally {
            entityManager.close();
        }

        return wasSuccess;
    }

    @Override
    public boolean deleteById(Long id) {
        entityManager.getTransaction().begin();

        T entity = entityManager.find(entityClass, id);

        if (entity == null) {
            entityManager.getTransaction().commit();
            entityManager.close();
            return false;
        }

        entityManager.remove(entity);
        entityManager.getTransaction().commit();
        entityManager.close();

        return true;
    }
}
