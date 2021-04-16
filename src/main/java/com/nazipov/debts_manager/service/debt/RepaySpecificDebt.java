package com.nazipov.debts_manager.service.debt;

import java.time.LocalDate;

public class RepaySpecificDebt extends RepayDebtRequest {
    private final Long debtId;

    public RepaySpecificDebt(Long debtId, String repayDescription, LocalDate repayDate) {
        super(repayDescription, repayDate);
        this.debtId = debtId;
    }

    public Long getDebtId() {
        return debtId;
    }
}
