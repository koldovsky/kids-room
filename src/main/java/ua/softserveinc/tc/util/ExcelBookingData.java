package ua.softserveinc.tc.util;

import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.User;

import java.util.Map;

public interface ExcelBookingData<T> extends ExcelData<BookingDto> {

    void setTableData(Map<T, String> report);
}
