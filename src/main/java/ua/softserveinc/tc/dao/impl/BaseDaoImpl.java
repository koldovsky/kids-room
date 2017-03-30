package ua.softserveinc.tc.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.GenericTypeResolver;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.constants.GenericConstants;
import ua.softserveinc.tc.dao.BaseDao;
import ua.softserveinc.tc.entity.pagination.*;
import ua.softserveinc.tc.entity.pagination.SortingPagination.*;
import ua.softserveinc.tc.util.PaginationCharacteristics;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseDaoImpl<T> implements BaseDao<T> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<?> entityClass;

    public BaseDaoImpl() {
        entityClass = getEntityClass();
    }

    public List<T> findAll() {
        return entityManager
                .createQuery("FROM " + entityClass.getSimpleName()
                        + " ORDER BY id", getEntityClass())
                .getResultList();
    }

    @Transactional
    public void create(T entity) {
        entityManager.persist(entity);
    }

    public T findById(Object id) {
        return entityManager.find(getEntityClass(), id);
    }

    @Transactional
    public void delete(T entity) {
        entityManager.remove(entityManager.merge(entity));
    }

    @Transactional
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM "
                + entityClass.getSimpleName()).executeUpdate();
    }

    @Transactional
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @SuppressWarnings("unchecked")
    protected final Class<T> getEntityClass() {
        Class<?> entityClass = GenericTypeResolver
                .resolveTypeArgument(getClass(), BaseDaoImpl.class);

        if (entityClass != null) {
            return (Class<T>) entityClass;
        }
        throw new IllegalArgumentException("Could not resolve entity class");
    }

    @Override
    public List<T> findAll(SortingPagination sortPaginate) {
        List<T> resultList;
        Pagination pagination = sortPaginate.getPagination();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(getEntityClass());
        Root<T> root = criteria.from(getEntityClass());

        List<Predicate> restrictions = new ArrayList<>();
        List<Order> restrictionsOrder = new ArrayList<>();

        List<Search> searches = sortPaginate.getSearches();
        if (PaginationCharacteristics.isSearched(searches)) {
            searches.forEach(item -> restrictions.add(builder.like(
                    root.get(item.getColumn()), "%" + item.getValue() + "%")));
        } else {
            restrictions.addAll(Arrays.asList(
                    builder.greaterThan(root.get(GenericConstants.GENERIC_ID),
                            pagination.getStart()),
                    builder.lessThanOrEqualTo(root.get(GenericConstants.GENERIC_ID),
                            pagination.getStart() + pagination.getItemsPerPage())
            ));
        }

        criteria.select(root).where(builder.and(
                restrictions.toArray(new Predicate[restrictions.size()])));

        List<Sorting> sorting = sortPaginate.getSortings();
        sorting.forEach(item -> {
            if (item.getDirection() == 1) {
                restrictionsOrder.add(builder.asc(root.get(item.getColumn())));
            } else {
                restrictionsOrder.add(builder.desc(root.get(item.getColumn())));
            }
        });

        if (!restrictionsOrder.isEmpty()) {
            criteria.orderBy(restrictionsOrder);
        }

        resultList = entityManager.createQuery(criteria).getResultList();
        return resultList;
    }



    @Override
    public long getRowsCount() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(getEntityClass())));
        return entityManager.createQuery(query).getSingleResult();
    }
}
