package ua.softserveinc.tc.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.GenericTypeResolver;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.constants.AbonnementConstants;
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
import java.util.stream.Collectors;

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
    @Transactional(readOnly = true)
    public List<T> findAll(SortingPagination sortPaginate) {
        List<T> resultList;
        Pagination pagination = sortPaginate.getPagination();
        List<Sorting> sortingList = sortPaginate.getSortings();
        List<Search> searchList = sortPaginate.getSearches();
        PaginationCharacteristics.searchCount = 0;

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(getEntityClass());
        Root<T> root = criteria.from(getEntityClass());

        if (!searchList.isEmpty()) {
            criteria.where(getListForSearching(searchList, builder, root).toArray(new Predicate[]{}));
        }

        List<Order> orders = getListForOrdering(sortingList, builder, root, criteria);

        criteria.select(root).orderBy(orders);

        resultList = entityManager.createQuery(criteria)
                .setFirstResult(pagination.getStart())
                .setMaxResults(pagination.getItemsPerPage())
                .getResultList();
        PaginationCharacteristics.searchCount = resultList.size();
        return resultList;
    }

    @Override
    public List<Order> getListForOrdering(List<SortingPagination.Sorting> sortingList, CriteriaBuilder builder,
                                          Root<T> root, CriteriaQuery<T> query) {

        return sortingList.stream().map(item -> item.getDirection() == 1 ?
                builder.asc(root.get(item.getColumn()))
                : builder.desc(root.get(item.getColumn()))).collect(Collectors.toList());
    }

    @Override
    public List<Predicate> getListForSearching(List<SortingPagination.Search> searches, CriteriaBuilder builder,
                                               Root<T> root) {
        List<Predicate> restrictions = new ArrayList<>();

        searches.forEach(item -> {
            try {
                int value = Integer.parseInt(item.getValue());
                restrictions.add(builder.equal(root.get(item.getColumn()), value));
            } catch (NumberFormatException ne) {
                restrictions.add(builder.like(
                        root.get(item.getColumn()), "%" + item.getValue() + "%")
                );
            }
        });

        return restrictions;
    }

    @Override
    public long getRowsCount() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(getEntityClass())));
        return entityManager.createQuery(query).getSingleResult();
    }
}
