package ua.softserveinc.tc.dao;

import ua.softserveinc.tc.entity.Event;

import java.util.List;

public interface EventDao extends BaseDao<Event>{
    Long getMaxRecurrentId();
    List<Event> getRecurrentEventByRecurrentId(Long recurrentId);
}
