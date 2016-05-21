package ua.softserveinc.tc.dto;

/**
 * Created by TARAS on 19.05.2016.
 */
public class RateDTO implements BaseDTO {

    private String hourRate;

    private String valueRate;

    public RateDTO() {
    }

    public RateDTO(String hourRate, String valueRate) {
        this.hourRate = hourRate;
        this.valueRate = valueRate;
    }


    public String getHourRate() {
        return hourRate;
    }

    public void setHourRate(String hourRate) {
        this.hourRate = hourRate;
    }

    public String getValueRate() {
        return valueRate;
    }

    public void setValueRate(String valueRate) {
        this.valueRate = valueRate;
    }
}
