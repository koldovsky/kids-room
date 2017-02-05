package ua.softserveinc.tc.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import ua.softserveinc.tc.constants.DayOffConstants;
import ua.softserveinc.tc.constants.RoomConstants;
import ua.softserveinc.tc.util.serializers.LocalDateDeserializer;
import ua.softserveinc.tc.util.serializers.LocalDateSerializer;
import ua.softserveinc.tc.util.serializers.SimpleRoomSerializer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = DayOffConstants.DB.TABLENAME)
@EqualsAndHashCode(of = "id")
@ToString
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "name")
public class DayOff {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = DayOffConstants.DB.ID_DAY_OFF)
    private Long id;

    @Column(name = DayOffConstants.DB.NAME)
    @NonNull
    private String name;

    @Column(name = DayOffConstants.DB.START_DATE)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @NonNull
    private LocalDate startDate;

    @Column(name = DayOffConstants.DB.END_DATE)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @NonNull
    private LocalDate endDate;

    @ManyToMany
    @JoinTable(name = RoomConstants.ROOMS_OFF,
            joinColumns = @JoinColumn(name = RoomConstants.DAY_OFF),
            inverseJoinColumns = @JoinColumn(name = RoomConstants.ROOM))
    @JsonSerialize(using = SimpleRoomSerializer.class)
    private Set<Room> rooms;

    /**
     * Deletes {@link DayOff} instance and avoids
     * throwing DataIntegrityViolationException.
     */
    @PreRemove
    private void removeDaysOffFromRoom() {
        for (Room room : rooms) {
            room.getDaysOff().remove(this);
        }
    }

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }
}
