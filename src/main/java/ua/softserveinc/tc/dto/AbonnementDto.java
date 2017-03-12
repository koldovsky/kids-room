package ua.softserveinc.tc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import ua.softserveinc.tc.entity.Abonnement;

import java.io.Serializable;
import java.util.Date;

@JsonInclude(Include.NON_NULL)
public class AbonnementDto implements Serializable {

    private long id;
    private String name;
    private int hour;
    private long price;
    private boolean isActive;
    private Date startDate;
    private Date endDate;

    public AbonnementDto() {
    }

    public AbonnementDto(Abonnement abonnement) {
        this.id = abonnement.getId();
        this.name = abonnement.getName();
        this.hour = abonnement.getHour();
        this.price = abonnement.getPrice();
        this.isActive = abonnement.getIsActive();
        this.startDate = abonnement.getStartDate();
        this.endDate = abonnement.getEndDate();
    }

    public Abonnement toEntity() {
        Abonnement abonnement = new Abonnement();
        abonnement.setId(this.id);
        abonnement.setName(this.name);
        abonnement.setHour(this.hour);
        abonnement.setStartDate(this.startDate);
        abonnement.setEndDate(this.endDate);
        abonnement.setPrice(this.price);
        abonnement.setActive(this.isActive);
        return abonnement;
    }

    public static class Builder {

        private AbonnementDto abonnementDto;

        public Builder() {
            abonnementDto = new AbonnementDto();
        }

        public Builder withId(Long id) {
            this.abonnementDto.setId(id);
            return this;
        }

        public Builder withName(String name) {
            this.abonnementDto.setName(name);
            return this;
        }

        public Builder withHour(int hour) {
            this.abonnementDto.setHour(hour);
            return this;
        }

        public Builder withPrice(long price) {
            this.abonnementDto.setPrice(price);
            return this;
        }

        public Builder withActive(boolean isActive) {
            this.abonnementDto.setActive(isActive);
            return this;
        }

        public Builder withStartDate(Date startDate) {
            this.abonnementDto.setStartDate(startDate);
            return this;
        }

        public Builder withEndDate(Date endDate) {
            this.abonnementDto.setEndDate(endDate);
            return this;
        }

        public AbonnementDto build() {
            return this.abonnementDto;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
