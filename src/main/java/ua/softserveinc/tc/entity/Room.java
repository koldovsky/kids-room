package ua.softserveinc.tc.entity;

import org.hibernate.annotations.GenericGenerator;
import ua.softserveinc.tc.constants.ColumnConstants.CityConst;
import ua.softserveinc.tc.constants.ColumnConstants.RoomConst;
import ua.softserveinc.tc.constants.ColumnConstants.UserConst;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Column(name = RoomConst.PHONE_ROOM)
    private String phoneNumber;
    @Column(name = RoomConst.CAPACITY_ROOM)
    private Integer capacity;
    @OneToOne
    @JoinColumn(name = UserConst.ID_USER)
    private User manager;
    @ManyToOne
    @JoinColumn(name = CityConst.ID_CITY)
    private City city;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private List<Event> events;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "idRate")
    private List<Rate> rates;

    @ElementCollection
    @JoinTable(name = RoomConst.TABLE_NAME_PRICES,
            joinColumns = @JoinColumn(name = RoomConst.ID_ROOM))
    @MapKeyColumn(name = RoomConst.HOUR)
    @Column(name = RoomConst.PRICE)
    private Map<Integer, Long> prices = new HashMap<>();

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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
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

    public Map<Integer, Long> getPrices() {
        return prices;
    }

    public void setPrices(Map<Integer, Long> prices) {
        this.prices = prices;
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
}
