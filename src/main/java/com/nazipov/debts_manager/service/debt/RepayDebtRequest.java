package com.nazipov.debts_manager.service.debt;

import java.time.LocalDate;

public class RepayDebtRequest {
    private final Long debtId;
    private final String repayDescription;
    private final LocalDate repayDate;
    private final Integer repaySum;

    public RepayDebtRequest(Long debtId, String repayDescription, LocalDate repayDate, Integer repaySum) {
        this.debtId = debtId;
        this.repayDescription = repayDescription;
        this.repayDate = repayDate;
        this.repaySum = repaySum;
    }

    public String getRepayDescription() {
        return repayDescription;
    }

    public LocalDate getRepayDate() {
        return repayDate;
    }

    public Integer getRepaySum() {
        return repaySum;
    }

    public Long getDebtId() {
        return debtId;
    }
}
