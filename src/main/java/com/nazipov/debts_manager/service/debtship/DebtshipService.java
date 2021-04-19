package com.nazipov.debts_manager.service.debtship;

import com.nazipov.debts_manager.entities.Debtship;
import com.nazipov.debts_manager.entities.MyUser;

import java.util.List;
import java.util.Optional;

public interface DebtshipService {
    Optional<Debtship> getDebtshipByUserAndDebtorId(long userId, long debtorId);
    List<Debtship> getDebtshipsByDebtorIds(long userId, Long[] debtorIds);
    Debtship saveDebtship(MyUser user, MyUser debtor);
    void deleteDebtship(Debtship debtship);
}
