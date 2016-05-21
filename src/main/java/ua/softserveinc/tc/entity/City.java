package ua.softserveinc.tc.entity;

import org.hibernate.annotations.GenericGenerator;
import ua.softserveinc.tc.constants.ColumnConstants.CityConst;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Chak on 30.04.2016.
 */


@Entity
@Table(name = CityConst.TABLE_NAME_CITY)
public class City {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = CityConst.ID_CITY, nullable = false)
    private Long idCity;

    @Column(name = CityConst.NAME_CITY)
    private String nameCity;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "city")
//    private List<Room> rooms;

    public Long getIdCity() {
        return idCity;
    }

    public void setIdCity(Long idCity) {
        this.idCity = idCity;
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

//    public List<Room> getRooms() {
//        return rooms;
//    }
//
//    public void setRooms(List<Room> rooms) {
//        this.rooms = rooms;
//    }


//    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL, mappedBy = CityConst.ID_CITY)
//    @Column
//    private Set<Child> cities;
//
//    public Set<Child> getCities() {
//        return cities;
//    }
}
