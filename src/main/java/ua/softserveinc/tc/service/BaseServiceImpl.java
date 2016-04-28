package ua.softserveinc.tc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ua.softserveinc.tc.dao.BaseDao;

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
        return baseDao.findById(id);
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
