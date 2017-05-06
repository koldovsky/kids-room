package ua.softserveinc.tc.dao;

import ua.softserveinc.tc.entity.pagination.SortingPagination;

import javax.persistence.criteria.*;
import java.util.List;

public interface BaseDao<T> {
    T findById(Object id);

    List<T> findAll();

    List<T> findAll(SortingPagination pagination);

    void create(T entity);

    void delete(T entity);

    void deleteAll();

    T update(T entity);

    List<Order> getListForOrdering(List<SortingPagination.Sorting> sortingList, CriteriaBuilder builder,
                                   Root<T> root, CriteriaQuery<T> query);

    List<Predicate> getListForSearching(List<SortingPagination.Search> searches, CriteriaBuilder builder,
                                        Root<T> root);

    long getRowsCount();
}
