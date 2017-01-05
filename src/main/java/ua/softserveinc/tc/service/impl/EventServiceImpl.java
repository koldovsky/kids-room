package ua.softserveinc.tc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dto.EventDto;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.mapper.EventMapper;
import ua.softserveinc.tc.mapper.GenericMapper;
import ua.softserveinc.tc.service.EventService;

import java.util.LinkedList;
import java.util.List;

@Service
public class EventServiceImpl extends BaseServiceImpl<Event>
        implements EventService {


    @Autowired
    private GenericMapper<Event, EventDto> genericMapper;

    @Override
    public List<EventDto> getListOfEventDto(List<Event> listOfEvents) {
        List<EventDto> listOfDto = new LinkedList<>();
        for (Event event:listOfEvents) {
            listOfDto.add(genericMapper.toDto(event));
        }
        return listOfDto;
    }
}
