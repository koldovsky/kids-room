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
        List<Sorting> sortingList = sortPaginate.getSortings();
        List<Search> searchList = sortPaginate.getSearches();
        PaginationCharacteristics.searchCount = 0;

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(getEntityClass());
        Root<T> root = criteria.from(getEntityClass());

        List<Predicate> restrictions = new ArrayList<>();
        List<Order> restrictionsOrder = new ArrayList<>();

        if (!searchList.isEmpty()) {
            addSearchToRestrictions(searchList, builder, root, restrictions);
            PaginationCharacteristics.searchCount = getSearchedItemsCount(builder, criteria, root, restrictions);
        }
        addSortingsToOrderRestrictions(sortingList, builder, root, restrictionsOrder);
        if (!restrictionsOrder.isEmpty()) {
            criteria.orderBy(restrictionsOrder);
        }
        addPaginationToRestrictions(pagination, builder, root, restrictions);

        criteria.select(root).where(builder.and(restrictions.toArray(new Predicate[restrictions.size()])));
        resultList = entityManager.createQuery(criteria).getResultList();
        return resultList;
    }

    private long getSearchedItemsCount(CriteriaBuilder builder, CriteriaQuery<T> criteria,
                                       Root<T> root, List<Predicate> restrictions) {
        criteria.select(root).where(builder.and(restrictions.toArray(new Predicate[restrictions.size()])));
        List<T> searchResultList = entityManager.createQuery(criteria).getResultList();
        return searchResultList.size();
    }

    private void addSortingsToOrderRestrictions(List<Sorting> sortingsList, CriteriaBuilder builder,
                                                Root<T> root, List<Order> restrictionsOrder) {
        sortingsList.forEach(sorting -> {
            if (sorting.getDirection() == 1) {
                restrictionsOrder.add(builder.asc(root.get(sorting.getColumn())));
            } else {
                restrictionsOrder.add(builder.desc(root.get(sorting.getColumn())));
            }
        });
    }

    private void addPaginationToRestrictions(Pagination pagination, CriteriaBuilder builder,
                                             Root<T> root, List<Predicate> restrictions) {
        restrictions.addAll(Arrays.asList(
                builder.greaterThan(root.get(GenericConstants.GENERIC_ID),
                        pagination.getStart()),
                builder.lessThanOrEqualTo(root.get(GenericConstants.GENERIC_ID),
                        pagination.getStart() + pagination.getItemsPerPage())
        ));
    }

    private void addSearchToRestrictions(List<Search> searches, CriteriaBuilder builder,
                                         Root<T> root, List<Predicate> restrictions) {
        searches.forEach(item -> restrictions.add(builder.like(
                root.get(item.getColumn()), "%" + item.getValue() + "%")
        ));
    }

    @Override
    public long getRowsCount() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(getEntityClass())));
        return entityManager.createQuery(query).getSingleResult();
    }
}
