package ua.softserveinc.tc.dto;

import java.util.List;

/**
 * Created by IhorTovpinets on 29.12.2016.
 */
public class EventsCreatingResultsDto {

    private List<EventDto> eventsCreated;

    private List<String> datesWhenNotCreated;

    public EventsCreatingResultsDto(List<EventDto> eventsCreated, List<String> datesWhenNotCreated) {
        this.eventsCreated = eventsCreated;
        this.datesWhenNotCreated = datesWhenNotCreated;
    }

    public List<EventDto> getEventsCreated() {
        return eventsCreated;
    }

    public List<String> getDatesWhenNotCreated() {
        return datesWhenNotCreated;
    }
}
