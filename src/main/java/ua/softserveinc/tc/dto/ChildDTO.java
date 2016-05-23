package ua.softserveinc.tc.dto;

import java.util.Date;

import ua.softserveinc.tc.entity.Child;

/**
 * Created by edward on 5/17/16.
 */
public class ChildDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String comment;
    private long parentId;

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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getComment() {
        return comment;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public ChildDTO(Child child) {
        this.id = child.getId();
        this.firstName = child.getFirstName();
        this.lastName = child.getLastName();
        this.dateOfBirth = child.getDateOfBirth();
        this.comment = child.getComment();
        this.parentId = child.getParentId().getId();
    }

}
