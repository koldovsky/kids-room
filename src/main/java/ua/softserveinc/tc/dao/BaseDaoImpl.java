package ua.softserveinc.tc.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class BaseDaoImpl<T> implements BaseDao<T> {

    @PersistenceContext
    private EntityManager entityManager;
    private Class<?> entityClass;

    public BaseDaoImpl() {
        entityClass = getEntityClass();
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return entityManager.createQuery("From " + entityClass.getSimpleName() + " order by id").getResultList();
    }

    public void create(T entity) {
        entityManager.persist(entity);
    }

    public T findById(Object id) {
        return entityManager.find(getEntityClass(), id);
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }

    public void deleteAll() {
        entityManager.createQuery("DELETE FROM " + entityClass.getSimpleName()).executeUpdate();
    }

    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @SuppressWarnings("unchecked")
    protected final Class<T> getEntityClass() {
        final Type type = getClass().getGenericSuperclass() instanceof ParameterizedType
                ? getClass().getGenericSuperclass() : getClass().getSuperclass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            final ParameterizedType paramType = (ParameterizedType) type;
            return (Class<T>) paramType.getActualTypeArguments()[0];
        } else
            throw new IllegalArgumentException("Could not guess entity class by reflection");
    }

}
