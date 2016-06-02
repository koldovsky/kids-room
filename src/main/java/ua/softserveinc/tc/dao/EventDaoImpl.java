package ua.softserveinc.tc.dao;

import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.constants.ColumnConstants.EventConst;
import ua.softserveinc.tc.entity.Event;

/**
 * Created by Nestor on 30.04.2016.
 */

@Repository(EventConst.REPOSITORY)
public class EventDaoImpl extends BaseDaoImpl<Event> implements EventDao{
}