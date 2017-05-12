package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.constants.AbonnementConstants;
import ua.softserveinc.tc.constants.UserConstants;
import ua.softserveinc.tc.dao.SubscriptionAssignmentDao;
import ua.softserveinc.tc.dto.SubscriptionsUsedHoursDto;
import ua.softserveinc.tc.dto.UserAssigmentDto;
import ua.softserveinc.tc.entity.Abonnement;
import ua.softserveinc.tc.entity.AbonnementUsage;
import ua.softserveinc.tc.entity.SubscriptionAssignment;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.entity.pagination.SortingPagination;
import ua.softserveinc.tc.util.PaginationCharacteristics;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Repository
public class SubscriptionAssignmentDaoImpl extends BaseDaoImpl<SubscriptionAssignment>
        implements SubscriptionAssignmentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SubscriptionsUsedHoursDto> getAssignmentByUserId(long userId) {
        CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
        CriteriaQuery<SubscriptionsUsedHoursDto> query = criteria.createQuery(SubscriptionsUsedHoursDto.class);
        Root<SubscriptionAssignment> root = query.from(SubscriptionAssignment.class);
        Join<SubscriptionAssignment, User> userJoin = root.join(AbonnementConstants.Entity.USER);
        Join<SubscriptionAssignment, AbonnementUsage> usageJoin = root.join(
                AbonnementConstants.Alias.ABONNEMENT_USAGES, JoinType.LEFT);
        Expression<Integer> usedMinutes = usageJoin.get(AbonnementConstants.Alias.USED_MINUTES).as(Integer.class);

        query.multiselect(root, criteria.<Long>selectCase().when(criteria.sumAsLong(usedMinutes).isNull(), 0L)
                .otherwise(usedMinutes.as(Long.class)))
                .where(criteria.equal(userJoin.get(AbonnementConstants.Hibernate.ABONNEMENT_ID), userId),
                        criteria.equal(root.get(AbonnementConstants.Entity.VALID), true));
        query.groupBy(root);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<SubscriptionAssignment> findAll(SortingPagination sortPaginate) {
        SortingPagination.Pagination pagination = sortPaginate.getPagination();
        List<SortingPagination.Sorting> sortingList = sortPaginate.getSortings();
        List<SortingPagination.Search> searchList = sortPaginate.getSearches();
        PaginationCharacteristics.searchCount = 0;

        CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
        CriteriaQuery<SubscriptionAssignment> query = criteria.createQuery(SubscriptionAssignment.class);
        Root<SubscriptionAssignment> root = query.from(SubscriptionAssignment.class);
        Join<SubscriptionAssignment, AbonnementUsage> usageJoin = root.join(
                AbonnementConstants.Alias.ABONNEMENT_USAGES, JoinType.LEFT);
        Join<SubscriptionAssignment, User> userJoin = root.join(AbonnementConstants.Entity.USER);
        Join<SubscriptionAssignment, Abonnement> abonnementJoin = root
                .join(AbonnementConstants.Entity.ABONNEMENT);

        Expression<String> userName = criteria.concat(criteria.concat(
                userJoin.get(AbonnementConstants.Alias.FIRST_NAME).as(String.class), " "),
                userJoin.get(AbonnementConstants.Alias.LAST_NAME).as(String.class));
        Expression<String> email = userJoin.get(UserConstants.Entity.EMAIL);
        Expression<String> abonnement = abonnementJoin.get(AbonnementConstants.Hibernate.ABONNEMENT_NAME);
        Expression<Long> hoursUsed = criteria
                .sumAsLong(usageJoin.get(AbonnementConstants.Alias.USED_MINUTES)).as(Long.class);
        Expression<Long> minutesLeft = criteria.diff(
                criteria.prod(60, abonnementJoin.get(AbonnementConstants.Hibernate.ABONNEMENT_HOUR)),
                criteria.<Long>selectCase()
                        .when(hoursUsed.isNull(), criteria.literal(0L))
                        .otherwise(hoursUsed)).as(Long.class);
        userName.alias(AbonnementConstants.Alias.USER_SORT_COLUMN);
        email.alias(AbonnementConstants.Alias.EMAIL_SORT_COLUMN);
        abonnement.alias(AbonnementConstants.Entity.ABONNEMENT);
        minutesLeft.alias(AbonnementConstants.Alias.USED_MINUTES);
        root.get(AbonnementConstants.Entity.VALID).alias(AbonnementConstants.Entity.VALID);

        List<Expression<?>> expressions = Arrays.asList(userName, email, abonnement, minutesLeft,
                root.get(AbonnementConstants.Entity.VALID));
        query.select(root).groupBy(root);
        if (!searchList.isEmpty()) {
            List<Predicate> restrictions = getSearchList(searchList, criteria, expressions);
            PaginationCharacteristics.searchCount = getSearchedItemsCount(criteria, query, root, restrictions);
        }
        query.orderBy(getOrderList(sortingList, criteria, expressions));

        return entityManager.createQuery(query)
                .setFirstResult(pagination.getStart())
                .setMaxResults(pagination.getItemsPerPage())
                .getResultList();
    }

    private List<Predicate> getSearchList(List<SortingPagination.Search> searches,
                                          CriteriaBuilder builder, List<Expression<?>> expressions) {
        List<Predicate> restrictions = new ArrayList<>();

        for (SortingPagination.Search search : searches) {
            restrictions.addAll(expressions.stream()
                    .filter(expression -> expression.getAlias().contains(search.getColumn()))
                    .map(expression -> {
                        try {
                            return builder.greaterThan(expression.as(Long.class),
                                    Long.parseLong(search.getValue()));
                        } catch (NumberFormatException ne) {
                            return builder.like(expression.as(String.class), "%" + search.getValue() + "%");
                        }
                    })
                    .limit(1)
                    .collect(Collectors.toList()));
        }

        return restrictions;
    }

    private List<Order> getOrderList(List<SortingPagination.Sorting> sortingList,
                                     CriteriaBuilder criteria, List<Expression<?>> expressions) {

        List<Order> orders = new ArrayList<>();
        for (SortingPagination.Sorting sorting : sortingList) {
            orders.addAll(expressions.stream()
                    .filter(expression -> expression.getAlias().contains(sorting.getColumn()))
                    .map(expression -> sorting.getDirection() == 1 ? criteria.asc(expression)
                            : criteria.desc(expression)).collect(Collectors.toList()));
        }

        return orders;
    }

    private long getSearchedItemsCount(CriteriaBuilder builder, CriteriaQuery<SubscriptionAssignment> criteria,
                                       Root<SubscriptionAssignment> root, List<Predicate> restrictions) {
        criteria.select(root).where(builder.and(restrictions.toArray(new Predicate[restrictions.size()])));
        return entityManager.createQuery(criteria).getResultList().size();
    }
}
