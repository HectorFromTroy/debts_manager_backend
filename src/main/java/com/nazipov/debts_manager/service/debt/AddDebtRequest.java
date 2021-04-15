package com.nazipov.debts_manager.service.debt;

import java.time.LocalDate;

public class AddDebtRequest {
    private final long debtship_id;
    private final int sum;
    private final String description;
    private final LocalDate date;

    public AddDebtRequest(long debtship_id, int sum, String description, LocalDate date) {
        this.debtship_id = debtship_id;
        this.sum = sum;
        this.description = description;
        this.date = date;
    }


    public long getDebtship_id() {
        return debtship_id;
    }

    public int getSum() {
        return sum;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }
}
