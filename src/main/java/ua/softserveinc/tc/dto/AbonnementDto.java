package ua.softserveinc.tc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import ua.softserveinc.tc.entity.Abonnement;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class AbonnementDto implements Serializable {

    private long id;
    private String name;
    private int hour;
    private long price;
    private boolean isActive = true;

    public AbonnementDto() {
    }

    public AbonnementDto(Abonnement abonnement) {
        this.id = abonnement.getId();
        this.name = abonnement.getName();
        this.hour = abonnement.getHour();
        this.price = abonnement.getPrice();
        this.isActive = abonnement.getIsActive();
    }

    public Abonnement toEntity() {
        Abonnement abonnement = new Abonnement();
        abonnement.setId(this.id);
        abonnement.setName(this.name);
        abonnement.setHour(this.hour);
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

    @Override
    public String toString() {
        return "AbonnementDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hour=" + hour +
                ", price=" + price +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbonnementDto that = (AbonnementDto) o;
        return id == that.id &&
                hour == that.hour &&
                price == that.price &&
                isActive == that.isActive &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, hour, price, isActive);
    }
}
