package ua.softserveinc.tc.service;

import ua.softserveinc.tc.dto.EventDto;
import ua.softserveinc.tc.entity.Event;

import java.util.List;

public interface EventService extends BaseService<Event> {

    List<EventDto> getListOfEventDto(List<Event> listOfEvents);

    void deleteRecurrentEvent(Long idRecurrent);

    List<Event> findByName(String name);

    List<String> getEmailForNotifyChangeEvent(Long event_id);
}
