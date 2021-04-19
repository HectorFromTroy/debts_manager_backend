package com.nazipov.debts_manager.dto;

import java.time.LocalDateTime;

public class AddDebtRequest {
    private final Long[] debtorIds;
    private final int sum;
    private final String description;
    private final LocalDateTime date;

    public AddDebtRequest(Long[] debtorIds, int sum, String description, LocalDateTime date) {
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

    public LocalDateTime getDate() {
        return date;
    }
}
