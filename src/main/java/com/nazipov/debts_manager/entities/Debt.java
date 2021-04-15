package com.nazipov.debts_manager.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "debt")
public class Debt {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "debtship_id")
    private Debtship debtship;

    @Column(name = "sum")
    private Integer sum;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "repay_sum")
    private Integer repaySum = 0;

    @Column(name = "repay_description")
    private String repayDescription;

    @Column(name = "repay_date")
    private LocalDate repayDate;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDebtship(Debtship debtship) {
        this.debtship = debtship;
    }

    public Debtship getDebtship() {
        return debtship;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getSum() {
        return sum;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setRepaySum(int repaySum) {
        this.repaySum = repaySum;
    }

    public int getRepaySum() {
        return repaySum;
    }

    public void setRepayDescription(String repayDescription) {
        this.repayDescription = repayDescription;
    }

    public String getRepayDescription() {
        return repayDescription;
    }

    public void setRepayDate(LocalDate repayDate) {
        this.repayDate = repayDate;
    }

    public LocalDate getRepayDate() {
        return repayDate;
    }
}
