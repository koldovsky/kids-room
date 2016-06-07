package ua.softserveinc.tc.dto;

/**
 * Created by TARAS on 19.05.2016.
 */
public class RateDtosss {

    private String hourRate;

    private String priceRate;

    public RateDtosss() {
    }

    public RateDtosss(String hourRate, String priceRate) {
        this.hourRate = hourRate;
        this.priceRate = priceRate;
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
