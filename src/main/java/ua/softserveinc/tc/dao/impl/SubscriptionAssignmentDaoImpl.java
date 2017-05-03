package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.dao.SubscriptionAssignmentDao;
import ua.softserveinc.tc.dto.SubscriptionsUsedHoursDto;
import ua.softserveinc.tc.dto.UserAssigmentDto;
import ua.softserveinc.tc.entity.Abonnement;
import ua.softserveinc.tc.entity.AbonnementUsage;
import ua.softserveinc.tc.entity.SubscriptionAssignment;
import ua.softserveinc.tc.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
    public List<UserAssigmentDto> getDtos() {
        CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserAssigmentDto> query = criteria.createQuery(UserAssigmentDto.class);
        Root<SubscriptionAssignment> root = query.from(SubscriptionAssignment.class);

        Join<SubscriptionAssignment, AbonnementUsage> usageJoin = root.join("abonnementUsages", JoinType.LEFT);
        Join<SubscriptionAssignment, User> userJoin = root.join("user");
        Join<SubscriptionAssignment, Abonnement> abonnementJoin = root.join("abonnement");

        query.multiselect(userJoin.get("firstName"), userJoin.get("lastName"), abonnementJoin.get("name"),
                abonnementJoin.get("hour"), criteria.sumAsLong(usageJoin.get("usedMinutes")));
        query.groupBy(root);

        List<UserAssigmentDto> result = entityManager.createQuery(query).getResultList();
        result.forEach(System.out::println);

        return result;
    }
}
