package ua.softserveinc.tc.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ua.softserveinc.tc.entity.Rate;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by TARAS on 24.05.2016.
 */
public class RoomDto {

    private Long id;

    private String name;

    private String address;

    private String city;

    private String phoneNumber;

    private Integer capacity;

    private User manager;

    private String workingHoursStart;

    private String workingHoursEnd;

    private String rate;

    private Long sum;

    public RoomDto() {
    }


    public RoomDto(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.address = room.getAddress();
        this.city = room.getCity();
        this.phoneNumber = room.getPhoneNumber();
        this.capacity = room.getCapacity();
        this.manager = room.getManager();
        this.workingHoursStart = room.getWorkingHoursStart();
        this.workingHoursEnd = room.getWorkingHoursEnd();

        Gson gson = new Gson();
        this.setRate(gson.toJson(room.getRates().stream()
                .map(RateDto::new)
                .collect(Collectors.toList())));
    }

    public RoomDto(Room room, Long sum) {
        this.name = room.getName();
        this.city = room.getCity();
        this.address = room.getAddress();
        this.manager = room.getManager();
        this.sum = sum;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public String getWorkingHoursStart() {
        return workingHoursStart;
    }

    public void setWorkingHoursStart(String workingHoursStart) {
        this.workingHoursStart = workingHoursStart;
    }

    public String getWorkingHoursEnd() {
        return workingHoursEnd;
    }

    public void setWorkingHoursEnd(String workingHoursEnd) {
        this.workingHoursEnd = workingHoursEnd;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "RoomDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", capacity=" + capacity +
                ", manager=" + manager +
                ", rate='" + rate + '\'' +
                '}';
    }

    /**
     * Method convert JSON format into list of Rate typed object's.
     *
     * @return List<Rate> (converted from JSON format)
     */
    public List<Rate> fromJsonToListOfRates() {
        List<Rate> result = new ArrayList<>();

        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();

        RateDto[] stringList = gson.fromJson(this.rate, RateDto[].class);

        for (RateDto rate : stringList) {
            if (rate.getHourRate() != null && rate.getPriceRate() != null) {
                result.add(new Rate(Integer.parseInt(rate.getHourRate()), Long.parseLong(rate.getPriceRate())));
            }
        }

        return result;
    }
}
