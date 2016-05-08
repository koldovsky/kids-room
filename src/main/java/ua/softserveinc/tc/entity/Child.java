package ua.softserveinc.tc.entity;

import org.springframework.format.annotation.DateTimeFormat;
import ua.softserveinc.tc.entity.ColumnConstants.ChildConst;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Demian on 29.04.2016.
 */
@Entity
@Table(name = ChildConst.TABLE_NAME)
public class Child
{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = ChildConst.ID, nullable = false)
    private Long id;

    @Column(name = ChildConst.FIRST_NAME, nullable = false)
    private String firstName;

    @Column(name = ChildConst.LAST_NAME, nullable = false)
    private String lastName;

    @ManyToOne
    @JoinColumn(name = ChildConst.PARENT_ID)
    private User parentId;

    @Temporal(value = TemporalType.DATE)
    @DateTimeFormat(pattern="dd.MM.yyyy")
    @Column(name = ChildConst.DATE_OF_BIRTH, nullable = false)
    private Date dateOfBirth;

    @Column(name = ChildConst.COMMENT, nullable = false)
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

    @Override
    public String toString()
    {
        return firstName;
    }
}
