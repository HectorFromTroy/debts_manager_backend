package com.nazipov.debts_manager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.TreeSet;

@Entity
@Table(name = "debtship")
public class Debtship {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private MyUser user;

    @ManyToOne
    @JoinColumn(name = "debtor_id")
    private MyUser debtor;

    @JsonIgnore
    @OneToMany(mappedBy = "debtship")
    @OrderBy("date ASC")
    private List<Debt> debts;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public MyUser getDebtor() {
        return debtor;
    }

    public void setDebtor(MyUser debtor) {
        this.debtor = debtor;
    }

    public List<Debt> getDebts() {
        return debts;
    }

    public void addDebt(Debt debt) {
        debts.add(debt);
    }
}
