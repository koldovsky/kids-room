package ua.softserveinc.tc.dto;

import ua.softserveinc.tc.entity.Rate;
import ua.softserveinc.tc.util.CurrencyConverter;


public class RateDto {

    private String hourRate;

    private String priceRate;

    public RateDto() {
        //empty constructor for instantiating in controller
    }

    public RateDto(Rate rate) {
        this.hourRate = rate.getHourRate().toString();
        this.priceRate = CurrencyConverter.getInstance().convertSingle(rate.getPriceRate());
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

    public Long getLongPrice(){
        return CurrencyConverter.getInstance().convertToLong(priceRate);
    }

     public Integer getIntegerHour(){
        return Integer.valueOf(hourRate);
    }

}
