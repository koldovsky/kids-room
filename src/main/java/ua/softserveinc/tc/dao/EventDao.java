package ua.softserveinc.tc.dao;

import ua.softserveinc.tc.entity.Event;

import java.util.List;

/**
 * Created by Nestor on 30.04.2016.
 */

public interface EventDao extends BaseDao<Event>{
    Long getMaxRecurrentId();
}
