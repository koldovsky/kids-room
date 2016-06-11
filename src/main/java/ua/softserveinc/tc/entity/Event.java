package ua.softserveinc.tc.entity;

import org.hibernate.annotations.GenericGenerator;
import ua.softserveinc.tc.constants.EventConstants;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nestor on 30.04.2016.
 */

@Entity
@Table(name = EventConstants.Entity.TABLENAME)
public class Event {
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = EventConstants.Entity.ID, unique = true, nullable = false)
    private Long id;

    @Column(name = EventConstants.Entity.NAME, nullable = false)
    private String name;

    @Column(name = EventConstants.Entity.START_TIME, nullable = false)
    private Date startTime;

    @Column(name = EventConstants.Entity.END_TIME, nullable = false)
    private Date endTime;

    @Column(name = EventConstants.Entity.AGE_LOW)
    private Integer ageLow;

    @Column(name = EventConstants.Entity.AGE_HIGH)
    private Integer ageHigh;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = EventConstants.Entity.ID_ROOM, nullable = false)
    private Room room;

    @Column(name = EventConstants.Entity.DESCRIPTION)
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return " " + name + " " + df.format(startTime) +
                " " + df.format(endTime) + " ";
    }
}