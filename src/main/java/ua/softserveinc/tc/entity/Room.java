package ua.softserveinc.tc.entity;

import org.hibernate.annotations.GenericGenerator;
import ua.softserveinc.tc.constants.RoomConstants;
import ua.softserveinc.tc.constants.UserConstants;
import ua.softserveinc.tc.dto.RoomDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Chak on 30.04.2016.
 */
@Entity
@Table(name = RoomConstants.TABLE_NAME_ROOMS)
public class Room {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = RoomConstants.ID_ROOM, unique = true, nullable = false)
    private Long id;

    @Column(name = RoomConstants.NAME_ROOM)
    private String name;

    @Column(name = RoomConstants.ADDRESS_ROOM)
    private String address;

    @Column(name = RoomConstants.CITY_ROOM)
    private String city;

    @Column(name = RoomConstants.PHONE_ROOM)
    private String phoneNumber;

    @Column(name = RoomConstants.CAPACITY_ROOM)
    private Integer capacity;

//    @OneToOne
//    @JoinColumn(name = UserConstants.Entity.ID_USER)
//    private User manager;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private List<Event> events;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "room")
    private List<Rate> rates = new LinkedList<>();

    @Column(name = RoomConstants.WORKING_START_HOUR)
    private String workingHoursStart;

    @Column(name = RoomConstants.WORKING_END_HOUR)
    private String workingHoursEnd;

    //    @JoinTable(name = RoomConstants.MANAGERS,
//            joinColumns = @JoinColumn(name = RoomConstants.ID_ROOM),
//            inverseJoinColumns = @JoinColumn(name = UserConstants.Entity.ID_USER))

    @ManyToMany
    @JoinTable(name = RoomConstants.MANAGERS,
            joinColumns = @JoinColumn(name = RoomConstants.ROOM),
            inverseJoinColumns = @JoinColumn(name = RoomConstants.MANAGER))
    private List<User> managers = new ArrayList<>();

    @Column(name = "active")
    private boolean active;

    public Room() {
        //TODO add comment what do this constructor
    }

    public Room(RoomDto roomDto) {
        this.id = roomDto.getId();
        this.name = roomDto.getName();
        this.address = roomDto.getAddress();
        this.city = roomDto.getCity();
        this.phoneNumber = roomDto.getPhoneNumber();
        this.capacity = roomDto.getCapacity();
        this.workingHoursStart = roomDto.getWorkingHoursStart();
        this.workingHoursEnd = roomDto.getWorkingHoursEnd();
        this.active = roomDto.isActive();

        List<Rate> rates = roomDto.fromJsonToListOfRates();
        for (Rate rate : rates) {
            this.addRate(rate);
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<User> getManagers() {
        return managers;
    }

    public void setManagers(List<User> managers) {
        this.managers = managers;
    }

    public String getWorkingHoursEnd() {
        return workingHoursEnd;
    }

    public void setWorkingHoursEnd(String workingHoursEnd) {
        this.workingHoursEnd = workingHoursEnd;
    }

    public String getWorkingHoursStart() {
        return workingHoursStart;
    }

    public void setWorkingHoursStart(String workingHoursStart) {
        this.workingHoursStart = workingHoursStart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    /**
     * Method check if there is no the same values in collection,
     * and if collection does not contains input value (rate) - add new note into collection.
     *
     * @param rate
     */
    public void addRate(Rate rate) {
        if (!this.rates.contains(rate)) {
            rate.setRoom(this);
            this.rates.add(rate);
        }
    }

    @Override
    public String toString() {
        return name;
    }


    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + phoneNumber.hashCode();
        result = 31 * result + capacity.hashCode();
        result = 31 * result + (workingHoursStart != null ? workingHoursStart.hashCode() : 0);
        result = 31 * result + (workingHoursEnd != null ? workingHoursEnd.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object that) {
        if (that == null) {
            return false;
        }
        if (this == that) {
            return true;
        }
        if (!(that instanceof Room)) {
            return false;
        }
        Room other = (Room) that;
        return city.equals(other.city) && address.equals(other.address);
    }
}
