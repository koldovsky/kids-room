package ua.softserveinc.tc.entity;

import org.hibernate.annotations.GenericGenerator;
import ua.softserveinc.tc.constants.AbonnementConstants;

import javax.persistence.*;
import java.util.Date;
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

    @ManyToMany
    @JoinTable(name = AbonnementConstants.Entity.USERS_ABONEMENTS,
            joinColumns = @JoinColumn(name = AbonnementConstants.Entity.ABONNEMENT),
            inverseJoinColumns = @JoinColumn(name = AbonnementConstants.Entity.USER))
    private List<User> abonementUsers;

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

    public List<User> getAbonementUsers() {
        return abonementUsers;
    }

    public void setAbonementUsers(List<User> abonementUsers) {
        this.abonementUsers = abonementUsers;
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
                Objects.equals(name, that.name) &&
                Objects.equals(abonementUsers, that.abonementUsers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, hour, price, isActive, abonementUsers);
    }
}
