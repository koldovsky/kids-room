package ua.softserveinc.tc.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.softserveinc.tc.constants.DayOffConstants;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = DayOffConstants.Entity.TABLENAME)
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
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

    @ManyToMany(mappedBy = "daysOff", fetch=FetchType.LAZY)
    List<Room> rooms;

}
