package ua.softserveinc.tc.util;

import ua.softserveinc.tc.constants.EventConstants;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.entity.Room;

import java.util.Date;

public class EventBuilder {

    private Event event;

    public static class Builder {

        private Event event = new Event();

        public Builder setId(Long id) {
            event.setId(id);
            return this;
        }

        public Builder setName(String name) {
            event.setName(name);
            return this;
        }

        public Builder setStartTime(Date startTime) {
            event.setStartTime(startTime);
            return this;
        }

        public Builder setEndTime(Date endTime) {
            event.setEndTime(endTime);
            return this;
        }

        public Builder setAgeLow(Integer ageLow) {
            event.setAgeLow(ageLow);
            return this;
        }

        public Builder setAgeHigh(Integer ageHigh) {
            event.setAgeHigh(ageHigh);
            return this;
        }

        public Builder setRoom(Room room) {
            event.setRoom(room);
            return this;
        }

        public Builder setDescription(String description) {
            event.setDescription(description);
            return this;
        }

        public Builder setRecurrentId(Long recurrentId) {
            event.setRecurrentId(recurrentId);
            return this;
        }

        public Builder setColor(String color) {
            event.setColor(color);
            return this;
        }

        public Builder setRecurrentType(EventConstants.TypeOfRecurentEvent recurrentType) {
            event.setRecurrentType(recurrentType);
            return this;
        }

        public Event build() {
            return new EventBuilder(this).getEvent();
        }
    }

    private EventBuilder(Builder builder) {
        this.event = builder.event;
    }

    public Event getEvent() {
        return event;
    }

}
