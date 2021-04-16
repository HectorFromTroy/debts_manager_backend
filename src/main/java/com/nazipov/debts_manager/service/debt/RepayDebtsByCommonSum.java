package com.nazipov.debts_manager.service.debt;

import java.time.LocalDate;

public class RepayDebtsByCommonSum extends RepayDebtRequest{
    private final Long debtshipId;
    private final int repaySum;

    public RepayDebtsByCommonSum(Long debtshipId, int repaySum,
                                 String repayDescription, LocalDate repayDate) {
        super(repayDescription, repayDate);
        this.debtshipId = debtshipId;
        this.repaySum = repaySum;
    }

    public Long getDebtshipId() {
        return debtshipId;
    }

    public int getRepaySum() {
        return repaySum;
    }
}
