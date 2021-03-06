package com.nazipov.debts_manager.service.debt;

import com.nazipov.debts_manager.entities.Debt;
import com.nazipov.debts_manager.entities.Debtship;
import com.nazipov.debts_manager.repositories.DebtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DebtServiceImpl implements DebtService {
    private final DebtRepository debtRepository;

    @Autowired
    public DebtServiceImpl(DebtRepository debtRepository) {
        this.debtRepository = debtRepository;
    }

    public Iterable<Debt> getAllDebtsById(Long[] debtIds) {
        return debtRepository.findAllById(Arrays.asList(debtIds));
    }

    public List<Debt> findAllByDebtship(Debtship debtship, Pageable pageable) {
        return debtRepository.findAllByDebtship(debtship, pageable);
    }

    public List<Debt> findAllByDebtshipAndIsPaidOff(Debtship debtship, boolean isPaidOff, Pageable pageable) {
        return debtRepository.findAllByDebtshipAndIsPaidOff(debtship, isPaidOff, pageable);
    }

    public Optional<Integer> getDebtsSum(long debtshipId) {
        return debtRepository.getDebtsSum(debtshipId);
    }

    public Iterable<Debt> saveAllDebts(List<Debt> debts) {
        return debtRepository.saveAll(debts);
    }

    public void deleteDebts(List<Debt> debts) {
        debtRepository.deleteAll(debts);
    }
}
