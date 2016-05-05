package ua.softserveinc.tc.dao;

import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.entity.ColumnConstants.EventConst;
import ua.softserveinc.tc.entity.Event;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Nestor on 30.04.2016.
 */

@Repository(EventConst.REPOSITORY)
public class EventDaoImpl extends BaseDaoImpl<Event> implements EventDao{
}