package ua.softserveinc.tc.dto;


import ua.softserveinc.tc.entity.Rate;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.util.JsonUtil;
import ua.softserveinc.tc.validator.annotation.RateValidation;
import ua.softserveinc.tc.validator.annotation.UniqueManagerValidation;

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

    private String workingHoursStart;

    private String workingHoursEnd;

    @UniqueManagerValidation
    private String managers;

    @RateValidation
    private String rate;

    private String timeStartEnd;

    private List<String> namesOfManagers;

    private boolean active;

    private Long sum;

    public RoomDto() {
        //empty constructor for instantiating in controller
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

        this.managers = JsonUtil.toJson(room.getManagers().stream()
                .map(UserDto::new)
                .collect(Collectors.toList()));
        this.rate = JsonUtil.toJson(room.getRates().stream()
                .map(RateDto::new)
                .collect(Collectors.toList()));
    }

    public RoomDto(Room room, Long sum) {
        this.name = room.getName();
        this.city = room.getCity();
        this.address = room.getAddress();
        this.sum = sum;
        this.namesOfManagers = room.getManagers().stream()
                .map(User::getFullName)
                .collect(Collectors.toList());
    }

    public static Room getRoomObjectFromDtoValues(RoomDto roomDto) {
        Room resultRoom = new Room();
        resultRoom.setId(roomDto.id);
        resultRoom.setName(roomDto.name);
        resultRoom.setAddress(roomDto.address);
        resultRoom.setCity(roomDto.city);
        resultRoom.setPhoneNumber(roomDto.phoneNumber);
        resultRoom.setCapacity(roomDto.capacity);
        resultRoom.setWorkingHoursStart(roomDto.workingHoursStart);
        resultRoom.setWorkingHoursEnd(roomDto.workingHoursEnd);
        resultRoom.setActive(roomDto.active);
        resultRoom.setRates(JsonUtil.fromJsonList(roomDto.getRate(), Rate[].class));
        resultRoom.getRates().stream().forEach(r -> r.setRoom(resultRoom));
        return resultRoom;
    }

    public String getTimeStartEnd() {
        return timeStartEnd;
    }

    public void setTimeStartEnd(String timeStartEnd) {
        this.timeStartEnd = timeStartEnd;
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

    public String getManagers() {
        return managers;
    }

    public void setManagers(String managers) {
        this.managers = managers;
    }

    public List<String> getNamesOfManagers() {
        return namesOfManagers;
    }

    public void setNamesOfManagers(List<String> namesOfManagers) {
        this.namesOfManagers = namesOfManagers;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
                ", rate='" + rate + '\'' +
                '}';
    }
}
