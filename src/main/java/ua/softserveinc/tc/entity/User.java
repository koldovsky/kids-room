package ua.softserveinc.tc.entity;

import org.hibernate.annotations.GenericGenerator;
import ua.softserveinc.tc.entity.ColumnConstants.UserConst;

import javax.persistence.*;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name = User.NQ_FIND_USER_BY_EMAIL, query = "from User WHERE email = :email")
})
@Entity
@Table(name = UserConst.TABLE_NAME_USER)
public class User {
    public static final String NQ_FIND_USER_BY_EMAIL = "findUserByEmail";
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name=UserConst.ID_USER, nullable = false)
    private Long id;

    @Column(name=UserConst.FIRST_NAME)
    private String firstName;

    @Column(name=UserConst.LAST_NAME)
    private String lastName;

    @Column(name=UserConst.EMAIL)
    private String email;

    @Column(name=UserConst.PASSWORD)
    private String password;

    @Column(name=UserConst.PHONE)
    private String phoneNumber;

    @Column(name = UserConst.ROLE)
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL, mappedBy = "parentId")
    @Column
    private Set<Child> children;

    public Set<Child> getChildren() {
        return children;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
