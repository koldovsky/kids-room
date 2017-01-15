package ua.softserveinc.tc.util;

import ua.softserveinc.tc.entity.Rate;

import java.util.Arrays;
import java.util.List;

public class RateUtils {

    public static List<Rate> getListOfRates() {
        Rate rate1 = new Rate();
        Rate rate2 = new Rate();
        Rate rate3 = new Rate();

        return Arrays.asList(rate1, rate2, rate3);
    }

}
