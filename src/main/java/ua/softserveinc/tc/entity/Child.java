package ua.softserveinc.tc.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import ua.softserveinc.tc.entity.ColumnConstants.ChildConst;
import ua.softserveinc.tc.entity.ColumnConstants.UserConst;

import javax.persistence.*;
import java.util.Date;

import static java.util.Calendar.DATE;

/**
 * Created by Demian on 29.04.2016.
 */
@Entity
@Table(name = "children")
public class Child
{
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = ChildConst.ID_CHILD, nullable = false)
    private Long id;
    private String firstName;
    private String lastName;

    @ManyToOne
    @JoinColumn(name = UserConst.ID_USER)
    private User parentId;

    @Temporal(value = TemporalType.DATE)
    @DateTimeFormat(pattern="dd.MM.yyyy")
    private Date dateOfBirth;
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User getParentId() {
        return parentId;
    }

    public void setParentId(User parentId) {
        this.parentId = parentId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}