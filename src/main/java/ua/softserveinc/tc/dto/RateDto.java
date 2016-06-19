package ua.softserveinc.tc.dto;

import ua.softserveinc.tc.entity.Rate;

/**
 * Created by TARAS on 19.05.2016.
 */
public class RateDto {

    private String hourRate;

    private String priceRate;

    public RateDto() {
        //empty constructor for instantiating in controller
    }

    public RateDto(Rate rate) {
        this.hourRate = rate.getHourRate().toString();
        this.priceRate = rate.getPriceRate().toString();
    }

    public String getHourRate() {
        return hourRate;
    }

    public void setHourRate(String hourRate) {
        this.hourRate = hourRate;
    }

    public String getPriceRate() {
        return priceRate;
    }

    public void setPriceRate(String priceRate) {
        this.priceRate = priceRate;
    }
}
