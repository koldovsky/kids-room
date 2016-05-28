package ua.softserveinc.tc.entity;

import org.hibernate.annotations.GenericGenerator;
import ua.softserveinc.tc.constants.EntityConstants.RoomConst;
import ua.softserveinc.tc.constants.EntityConstants.UserConst;
import ua.softserveinc.tc.dto.RoomDTO;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Chak on 30.04.2016.
 */
@Entity
@Table(name = RoomConst.TABLE_NAME_ROOMS)
public class Room {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = RoomConst.ID_ROOM, unique = true, nullable = false)
    private Long id;

    @Column(name = RoomConst.NAME_ROOM)
    private String name;

    @Column(name = RoomConst.ADDRESS_ROOM)
    private String address;

    @Column(name = RoomConst.CITY_ROOM)
    private String city;

    @Column(name = RoomConst.PHONE_ROOM)
    private String phoneNumber;

    @Column(name = RoomConst.CAPACITY_ROOM)
    private Integer capacity;

    @OneToOne
    @JoinColumn(name = UserConst.ID_USER)
    private User manager;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private List<Event> events;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "room")
    private List<Rate> rates = new LinkedList<>();

    public Room() {
    }

    public Room(RoomDTO roomDTO) {
        this.id = roomDTO.getId();
        this.name = roomDTO.getName();
        this.address = roomDTO.getAddress();
        this.city = roomDTO.getCity();
        this.phoneNumber = roomDTO.getPhoneNumber();
        this.capacity = roomDTO.getCapacity();
        this.manager = roomDTO.getManager();

        List<Rate> rates = roomDTO.fromJsonToListOfRates();
        for (Rate rate : rates) {
            this.addRate(rate);
        }
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

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
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
        return 750 * Objects.hashCode(city)
                + 13 * Objects.hashCode(address);
    }

    @Override
    public boolean equals(Object that) {
        if (that == null) return false;
        if (this == that) return true;
        if (!(that instanceof Room)) return false;
        Room other = (Room) that;
        return city.equals(other.city) && address.equals(other.address);
    }
}
