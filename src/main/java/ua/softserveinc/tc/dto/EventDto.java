package ua.softserveinc.tc.dto;

import ua.softserveinc.tc.constants.DateConstants;
import ua.softserveinc.tc.entity.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by dima- on 07.05.2016.
 */
public class EventDto {

    private long id;

    private String name;

    private String startTime;

    private String endTime;

    private Integer ageLow;

    private Integer ageHigh;

    private long roomId;

    private String description;

    private Long recurrentId;

    private String color;

    public EventDto() {
    }

    public EventDto(Event event) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.name = event.getName();
        this.startTime = df.format(event.getStartTime());
        this.endTime = df.format(event.getEndTime());
        this.ageLow = event.getAgeLow();
        this.ageHigh = event.getAgeHigh();
        this.id = event.getId();
        this.description = event.getDescription();
        this.recurrentId = event.getRecurrentId();
        this.color = event.getColor();
        this.roomId = event.getRoom().getId();
    }


    public Long getRecurrentId() {
        return recurrentId;
    }

    public void setRecurrentId(Long recurrentId) {
        this.recurrentId = recurrentId;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
