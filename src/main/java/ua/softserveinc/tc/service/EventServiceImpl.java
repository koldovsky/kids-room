package ua.softserveinc.tc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.dao.EventDao;
import ua.softserveinc.tc.entity.Event;

import java.util.Date;
import java.util.List;

/**
 * Created by Nestor on 01.05.2016.
 */

@Transactional
@Service
public class EventServiceImpl extends BaseServiceImpl<Event> implements EventService{
    @Autowired
    private EventDao eventDao;

    public List<Event> getAllEventsByDay(Date searchDate){
        return eventDao.getAllEventsByDay(searchDate);
    }

    public void deleteOutdated(){
        eventDao.deleteOutdated();
    }
}
