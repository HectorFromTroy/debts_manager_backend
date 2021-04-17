package com.nazipov.debts_manager.service.debt;

import java.time.LocalDate;

public class RepayDebtRequest {
    private final Long[] debtIds;
    private final String repayDescription;
    private final LocalDate repayDate;

    public RepayDebtRequest(Long[] debtIds, String repayDescription, LocalDate repayDate) {
        this.debtIds = debtIds;
        this.repayDescription = repayDescription;
        this.repayDate = repayDate;
    }

    public String getRepayDescription() {
        return repayDescription;
    }

    public LocalDate getRepayDate() {
        return repayDate;
    }

    public Long[] getDebtIds() {
        return debtIds;
    }
}
