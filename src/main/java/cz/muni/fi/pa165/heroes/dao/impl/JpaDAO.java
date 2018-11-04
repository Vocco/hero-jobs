package cz.muni.fi.pa165.heroes.dao.impl;

import cz.muni.fi.pa165.heroes.dao.DAO;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Base JPA {@link DAO} implementation.
 *
 * @author Vojtech Krajnansky (423126)
 *
 * @param <T> A {@link Serializable} entity.
 */
@Repository
public abstract class JpaDAO<T extends Serializable> implements DAO<T> {


    // CLASS ATTRIBUTES

    protected Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    // FIXME: This is a workaround.
    @Autowired
    private EntityManagerFactory emf;


    // CONSTRUCTORS

    public JpaDAO() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }


    // DAO INTERFACE IMPLEMENTATION

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<T> findAll() {
        return entityManager
            .createQuery("SELECT t FROM " + entityClass.getSimpleName() + " t")
            .getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public T findById(Long id) {
        if (id == null) throw new IllegalArgumentException("Cannot find by null id.");
        return entityManager.find(entityClass, id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public T update(T entity) {
        if (entity == null) throw new IllegalArgumentException("Cannot update to null entity.");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        T updated = em.merge(entity);

        tx.commit();
        em.close();

        return updated;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean save(T entity) {
        if (entity == null) throw new IllegalArgumentException("Cannot save a null entity.");
        boolean wasSuccess = true;

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            em.persist(entity);
            tx.commit();
        } catch (EntityExistsException ex) {
            wasSuccess = false;
        } finally {
          em.close();
        }

        return wasSuccess;
    }

    @Override
    public boolean delete(T entity) {
        if (entity == null) throw new IllegalArgumentException("Cannot delete a null entity.");
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
        if (id == null) throw new IllegalArgumentException("Cannot delete by a null id.");
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
