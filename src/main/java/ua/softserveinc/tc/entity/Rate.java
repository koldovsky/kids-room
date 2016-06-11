package ua.softserveinc.tc.entity;

import org.hibernate.annotations.GenericGenerator;
import ua.softserveinc.tc.constants.RoomConstants;
import ua.softserveinc.tc.constants.column.RateConst;

import javax.persistence.*;

/**
 * Created by TARAS on 19.05.2016.
 */
@Entity
@Table(name = RateConst.TABLE_RATES)
public class Rate {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = RateConst.ID_RATE, nullable = false)
    private Long idRate;

    @Column(name = RateConst.HOUR_RATE, nullable = false)
    private Integer hourRate;

    @Column(name = RateConst.PRICE_RATE, nullable = false)
    private Long priceRate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = RoomConstants.ID_ROOM)
    private Room room;

    public Rate() {
        //TODO add comment what do this constructor
    }

    public Rate(Integer hourRate, Long priceRate) {
        this.hourRate = hourRate;
        this.priceRate = priceRate;
    }

    public Long getIdRate() {
        return idRate;
    }

    public void setIdRate(Long idRate) {
        this.idRate = idRate;
    }

    public Integer getHourRate() {
        return hourRate;
    }

    public void setHourRate(Integer hourRate) {
        this.hourRate = hourRate;
    }

    public Long getPriceRate() {
        return priceRate;
    }

    public void setPriceRate(Long priceRate) {
        this.priceRate = priceRate;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        room.getRates().add(this);
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Rate rate = (Rate) o;

        return hourRate.equals(rate.hourRate);
    }

    @Override
    public int hashCode() {
        return hourRate.hashCode();
    }

    @Override
    public String toString() {
        return "Rate{" +
                "priceRate=" + priceRate +
                ", hourRate=" + hourRate +
                '}';
    }

}