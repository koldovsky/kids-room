package ua.softserveinc.tc.entity;

import org.hibernate.annotations.GenericGenerator;
import ua.softserveinc.tc.constants.RoomConstants;

import javax.persistence.*;
import java.util.List;


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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private List<Event> events;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "room")
    private List<Rate> rates;

    @Column(name = RoomConstants.WORKING_START_HOUR)
    private String workingHoursStart;

    @Column(name = RoomConstants.WORKING_END_HOUR)
    private String workingHoursEnd;

    @ManyToMany
    @JoinTable(name = RoomConstants.MANAGERS,
            joinColumns = @JoinColumn(name = RoomConstants.ROOM),
            inverseJoinColumns = @JoinColumn(name = RoomConstants.MANAGER))
    private List<User> managers;

    @Column(name = RoomConstants.ACTIVE)
    private boolean active;

    public Room() {
        //empty constructor for instantiating in controller
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

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Room room = (Room) o;

        return id != null ? id.equals(room.id) : room.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
