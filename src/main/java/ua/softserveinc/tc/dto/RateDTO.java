package ua.softserveinc.tc.dto;

/**
 * Created by TARAS on 19.05.2016.
 */
public class RateDTO implements BaseDTO {

    private String hourRate;

    private String priceRate;

    public RateDTO() {
    }

    public RateDTO(String hourRate, String valueRate) {
        this.hourRate = hourRate;
        this.priceRate = valueRate;
    }


    public String getHourRate() {
        return hourRate;
    }

    public void setHourRate(String hourRate) {
        this.hourRate = hourRate;
    }

    public String getValueRate() {
        return priceRate;
    }

    public void setValueRate(String valueRate) {
        this.priceRate = valueRate;
    }
}
