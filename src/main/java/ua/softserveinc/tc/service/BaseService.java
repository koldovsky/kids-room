package ua.softserveinc.tc.service;

import java.util.List;

public interface BaseService<T> {

    void create(T entity);

    T update(T entity);

    T findByIdTransactional(Object id);

    T findEntityById(Object id);

    void delete(T entity);

    List<T> findAll();

    void deleteAll();
}
