package ua.softserveinc.tc.entity;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Demian on 29.04.2016.
 */
@Entity
public class Child
{
    @Id
    @GeneratedValue
    private int id;
    private String firstName;
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "parentId")
    private User parentId;
    private Date dateOfBirth;
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
