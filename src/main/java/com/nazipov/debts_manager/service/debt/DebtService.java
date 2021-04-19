package com.nazipov.debts_manager.service.debt;

import com.nazipov.debts_manager.entities.Debt;
import com.nazipov.debts_manager.entities.Debtship;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DebtService {
    Iterable<Debt> getAllDebtsById(Long[] debtIds);
    List<Debt> findAllByDebtship(Debtship debtship, Pageable pageable);
    List<Debt> findAllByDebtshipAndIsPaidOff(Debtship debtship, boolean isPaidOff, Pageable pageable);
    Optional<Integer> getDebtsSum(long debtshipId);
    Iterable<Debt> saveAllDebts(List<Debt> debts);
    void deleteDebts(List<Debt> debts);
}
