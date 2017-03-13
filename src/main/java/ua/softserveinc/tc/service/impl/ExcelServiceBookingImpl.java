package ua.softserveinc.tc.service.impl;

import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.service.ExcelService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExcelServiceBookingImpl implements ExcelService<BookingDto> {

    @Override
    public Map<String, List<String>> getDataFromDto(List<BookingDto> bookingDtos) {
        Map<String, List<String>> stringListMap = new LinkedHashMap<>();

        stringListMap.put("Date",
                bookingDtos.stream().map(BookingDto::getDate).collect(Collectors.toList()));
        stringListMap.put("Kid",
                bookingDtos.stream().map(BookingDto::getKidName).collect(Collectors.toList()));
        stringListMap.put("Place",
                bookingDtos.stream().map(BookingDto::getRoomName).collect(Collectors.toList()));
        stringListMap.put("Start time",
                bookingDtos.stream().map(BookingDto::getStartTime).collect(Collectors.toList()));
        stringListMap.put("End time",
                bookingDtos.stream().map(BookingDto::getEndTime).collect(Collectors.toList()));
        stringListMap.put("Duration",
                bookingDtos.stream().map(BookingDto::getDuration).collect(Collectors.toList()));
        stringListMap.put("Sum",
                bookingDtos.stream().map(BookingDto::getSum).map(p -> p / 100.0).map(String::valueOf)
                        .collect(Collectors.toList()));

        return stringListMap;
    }
}
