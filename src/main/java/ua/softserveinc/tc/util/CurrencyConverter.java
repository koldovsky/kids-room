package ua.softserveinc.tc.util;

import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyConverter<T> {
    public Map<T, BigDecimal> convertCurrency(Map<T, Long> inputMap) {
        Map<T, BigDecimal> resultMap = new HashMap<T, BigDecimal>();
        for (T key : inputMap.keySet()) {
            resultMap.put((T) key, new BigDecimal(inputMap.get(key) / 100.0).setScale(2, RoundingMode.HALF_UP));
        }
        return resultMap;

    }

    public BigDecimal convertSingle(Long sum) {
        return new BigDecimal(sum / 100.0).setScale(2, RoundingMode.HALF_UP);
    }

    public List<BookingDto> convertBookingSum(List<Booking> inputList) {
        List<BookingDto> resultList = new ArrayList<BookingDto>();
        for (Booking booking : inputList) {
            BookingDto sto = booking.getDto();
            sto.setCurrencySum(new BigDecimal(booking.getSum() / 100.0).setScale(2, BigDecimal.ROUND_HALF_UP));
            resultList.add(sto);
        }
        return resultList;
    }
}
