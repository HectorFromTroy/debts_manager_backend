package com.nazipov.debts_manager.dto;

import com.nazipov.debts_manager.entities.Debt;

import java.util.List;

public class DebtsAndSumDto {
    private final List<Debt> debts;
    private final int sum;

    public DebtsAndSumDto(List<Debt> debts, int sum) {
        this.debts = debts;
        this.sum = sum;
    }

    public List<Debt> getDebts() {
        return debts;
    }

    public int getSum() {
        return sum;
    }
}
