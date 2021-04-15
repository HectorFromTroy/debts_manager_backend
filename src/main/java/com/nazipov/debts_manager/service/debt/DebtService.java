package com.nazipov.debts_manager.service.debt;

import com.nazipov.debts_manager.dto.SampleResponseDto;
import com.nazipov.debts_manager.entities.Debt;
import com.nazipov.debts_manager.repositories.DebtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DebtService {
    private final DebtRepository debtRepository;


    @Autowired
    public DebtService(DebtRepository debtRepository) {
        this.debtRepository = debtRepository;
    }

    public Optional<Debt> getDebtById(long debtId) {
        return debtRepository.findById(debtId);
    }

    public Debt saveDebt(Debt debt) {
        return debtRepository.save(debt);
    }

    public void deleteDebt(Debt debt) {
        debtRepository.delete(debt);
    }
}
