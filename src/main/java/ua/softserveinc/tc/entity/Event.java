package ua.softserveinc.tc.entity;

import org.hibernate.annotations.GenericGenerator;
import ua.softserveinc.tc.constants.EventConstants;
import ua.softserveinc.tc.util.DateUtil;

import javax.persistence.*;
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

    @Column(name = EventConstants.Entity.ID_RECURRENT, columnDefinition = "bigint default NULL")
    private Long recurrentId;

    @Column(name = EventConstants.Entity.COLOR, columnDefinition = "char(7) default '#6AA4C1'")
    private String color;

    public Long getRecurrentId() {
        return recurrentId;
    }

    public void setRecurrentId(Long recurrentId) {
        this.recurrentId = recurrentId;
    }

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Event event = (Event) o;

        if (!name.equals(event.name)) {
            return false;
        }
        if (!startTime.equals(event.startTime)) {
            return false;
        }
        if (!endTime.equals(event.endTime)) {
            return false;
        }
        if (ageLow != null ? !ageLow.equals(event.ageLow) : event.ageLow != null) {
            return false;
        }
        if (ageHigh != null ? !ageHigh.equals(event.ageHigh) : event.ageHigh != null) {
            return false;
        }
        return room.equals(event.room);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + startTime.hashCode();
        result = 31 * result + endTime.hashCode();
        result = 31 * result + (ageLow != null ? ageLow.hashCode() : 0);
        result = 31 * result + (ageHigh != null ? ageHigh.hashCode() : 0);
        result = 31 * result + room.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return " " + name + " " + DateUtil.toIsoString(startTime) +
                " " + DateUtil.toIsoString(endTime) + " ";
    }
}