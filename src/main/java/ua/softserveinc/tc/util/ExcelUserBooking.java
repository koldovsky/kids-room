package ua.softserveinc.tc.util;

import org.springframework.stereotype.Component;
import ua.softserveinc.tc.constants.ExcelConstants;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ExcelUserBooking implements ExcelData<BookingDto> {

    public static String[] ADDITIONAL_EXCEL_FIELDS = { "TOTAL SUM: ", " UAH", "ROOM: ", "PARENT: " };

    private Map<String, List<String>> tableData;
    private List<String> additionalFields;

    @Override
    public void setTableData(List<BookingDto> bookingDtos) {
        tableData = new LinkedHashMap<>();
        additionalFields = new ArrayList<>();

        tableData.put(ExcelConstants.Headers.BOOKING_DATE,
                bookingDtos.stream().map(BookingDto::getDate).collect(Collectors.toList()));
        tableData.put(ExcelConstants.Headers.KID,
                bookingDtos.stream().map(BookingDto::getKidName).collect(Collectors.toList()));
        long uniqueRoomNameCount = bookingDtos.stream().map(BookingDto::getRoomName).distinct().count();
        if (uniqueRoomNameCount > 1 ) {
            tableData.put(ExcelConstants.Headers.PLACE,
                    bookingDtos.stream().map(BookingDto::getRoomName).collect(Collectors.toList()));
        } else if (uniqueRoomNameCount != 0) {
            addAdditionalFields(ExcelConstants.Fields.ROOM + bookingDtos.get(0).getRoomName());
        }
        tableData.put(ExcelConstants.Headers.BOOKING_START_TIME,
                bookingDtos.stream().map(BookingDto::getStartTime).collect(Collectors.toList()));
        tableData.put(ExcelConstants.Headers.BOOKING_END_TIME,
                bookingDtos.stream().map(BookingDto::getEndTime).collect(Collectors.toList()));
        tableData.put(ExcelConstants.Headers.BOOKING_DURATION,
                bookingDtos.stream().map(BookingDto::getDuration).collect(Collectors.toList()));
        tableData.put(ExcelConstants.Headers.ABONNEMENT,
                bookingDtos.stream().map(BookingDto::getAbonnement).collect(Collectors.toList()));
        tableData.put(ExcelConstants.Headers.DISCOUNT, bookingDtos.stream()
                .map(BookingDto::getDiscount)
                .map(s -> s != null ? s : ExcelConstants.Other.NOT_PROVIDED)
                .map(s -> s.replace("_", "\n"))
                .collect(Collectors.toList()));
        tableData.put(ExcelConstants.Headers.SUM,
                bookingDtos.stream().map(BookingDto::getCurrencySum).collect(Collectors.toList()));
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
