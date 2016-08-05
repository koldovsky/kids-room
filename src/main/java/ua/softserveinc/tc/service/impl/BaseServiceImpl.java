package ua.softserveinc.tc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.dao.BaseDao;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.service.BaseService;

import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    private BaseDao<T> baseDao;

    @Override
    @Transactional(readOnly = true)
    public T findById(Object id) {
        T obj  = baseDao.findById(id);
        if(obj == null) {
            throw new ResourceNotFoundException();
        }
        return obj;
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return baseDao.findAll();
    }

    @Override
    @Transactional
    public void create(T entity) {
        baseDao.create(entity);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        baseDao.delete(entity);
    }

    @Override
    @Transactional
    public void deleteAll() {
        baseDao.deleteAll();
    }

    @Override
    @Transactional
    public T update(T entity) {
        return baseDao.update(entity);
    }
}
