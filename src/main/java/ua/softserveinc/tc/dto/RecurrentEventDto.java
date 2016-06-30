package ua.softserveinc.tc.dto;

import org.springframework.beans.factory.annotation.Autowired;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.mapper.GenericMapper;

import java.util.List;

/**
 * Created by dima- on 28.06.2016.
 */
public class RecurrentEventDto {

    @Autowired
    private GenericMapper<Event, EventDto> genericMapper;
    private long id;

    private String name;

    private String startTime;

    private String endTime;

    private Integer ageLow;

    private Integer ageHigh;

    private long roomId;

    private String description;

    private Long recurrentId;

    private String daysOfWeek;

    private transient Event event;

    public Event toEvent() {
        EventDto eventDto = new EventDto();
        eventDto.setName(this.getName());
        eventDto.setDescription(this.getDescription());
        eventDto.setStartTime(this.getStartTime());
        eventDto.setEndTime(this.getEndTime());
        eventDto.setRoomId(this.getRoomId());

        if (this.getId() != 0) {
            eventDto.setId(this.getId());
        }

        return genericMapper.toEntity(eventDto);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getAgeLow() {
        return ageLow;
    }

    public void setAgeLow(Integer ageLow) {
        this.ageLow = ageLow;
    }

    public Integer getAgeHigh() {
        return ageHigh;
    }

    public void setAgeHigh(Integer ageHigh) {
        this.ageHigh = ageHigh;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRecurrentId() {
        return recurrentId;
    }

    public void setRecurrentId(Long recurrentId) {
        this.recurrentId = recurrentId;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }
}
