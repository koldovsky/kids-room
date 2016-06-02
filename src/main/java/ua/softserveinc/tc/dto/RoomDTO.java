package ua.softserveinc.tc.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import ua.softserveinc.tc.entity.Rate;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TARAS on 24.05.2016.
 */
public class RoomDTO {

    private Long id;

    private String name;

    private String address;

    private String city;

    private String phoneNumber;

    private Integer capacity;

    private User manager;

    private String rate;

    private Long sum;

    public RoomDTO() {
    }

    public RoomDTO(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.address = room.getAddress();
        this.city = room.getCity();
        this.phoneNumber = room.getPhoneNumber();
        this.capacity = room.getCapacity();
        this.manager = room.getManager();

        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.registerTypeAdapter(Rate.class, new RateTypeAdapter()).create();

        this.rate = gson.toJson(room.getRates());
    }

    public RoomDTO(Room room, Long sum) {
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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "RoomDTO{" +
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

        RateDTO[] stringList = gson.fromJson(this.rate, RateDTO[].class);

        for (RateDTO rate : stringList) {
            if (rate.getHourRate() != null && rate.getPriceRate() != null) {
                result.add(new Rate(Integer.parseInt(rate.getHourRate()), Long.parseLong(rate.getPriceRate())));
            }
        }

        return result;
    }

    class RateTypeAdapter extends TypeAdapter<Rate> {

        /**
         * Method to get String from object Rate.
         *
         * @param jsonWriter
         * @param rate
         * @throws IOException
         */
        @Override
        public void write(JsonWriter jsonWriter, Rate rate) throws IOException {
            if (rate == null) {
                jsonWriter.nullValue();
                return;
            }
            String result = "hourRate:" + rate.getHourRate() + "|priceRate:" + rate.getPriceRate();
            jsonWriter.value(result);
        }

        @Deprecated
        @Override
        public Rate read(JsonReader jsonReader) throws IOException {
            return null;
        }
    }
}
