package ua.softserveinc.tc.dao;

import ua.softserveinc.tc.entity.Event;

public interface EventDao extends BaseDao<Event>{
    Long getMaxRecurrentId();
}
