package com.nazipov.debts_manager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "debt")
public class Debt {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "debtship_id")
    private Debtship debtship;

    @Column(name = "sum")
    private Integer sum;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "is_paid_off")
    private Boolean isPaidOff = false;

    @Column(name = "repay_description")
    private String repayDescription;

    @Column(name = "repay_date")
    private LocalDateTime repayDate;

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

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setRepayDescription(String repayDescription) {
        this.repayDescription = repayDescription;
    }

    public String getRepayDescription() {
        return repayDescription;
    }

    public void setRepayDate(LocalDateTime repayDate) {
        this.repayDate = repayDate;
    }

    public LocalDateTime getRepayDate() {
        return repayDate;
    }

    public void setPaidOff(Boolean paidOff) {
        isPaidOff = paidOff;
    }

    public Boolean getPaidOff() {
        return isPaidOff;
    }
}
