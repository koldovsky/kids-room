package ua.softserveinc.tc.util;

import org.springframework.stereotype.Component;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ExcelUserRoomBooking implements ExcelBookingData {

    public static String[] ADDITIONAL_EXCEL_FIELDS = { "TOTAL SUM: ", " UAH", "ROOM: ", "PARENT: " };

    private Map<String, List<String>> tableData;
    private List<String> additionalFields;

    @Override
    public void setTableData(List<BookingDto> bookingDtos) {
        tableData = new LinkedHashMap<>();
        additionalFields = new ArrayList<>();

        this.addAdditionalFields(bookingDtos.stream().mapToLong(BookingDto::getSum).sum());
        tableData.put("Date",
                bookingDtos.stream().map(BookingDto::getDate).collect(Collectors.toList()));
        tableData.put("Kid",
                bookingDtos.stream().map(BookingDto::getKidName).collect(Collectors.toList()));
        long uniqueRoomNameCount = bookingDtos.stream().map(BookingDto::getRoomName).distinct().count();
        if (uniqueRoomNameCount > 1 ) {
            tableData.put("Place",
                    bookingDtos.stream().map(BookingDto::getRoomName).collect(Collectors.toList()));
        } else if (uniqueRoomNameCount != 0) {
            addAdditionalFields(ExcelUserRoomBooking.ADDITIONAL_EXCEL_FIELDS[2] +
                    bookingDtos.get(0).getRoomName());
        }
        tableData.put("Start time",
                bookingDtos.stream().map(BookingDto::getStartTime).collect(Collectors.toList()));
        tableData.put("End time",
                bookingDtos.stream().map(BookingDto::getEndTime).collect(Collectors.toList()));
        tableData.put("Duration",
                bookingDtos.stream().map(BookingDto::getDuration).collect(Collectors.toList()));
        tableData.put("Sum",
                bookingDtos.stream().map(BookingDto::getSum).map(p -> p / 100.0).map(String::valueOf)
                        .collect(Collectors.toList()));
    }

    @Override
    public void setTableData(Map<User, Long> listOfParents) {
        tableData = new LinkedHashMap<>();
        additionalFields = new ArrayList<>();

        tableData.put("Parent", listOfParents.keySet().stream()
                .map(User::getFullName).collect(Collectors.toList()));
        tableData.put("Email", listOfParents.keySet().stream()
                .map(User::getEmail).collect(Collectors.toList()));
        tableData.put("Sum", listOfParents.values().stream().map(p -> p / 100.0)
                .map(String::valueOf).collect(Collectors.toList()));

        addAdditionalFields(listOfParents.values().stream().mapToLong(Long::longValue).sum());
    }

    private void addAdditionalFields(long TotalSum) {
        additionalFields.add(ExcelUserRoomBooking.ADDITIONAL_EXCEL_FIELDS[0] +
                String.valueOf(TotalSum/100.0) + ExcelUserRoomBooking.ADDITIONAL_EXCEL_FIELDS[1]);
    }

    @Override
    public void addAdditionalFields(String field) {
        additionalFields.add(field);
    }

    @Override
    public Map<String, List<String>> getTableData() {
        return this.tableData;
    }

    @Override
    public List<String> getAdditionalFields() {
        return this.additionalFields;
    }

    @Override
    public String[] getHeaders() {
        return tableData.keySet().toArray(new String[]{""});
    }

    @Override
    public int getSize() {
        return tableData.get(getHeaders()[0]).size();
    }

    @Override
    public boolean hasAdditionalFields() {
        return true;
    }
}
