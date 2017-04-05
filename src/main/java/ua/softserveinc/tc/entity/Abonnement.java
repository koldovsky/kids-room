package ua.softserveinc.tc.entity;

import org.hibernate.annotations.GenericGenerator;
import ua.softserveinc.tc.constants.AbonnementConstants;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = AbonnementConstants.Entity.TABLE_NAME_ABONEMENT)
public class Abonnement {
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = AbonnementConstants.Entity.ID_ABONEMENT, nullable = false)
    private long id;

    @Column(name = AbonnementConstants.Entity.NAME_ABONEMENT,
            unique = true)
    private String name;

    @Column(name = AbonnementConstants.Entity.HOUR)
    private int hour;

    @Column(name = AbonnementConstants.Entity.PRICE)
    private long price;

    @Column(name = AbonnementConstants.Entity.ACTIVE,
            nullable = false,
            columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isActive = true;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "abonnement")
    private List<SubscriptionAssignment> subscriptionAssignments;

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

    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Abonnement that = (Abonnement) o;
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
