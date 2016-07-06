package ua.softserveinc.tc.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.service.EventService;

/**
 * Created by Nestor on 01.05.2016.
 */

@Transactional
@Service
public class EventServiceImpl extends BaseServiceImpl<Event> implements EventService {

}
