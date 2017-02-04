package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.constants.DayOffConstants;
import ua.softserveinc.tc.dao.DayOffDao;
import ua.softserveinc.tc.entity.DayOff;
import ua.softserveinc.tc.entity.Event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

import static ua.softserveinc.tc.util.DateUtil.toDate;

@Repository
public class DayOffDaoImpl extends BaseDaoImpl<DayOff> implements DayOffDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DayOff> findByNameOrStartDate(String name, LocalDate startDate) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DayOff> query = builder.createQuery(DayOff.class);
        Root<DayOff> root = query.from(DayOff.class);

        query.select(root).where(
          builder.or(
                  builder.equal(root.get(DayOffConstants.Entity.NAME), name),
                  builder.equal(root.get(DayOffConstants.Entity.START_DATE), toDate(startDate))
          )
        );

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public boolean isBetweenStartDateAndEndDate(LocalDate startDate, LocalDate endDate) {
        return findIfBetweenStartDateAndEndDate(startDate, endDate).isEmpty();
    }

    @Override
    @Transactional
    public void delete(long id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Event> delete = builder.createCriteriaDelete(Event.class);
        Root r = delete.from(Event.class);

        delete.where(builder.equal(r.get("id"), id));
        entityManager.createQuery(delete).executeUpdate();
    }

    private List<DayOff> findIfBetweenStartDateAndEndDate(LocalDate startDate, LocalDate endDate) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DayOff> query = builder.createQuery(DayOff.class);
        Root<DayOff> root = query.from(DayOff.class);

        query.select(root).where(
                builder.and(
                        builder.greaterThanOrEqualTo(root.get(DayOffConstants.Entity.START_DATE), toDate(startDate)),
                        builder.lessThanOrEqualTo(root.get(DayOffConstants.Entity.END_DATE), toDate(endDate))
                )
        );

        return entityManager.createQuery(query).getResultList();
    }
}
