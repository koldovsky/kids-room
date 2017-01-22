package ua.softserveinc.tc.dto;

import ua.softserveinc.tc.entity.Child;

import java.util.Date;


public class ChildDto {

    private Long id;
    private String firstName;
    private String lastName;
    private UserDto parentId;
    private Date dateOfBirth;
    private long age;
    private Boolean isBirthday;
    private String comment;

    public ChildDto(Child child) {
        this.id = child.getId();
        this.firstName = child.getFirstName();
        this.lastName = child.getLastName();
        this.parentId = new UserDto(child.getParentId());
        this.dateOfBirth = child.getDateOfBirth();
        this.age = child.getAge();
        this.isBirthday = child.isBirthday();
        this.comment = child.getComment();
    }

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

    public UserDto getParentId() {
        return parentId;
    }

    public void setParentId(UserDto parentId) {
        this.parentId = parentId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public Boolean getIsBirthday() {
        return isBirthday;
    }

    public void setIsBirthday(Boolean birthday) {
        isBirthday = birthday;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }



}
