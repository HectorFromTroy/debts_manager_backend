package com.nazipov.debts_manager.dto;

public class AddDebtorRequest {
    private final int debtorId;
    private final String name;
    public AddDebtorRequest(int debtorId, String name) {
        this.debtorId = debtorId;
        this.name = name;
    }

    public int getDebtorId() {
        return debtorId;
    }
    public String getName() {
        return name;
    }
}
