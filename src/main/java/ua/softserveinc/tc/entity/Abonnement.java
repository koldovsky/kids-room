package ua.softserveinc.tc.entity;

import org.hibernate.annotations.GenericGenerator;
import ua.softserveinc.tc.constants.AbonnementConstants;
import ua.softserveinc.tc.dto.AbonnementDto;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = AbonnementConstants.Entity.TABLE_NAME_ABONEMENT)
public class Abonnement {
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = AbonnementConstants.Entity.ID_ABONEMENT, nullable = false)
    private long id;

    @Column(name = AbonnementConstants.Entity.NAME_ABONEMENT)
    private String name;

    @Column(name = AbonnementConstants.Entity.HOUR)
    private int hour;

    @Column(name = AbonnementConstants.Entity.PRICE)
    private long price;

    @Column(name = AbonnementConstants.Entity.ACTIVE,
            nullable = false,
            columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isActive = true;

    @Column(name = AbonnementConstants.Entity.START_DATE)
    private Date startDate;

    @Column(name = AbonnementConstants.Entity.END_DATE)
    private Date endDate;

    @ManyToMany
    @JoinTable(name = AbonnementConstants.Entity.USERS_ABONEMENTS,
            joinColumns = @JoinColumn(name = AbonnementConstants.Entity.ABONEMENT),
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

        if (id != that.id) return false;
        if (hour != that.hour) return false;
        if (price != that.price) return false;
        if (isActive != that.isActive) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        return abonementUsers != null ? abonementUsers.equals(that.abonementUsers) : that.abonementUsers == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + hour;
        result = 31 * result + (int) (price ^ (price >>> 32));
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (abonementUsers != null ? abonementUsers.hashCode() : 0);
        return result;
    }
}
