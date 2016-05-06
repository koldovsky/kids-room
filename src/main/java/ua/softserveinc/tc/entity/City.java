package ua.softserveinc.tc.entity;

import org.hibernate.annotations.GenericGenerator;
import ua.softserveinc.tc.entity.ColumnConstants.CityConst;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Chak on 30.04.2016.
 */

@Entity
@Table(name = "cities")
public class City {
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = CityConst.ID_CITY, unique = true, nullable = false)
    private Long id;

    @Column(name = CityConst.NAME_CITY)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "city")
    private List<Room> rooms;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
