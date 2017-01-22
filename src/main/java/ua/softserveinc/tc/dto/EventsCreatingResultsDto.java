package ua.softserveinc.tc.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class EventsCreatingResultsDto {

    private final List<EventDto> eventsCreated = new ArrayList<>();

    private final List<String> datesWhenNotCreated = new ArrayList<>();

    public EventsCreatingResultsDto(List<EventDto> eventsCreated, List<String> datesWhenNotCreated) {
        this.eventsCreated.addAll(eventsCreated);
        this.datesWhenNotCreated.addAll(datesWhenNotCreated);
    }

    public List<EventDto> getEventsCreated() {
        return Collections.unmodifiableList(eventsCreated);
    }

    public List<String> getDatesWhenNotCreated() {
        return Collections.unmodifiableList(datesWhenNotCreated);
    }
}
