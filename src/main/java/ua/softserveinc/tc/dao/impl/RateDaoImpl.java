package ua.softserveinc.tc.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.constants.BookingConstants;
import ua.softserveinc.tc.constants.RateConstants;
import ua.softserveinc.tc.constants.RoomConstants;
import ua.softserveinc.tc.dao.RateDao;
import ua.softserveinc.tc.entity.Rate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class RateDaoImpl extends BaseDaoImpl<Rate> implements RateDao {

    @PersistenceContext
    EntityManager entityManager;

    public List<Rate> getRatesByRoomId(Long roomId){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Rate> criteria = builder.createQuery(Rate.class);
        Root<Rate> from = criteria.from(Rate.class);
        criteria.select(from).where(builder.equal(from.get(RoomConstants.ID_ROOM),roomId));
        return entityManager.createQuery(criteria).getResultList();
    }
}
