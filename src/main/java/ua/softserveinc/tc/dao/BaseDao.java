package ua.softserveinc.tc.dao;

import java.util.List;

public interface BaseDao<T> {
    T findById(Object id);

    List<T> findAll();

    void create(T entity);

    void delete(T entity);

    void deleteAll();

    T update(T entity);
}
