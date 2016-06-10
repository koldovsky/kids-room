package ua.softserveinc.tc.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.constants.column.EventConst;
import ua.softserveinc.tc.dao.EventDao;
import ua.softserveinc.tc.entity.Event;

/**
 * Created by Nestor on 30.04.2016.
 */

@Repository(EventConst.REPOSITORY)
public class EventDaoImpl extends BaseDaoImpl<Event> implements EventDao {
}