package com.nazipov.debts_manager.service.debt;

import java.time.LocalDate;

public class AddDebtRequest {
    private final Long[] debtorIds;
    private final int sum;
    private final String description;
    private final LocalDate date;

    public AddDebtRequest(Long[] debtorIds, int sum, String description, LocalDate date) {
        this.debtorIds = debtorIds;
        this.sum = sum;
        this.description = description;
        this.date = date;
    }


    public Long[] getDebtorIds() {
        return debtorIds;
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
