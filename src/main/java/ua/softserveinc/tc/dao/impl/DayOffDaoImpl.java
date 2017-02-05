package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.constants.DayOffConstants;
import ua.softserveinc.tc.dao.DayOffDao;
import ua.softserveinc.tc.entity.DayOff;

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
    @Transactional
    public void delete(long id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaDelete<DayOff> delete = builder.createCriteriaDelete(DayOff.class);
        Root r = delete.from(DayOff.class);

        delete.where(builder.equal(r.get("id"), id));
        entityManager.createQuery(delete).executeUpdate();
    }
}
