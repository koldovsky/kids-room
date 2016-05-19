package ua.softserveinc.tc.entity;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ua.softserveinc.tc.constants.ColumnConstants.UserConst;

@NamedQueries({
        @NamedQuery(name = UserConst.NQ_FIND_USER_BY_EMAIL, query = "from User WHERE email = :email")
})
@Entity
@Table(name = UserConst.TABLE_NAME_USER)
@Indexed
@AnalyzerDef(name = "ngram",
        tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
        filters = {
            @TokenFilterDef(factory = LowerCaseFilterFactory.class),
            @TokenFilterDef(factory = NGramFilterFactory.class,
                params = {
                    @Parameter(name = "minGramSize",value = "3"),
                    @Parameter(name = "maxGramSize",value = "1024")
                })
        })
public class User{
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = UserConst.ID_USER, nullable = false)
    private Long id;

    @NotEmpty
    @Column(name = UserConst.FIRST_NAME)
    @Field
    @Analyzer(definition = "ngram")
    private String firstName;

    @NotEmpty
    @Column(name = UserConst.LAST_NAME)
    @Field
    @Analyzer(definition = "ngram")
    private String lastName;

    @NotEmpty
    @Email
    @Column(name = UserConst.EMAIL, unique = true)
    @Field(store = Store.NO)
    @Analyzer(definition = "ngram")
    private String email;

    @NotEmpty
    @Column(name = UserConst.PASSWORD)
    private String password;

    @Column(name = UserConst.ENABLED)
    private boolean enabled;

    @NotEmpty
    @Column(name = UserConst.PHONE)
    @Field(store = Store.NO)
    @Analyzer(definition = "ngram")
    private String phoneNumber;

    @Column(name = UserConst.ROLE)
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "parentId")
    private Set<Child> children;

    public void setChildren(Set<Child> children) {
        this.children = children;
    }

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
        return firstName + " " + lastName;
    }

    public List<Child> getEnabledChildren() {
        List<Child> li = new ArrayList<>(children);
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
