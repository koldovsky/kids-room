package ua.softserveinc.tc.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.springframework.format.annotation.DateTimeFormat;
import ua.softserveinc.tc.constants.ChildConstants;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Demian on 29.04.2016.
 */
@Entity
@Table(name = ChildConstants.TABLE_NAME)
@Indexed
@Embeddable
public class Child implements Comparable<Child>
{
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = ChildConstants.ID_CHILD, nullable = false)
    private Long id;

    @Column(name = ChildConstants.FIRST_NAME)
    @Field
    @Analyzer(definition = "ngram")
    private String firstName;

    @Column(name = ChildConstants.LAST_NAME)
    @Field
    @Analyzer(definition = "ngram")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = ChildConstants.ID_PARENT,
    nullable = false)
    @Embedded
    @IndexedEmbedded
    private User parentId;

    @Temporal(value = TemporalType.DATE)
    @DateTimeFormat(pattern= ChildConstants.DATE_FORMAT)
    @Column(name = ChildConstants.DATE_OF_BIRTH, nullable = false)
    private Date dateOfBirth;

    @Column(name = ChildConstants.COMMENT)
    private String comment;

    @Column(name = ChildConstants.ENABLED,
            nullable = false,
            columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean enabled = true;

    @Column(name = ChildConstants.GENDER)
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @Lob
    @Column(name = ChildConstants.PROFILE_IMG)
    private byte[] image;

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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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
    public int compareTo(Child that) {
        int res = this.dateOfBirth.compareTo(that.getDateOfBirth());
        if(res != 0) {
            return res;
        }

        res = this.firstName.compareTo(that.getFirstName());
        if(res != 0) {
            return res;
        }

        res = this.lastName.compareTo(that.getLastName());
        if(res != 0) {
            return res;
        }
        return this.gender.compareTo(that.getGender());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Child child = (Child) o;

        if (enabled != child.enabled) {
            return false;
        }
        if (firstName != null ? !firstName.equals(child.firstName) : child.firstName != null) {
            return false;
        }
        if (lastName != null ? !lastName.equals(child.lastName) : child.lastName != null) {
            return false;
        }
        if (parentId != null ? !parentId.equals(child.parentId) : child.parentId != null) {
            return false;
        }
        return dateOfBirth != null ? dateOfBirth.equals(child.dateOfBirth) : child.dateOfBirth == null;

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (enabled ? 1 : 0);
        return result;
    }
}
