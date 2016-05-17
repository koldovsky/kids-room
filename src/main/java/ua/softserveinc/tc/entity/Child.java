package ua.softserveinc.tc.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ua.softserveinc.tc.constants.ColumnConstants.ChildConst;

/**
 * Created by Demian on 29.04.2016.
 */
@Entity
@Table(name = ChildConst.TABLE_NAME)
@Indexed
public class Child implements Comparable<Child>
{
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = ChildConst.ID_CHILD, nullable = false)
    private Long id;

    @Column(name = ChildConst.FIRST_NAME)
    @Field
    @Analyzer(definition = "ngram")
    private String firstName;

    @Column(name = ChildConst.LAST_NAME)
    @Field
    @Analyzer(definition = "ngram")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = ChildConst.ID_PARENT,
    nullable = false)
    private User parentId;

    @Temporal(value = TemporalType.DATE)
    @DateTimeFormat(pattern=ChildConst.DATE_FORMAT)
    @Column(name = ChildConst.DATE_OF_BIRTH, nullable = false)
    private Date dateOfBirth;

    @Column(name = ChildConst.COMMENT)
    private String comment;

    @Column(name = ChildConst.ENABLED,
            nullable = false,
            columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean enabled = true;

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return firstName;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public int getAge() {
        Calendar today = Calendar.getInstance();
        Calendar dob = Calendar.getInstance();
        dob.setTime(dateOfBirth);
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) <= dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }

    @Override
    public int compareTo(Child o) {
        if(this.getId()>o.getId())
            return 1;
        else if(this.getId()<o.getId())
            return -1;
        return 0;
    }
}
