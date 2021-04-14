package com.nazipov.debts_manager.entities;

import javax.persistence.*;

@Entity
@Table(name = "debtship")
public class Debtship {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "user_id")
//    private Long user_id;
//    @OneToOne
//    @JoinColumn(name = "id", foreignKey = @ForeignKey(name = "user_id"))
    @ManyToOne
    @JoinColumn(name = "user_id")
    private MyUser user;

//    @Column(name = "debtor_id")
//    private Long debtor_id;
//    @OneToOne
//    @JoinColumn(name = "id", foreignKey = @ForeignKey(name = "debtor_id"))
    @ManyToOne
    @JoinColumn(name = "debtor_id")
    private MyUser debtor;

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
}
