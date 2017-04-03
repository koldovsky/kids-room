package ua.softserveinc.tc.util;

import org.springframework.context.i18n.LocaleContextHolder;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CurrencyConverter {

    private static CurrencyConverter instance = null;

    protected CurrencyConverter() {
    }

    public static CurrencyConverter getInstance() {
        if (instance == null) {
            synchronized (CurrencyConverter.class) {
                if (instance == null) {
                    instance = new CurrencyConverter();
                }
            }
        }
        return instance;
    }

    public Map<?, String> convertCurrency(Map<?, Long> inputMap) {
        return inputMap.entrySet().stream().collect(Collectors.
                toMap(Map.Entry::getKey, e -> NumberFormat.getNumberInstance(LocaleContextHolder.getLocale()).format(e.getValue() / 100.0)));
    }

    public String convertSingle(Long sum) {
        return NumberFormat.getNumberInstance(LocaleContextHolder.getLocale()).format(sum / 100.0);
    }

    public List<BookingDto> convertBookingSum(List<Booking> inputList) {
        return inputList.stream()
                .map(booking -> {
                    BookingDto dto = booking.getDto();
                    dto.setCurrencySum(NumberFormat.getNumberInstance(LocaleContextHolder.getLocale()).format(booking.getSum() / 100.0));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public Long convertToLong(String summ){
        BigDecimal big = new BigDecimal(summ);
        return big.multiply(BigDecimal.valueOf(100)).longValue();
    }

}

