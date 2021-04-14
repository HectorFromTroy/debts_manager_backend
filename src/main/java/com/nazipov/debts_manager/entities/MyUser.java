package com.nazipov.debts_manager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class MyUser {
    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @Column(name = "password")
    private String password;

//    @JsonIgnore
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "debtship",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "debtor_id")
//    )
//    private Set<MyUser> debtors = new HashSet<>();
//
//    @JsonIgnore
//    @ManyToMany
//    @JoinTable(
//            name = "debtship",
//            joinColumns = @JoinColumn(name = "debtor_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id")
//    )
//    private Set<MyUser> debtorOf = new HashSet<>();
//
//    public Set<MyUser> getDebtors() {
//        return debtors;
//    }
//
//    public void addDebtor(MyUser debtor) {
//        debtors.add(debtor);
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long _id) {
        id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String _username) {
        username = _username;
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String p) {
        password = p;//PASSWORD_ENCODER.encode(p);
    }
}
