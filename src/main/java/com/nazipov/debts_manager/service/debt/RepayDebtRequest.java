package com.nazipov.debts_manager.service.debt;

import com.fasterxml.jackson.annotation.JsonSubTypes;

import java.time.LocalDate;

@JsonSubTypes(value = {
        @JsonSubTypes.Type(value = RepaySpecificDebt.class),
        @JsonSubTypes.Type(value = RepayDebtsByCommonSum.class)
})
public class RepayDebtRequest {
    private final String repayDescription;
    private final LocalDate repayDate;

    public RepayDebtRequest(String repayDescription, LocalDate repayDate) {
        this.repayDescription = repayDescription;
        this.repayDate = repayDate;
    }

    public String getRepayDescription() {
        return repayDescription;
    }

    public LocalDate getRepayDate() {
        return repayDate;
    }
}
