package ua.softserveinc.tc.util;

import org.springframework.context.i18n.LocaleContextHolder;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CurrencyConverter {

    private static final CurrencyConverter inctance = new CurrencyConverter();

    private CurrencyConverter() {
    }

    public static CurrencyConverter getInctance() {
        return inctance;
    }

    public static Map<?, String> convertCurrency(Map<?, Long> inputMap) {
        return inputMap.entrySet().stream().collect(Collectors.
                toMap(Map.Entry::getKey, e -> NumberFormat.getNumberInstance(LocaleContextHolder.getLocale()).format(e.getValue() / 100.0)));
    }

    public static String convertSingle(Long sum) {
        return NumberFormat.getNumberInstance(LocaleContextHolder.getLocale()).format(sum / 100.0);
    }

    public static List<BookingDto> convertBookingSum(List<Booking> inputList) {
        return inputList.stream()
                .map(booking -> {
                    BookingDto dto = booking.getDto();
                    dto.setCurrencySum(NumberFormat.getNumberInstance(LocaleContextHolder.getLocale()).format(booking.getSum() / 100.0));
                    return dto;
                })
                .collect(Collectors.toList());
    }
}

