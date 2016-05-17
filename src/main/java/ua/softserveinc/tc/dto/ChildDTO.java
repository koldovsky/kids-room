package ua.softserveinc.tc.dto;

import ua.softserveinc.tc.entity.Child;

/**
 * Created by edward on 5/17/16.
 */
public class ChildDTO {

    private String firstName;
    private String lastName;

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

    public ChildDTO(Child child) {
        this.firstName = child.getFirstName();
        this.lastName = child.getLastName();
    }

}
