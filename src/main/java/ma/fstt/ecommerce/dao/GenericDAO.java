package ma.fstt.ecommerce.dao;

import ma.fstt.ecommerce.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

public abstract class GenericDAO<T> {

    private final Class<T> type;

    protected GenericDAO(Class<T> type) { this.type = type; }

    public T save(T entity) {                       // persist = ajouter
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public T update(T entity) {                     // merge = modifier
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            T merged = em.merge(entity);
            em.getTransaction().commit();
            return merged;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {                   // remove = supprimer
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            T entity = em.find(type, id);
            if (entity != null) em.remove(entity);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public T findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(type, id);
        } finally {
            em.close();
        }
    }

    public List<T> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT e FROM " + type.getSimpleName() + " e", type)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}