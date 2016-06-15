package ua.softserveinc.tc.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.validator.constraints.NotEmpty;
import ua.softserveinc.tc.entity.Rate;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by TARAS on 24.05.2016.
 */
public class RoomDto {

    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String address;

    @NotEmpty
    private String city;

    @NotEmpty
    private String phoneNumber;

    @NotNull
    private Integer capacity;

    private String managers;

    @NotEmpty
    private String workingHoursStart;

    @NotEmpty
    private String workingHoursEnd;

    private String rate;

    private boolean active;

    private Long sum;

    public RoomDto() {
        //TODO add comment what do this constructor
    }


    public RoomDto(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.address = room.getAddress();
        this.city = room.getCity();
        this.phoneNumber = room.getPhoneNumber();
        this.capacity = room.getCapacity();
        this.workingHoursStart = room.getWorkingHoursStart();
        this.workingHoursEnd = room.getWorkingHoursEnd();
        this.active = room.isActive();

        Gson gson = new Gson();
        this.setRate(gson.toJson(room.getRates().stream()
                .map(RateDto::new)
                .collect(Collectors.toList())));
    }

    public RoomDto(Room room, Long sum) {
        this.name = room.getName();
        this.city = room.getCity();
        this.address = room.getAddress();
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

    public String getManagers() {
        return managers;
    }

    public void setManagers(String managers) {
        this.managers = managers;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

        for (RateDto rateDto : stringList) {
            if (rateDto.getHourRate() != null && rateDto.getPriceRate() != null) {
                result.add(new Rate(Integer.parseInt(rateDto.getHourRate()), Long.parseLong(rateDto.getPriceRate())));
            }
        }
        return result;
    }

    public List<Long> fromJsonToListOfManagersId() {
        List<Long> result = new ArrayList<>();

        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();

        ManagerDto[] managerList = gson.fromJson(this.managers, ManagerDto[].class);

        for (ManagerDto managerDto : managerList) {
            if (managerDto != null) {
                result.add(Long.parseLong(managerDto.getManagerId()));
            }
        }
        return result;
    }
}
