package ua.softserveinc.tc.dao;

import java.util.List;

public interface BaseDao<T> {
    List<T> findAll();

    void create(T entity);

    T findById(Object id);

    void delete(T entity);

    void deleteAll();

    T update(T entity);
}
