package ua.softserveinc.tc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.dao.BaseDao;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.service.BaseService;

import java.util.List;

@Transactional
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    private BaseDao<T> baseDao;

    @Override
    public List<T> findAll() {
        return baseDao.findAll();
    }

    @Override
    public void create(T entity) {
        baseDao.create(entity);
    }

    @Override
    public T findById(Object id) {
        T obj  = baseDao.findById(id);
        if(obj == null) {
            throw new ResourceNotFoundException();
        }
        return obj;
    }

    @Override
    public void delete(T entity) {
        baseDao.delete(entity);
    }

    @Override
    public void deleteAll() {
        baseDao.deleteAll();
    }

    @Override
    public T update(T entity) {
        return baseDao.update(entity);
    }

}
