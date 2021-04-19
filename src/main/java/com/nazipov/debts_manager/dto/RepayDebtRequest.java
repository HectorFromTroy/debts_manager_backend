package com.nazipov.debts_manager.dto;

import java.time.LocalDateTime;

public class RepayDebtRequest {
    private final Long[] debtIds;
    private final String repayDescription;
    private final LocalDateTime repayDate;

    public RepayDebtRequest(Long[] debtIds, String repayDescription, LocalDateTime repayDate) {
        this.debtIds = debtIds;
        this.repayDescription = repayDescription;
        this.repayDate = repayDate;
    }

    public String getRepayDescription() {
        return repayDescription;
    }

    public LocalDateTime getRepayDate() {
        return repayDate;
    }

    public Long[] getDebtIds() {
        return debtIds;
    }
}
