package ua.softserveinc.tc.service;

import ua.softserveinc.tc.entity.Event;

import java.util.Date;
import java.util.List;

/**
 * Created by Nestor on 01.05.2016.
 */
public interface EventService extends BaseService<Event> {
    List<Event> getAllEventsByDay(Date searchDate);
}
