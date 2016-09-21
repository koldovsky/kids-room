package ua.softserveinc.tc.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.softserveinc.tc.constants.DayOffConstants;
import ua.softserveinc.tc.constants.RoomConstants;
import ua.softserveinc.tc.util.SimpleRoomSerializer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = DayOffConstants.Entity.TABLENAME)
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "name")
public class DayOff {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = DayOffConstants.Entity.ID_DAY_OFF)
    private Long id;

    @Column(name = DayOffConstants.Entity.NAME)
    private String name;

    @Column(name = DayOffConstants.Entity.START_DATE)
    private LocalDate startDate;

    @Column(name = DayOffConstants.Entity.END_DATE)
    private LocalDate endDate;

    @ManyToMany
    @JoinTable(name = RoomConstants.ROOMS_OFF,
            joinColumns = @JoinColumn(name = RoomConstants.DAY_OFF),
            inverseJoinColumns = @JoinColumn(name = RoomConstants.ROOM))
    @JsonSerialize(using = SimpleRoomSerializer.class)
    Set<Room> rooms;

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
}
