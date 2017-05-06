package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;
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
        Join<SubscriptionAssignment, User> userJoin = root.join("user");
        Join<SubscriptionAssignment, AbonnementUsage> usageJoin = root.join("abonnementUsages", JoinType.LEFT);

        query.multiselect(root, criteria.sumAsLong(usageJoin.get("usedMinutes")))
                .where(criteria.equal(userJoin.get("id"), userId), criteria.equal(root.get("valid"), true));
        query.groupBy(root);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Map<SubscriptionAssignment, Long> getAssignmentWithUsedHoursByUserId(long userId) {
        Query query = entityManager.createQuery("select sa, sum(au.usedMinutes) from " +
                " AbonnementUsage au inner join au.assignment sa inner join sa.user user " +
                " where user.id = :id_user");
        query.setParameter("id_user", userId);

        List<Object[]> list = query.getResultList();

        return list.stream().collect(Collectors.toMap(o -> (SubscriptionAssignment) o[0],
                        o -> (Long)o[1]));
    }

    @Override
    public List<UserAssigmentDto> getDtos(SortingPagination sortPaginate) {
        findAll(sortPaginate);

        SortingPagination.Pagination pagination = sortPaginate.getPagination();
        List<SortingPagination.Sorting> sortingList = sortPaginate.getSortings();
        List<SortingPagination.Search> searchList = sortPaginate.getSearches();
        PaginationCharacteristics.searchCount = 0;

        CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserAssigmentDto> query = criteria.createQuery(UserAssigmentDto.class);
        Root<SubscriptionAssignment> root = query.from(SubscriptionAssignment.class);
        Join<SubscriptionAssignment, AbonnementUsage> usageJoin = root.join("abonnementUsages", JoinType.LEFT);
        Join<SubscriptionAssignment, User> userJoin = root.join("user");
        Join<SubscriptionAssignment, Abonnement> abonnementJoin = root.join("abonnement");

        Expression<String> userName = criteria
                .concat(criteria.concat(userJoin.get("firstName").as(String.class), " "),
                userJoin.get("lastName").as(String.class));
        Expression<String> email = userJoin.get("email");
        Expression<String> abonnement = abonnementJoin.get("name");
        Expression<Integer> hours = abonnementJoin.get("hour").as(Integer.class);
        Expression<Long> hoursUsed = criteria.sumAsLong(usageJoin.get("usedMinutes")).as(Long.class);
        Expression<Long> minutesLeft = criteria.diff(criteria.prod(60, hours).as(Long.class),
                criteria.<Long>selectCase().when(hoursUsed.isNull(), criteria.literal(0L))
                        .otherwise(hoursUsed)).as(Long.class);
        userName.alias("user1");
        email.alias("user2");
        abonnement.alias("abonnement");
        minutesLeft.alias("usedMinutes");
        List<Expression<?>> expressions = Stream.of(userName, email, abonnement, minutesLeft)
                .collect(Collectors.toList());
        List<Predicate> restrictions = new ArrayList<>();
        if (!searchList.isEmpty()) {
            addSearchToRestrictions(searchList, criteria, expressions, restrictions);
        }

        query.multiselect(userName, email, abonnement, hours, minutesLeft)
                .where(criteria.and(restrictions.toArray(new Predicate[restrictions.size()])));
        query.groupBy(root);

        List<Order> orders = new ArrayList<>();
        addOrders(sortingList, criteria, expressions, orders);
        query.orderBy(orders);

        List<UserAssigmentDto> result = entityManager.createQuery(query)
                .setFirstResult(pagination.getStart())
                .setMaxResults(pagination.getItemsPerPage())
                .getResultList();
        PaginationCharacteristics.searchCount = result.size();
        result.forEach(System.out::println);

        return result;
    }

    private void addSearchToRestrictions(List<SortingPagination.Search> searches, CriteriaBuilder builder,
                                         List<Expression<?>> expressions, List<Predicate> restrictions) {

        for (SortingPagination.Search search : searches) {
            restrictions.addAll(expressions.stream()
                    .filter(expression -> expression.getAlias().contains(search.getColumn()))
                    .map(expression -> builder.like(expression.as(String.class), "%" + search.getValue() + "%"))
                    .limit(1)
                    .collect(Collectors.toList()));
        }
    }

    private void addOrders(List<SortingPagination.Sorting> sortingList, CriteriaBuilder criteria,
                                         List<Expression<?>> expressions, List<Order> orders) {

        for (SortingPagination.Sorting sorting : sortingList) {
            orders.addAll(expressions.stream()
                    .filter(expression -> expression.getAlias().contains(sorting.getColumn()))
                    .map(expression -> sorting.getDirection() == 1 ? criteria.asc(expression)
                            : criteria.desc(expression)).collect(Collectors.toList()));
        }
    }

    @Override
    public List<Order> getListForOrdering(List<SortingPagination.Sorting> sortingList, CriteriaBuilder criteria,
                                          Root<SubscriptionAssignment> root,
                                          CriteriaQuery<SubscriptionAssignment> query) {
        List<Order> orders = new ArrayList<>();
        Join<SubscriptionAssignment, AbonnementUsage> usageJoin = root.join("abonnementUsages", JoinType.LEFT);
        Join<SubscriptionAssignment, User> userJoin = root.join("user");
        Join<SubscriptionAssignment, Abonnement> abonnementJoin = root.join("abonnement");

        Expression<String> userName = criteria
                .concat(criteria.concat(userJoin.get("firstName").as(String.class), " "),
                        userJoin.get("lastName").as(String.class));
        Expression<String> email = userJoin.get("email");
        Expression<String> abonnement = abonnementJoin.get("name");
        Expression<Integer> hours = abonnementJoin.get("hour").as(Integer.class);
        Expression<Long> hoursUsed = criteria.sumAsLong(usageJoin.get("usedMinutes")).as(Long.class);
        Expression<Long> minutesLeft = criteria.diff(criteria.prod(60, hours).as(Long.class),
                criteria.<Long>selectCase().when(hoursUsed.isNull(), criteria.literal(0L))
                        .otherwise(hoursUsed)).as(Long.class);
        userName.alias("user1");
        email.alias("user2");
        abonnement.alias("abonnement");
        minutesLeft.alias("usedMinutes");

        List<Expression<?>> expressions = Stream.of(userName, email, abonnement, minutesLeft)
                .collect(Collectors.toList());
        for (SortingPagination.Sorting sorting : sortingList) {
            orders.addAll(expressions.stream()
                    .filter(expression -> expression.getAlias().contains(sorting.getColumn()))
                    .map(expression -> sorting.getDirection() == 1 ? criteria.asc(expression)
                            : criteria.desc(expression)).collect(Collectors.toList()));
        }
        query.groupBy(root);

        return orders;
    }

    @Override
    public List<Predicate> getListForSearching(List<SortingPagination.Search> searches, CriteriaBuilder criteria,
                                               Root<SubscriptionAssignment> root) {
        List<Predicate> restrictions = new ArrayList<>();
        Join<SubscriptionAssignment, User> userJoin = root.join("user");
        Join<SubscriptionAssignment, Abonnement> abonnementJoin = root.join("abonnement");

        Expression<String> userName = criteria
                .concat(criteria.concat(userJoin.get("firstName").as(String.class), " "),
                        userJoin.get("lastName").as(String.class));
        Expression<String> abonnement = abonnementJoin.get("name");
        userName.alias("user");
        abonnement.alias("abonnement");
        List<Expression<?>> expressions = Stream.of(userName, abonnement).collect(Collectors.toList());

        for (SortingPagination.Search search : searches) {
            restrictions.addAll(expressions.stream()
                    .filter(expression -> expression.getAlias().equals(search.getColumn()))
                    .map(expression -> criteria.like(expression.as(String.class), "%" + search.getValue() + "%"))
                    .collect(Collectors.toList()));
        }

        return restrictions;
    }
}
