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
    private UserDTO parentId;
    private Date dateOfBirth;
    private long age;
    private String comment;

    public ChildDTO(Child child) {
        this.id = child.getId();
        this.firstName = child.getFirstName();
        this.lastName = child.getLastName();
        this.parentId = new UserDTO(child.getParentId());
        this.dateOfBirth = child.getDateOfBirth();
        this.age = child.getAge();
        this.comment = child.getComment();
    }

}
