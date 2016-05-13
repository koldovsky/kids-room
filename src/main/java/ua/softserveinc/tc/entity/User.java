package ua.softserveinc.tc.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import ua.softserveinc.tc.validator.UniqueEmail;
import ua.softserveinc.tc.constants.ColumnConstants.UserConst;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

@NamedQueries({
        @NamedQuery(name = UserConst.NQ_FIND_USER_BY_EMAIL, query = "from User WHERE email = :email")
})
@Entity
@Table(name = UserConst.TABLE_NAME_USER)
public class User {


    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = UserConst.ID_USER, nullable = false)
    private Long id;

    @NotEmpty
    @Column(name = UserConst.FIRST_NAME)
    private String firstName;

    @NotEmpty
    @Column(name = UserConst.LAST_NAME)
    private String lastName;

    @NotEmpty
    @Email
    @Column(name = UserConst.EMAIL, unique = true)
    @UniqueEmail
    private String email;

    @NotEmpty
    @Size(min = 8)
    @Column(name = UserConst.PASSWORD)
    private String password;

    @NotEmpty
    @Column(name = UserConst.ENABLED)
    private boolean enabled;

    @NotEmpty
    @Size(min = 10)
    @Column(name = UserConst.PHONE)
    private String phoneNumber;


    @Column(name = UserConst.ROLE)
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "parentId")
    @Column
    private Set<Child> children;

    public void setChildren(Set<Child> children) {
        this.children = children;
    }

    public Set<Child> getChildren() {
        return new TreeSet<>(children);
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

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public List<Child> getEnabledChildren() {
        List<Child> li = new ArrayList<>(this.getChildren());

        return li.stream()
                .filter(Child::isEnabled)
                .collect(Collectors.toList());
    }

    @Override
    public int hashCode() {
        return 13 * Objects.hashCode(email);
    }

    @Override
    public boolean equals(Object that) {
        if (that == null) return false;
        if (this == that) return true;
        if (!(that instanceof User)) return false;
        User other = (User) that;
        return email.equals(other.email);
    }
}