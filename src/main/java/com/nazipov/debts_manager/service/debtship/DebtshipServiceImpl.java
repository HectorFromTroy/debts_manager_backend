package com.nazipov.debts_manager.service.debtship;

import com.nazipov.debts_manager.entities.Debtship;
import com.nazipov.debts_manager.entities.MyUser;
import com.nazipov.debts_manager.repositories.DebtshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DebtshipServiceImpl implements DebtshipService {
    DebtshipRepository debtshipRepository;
    @Autowired
    public DebtshipServiceImpl(DebtshipRepository debtshipRepository) {
        this.debtshipRepository = debtshipRepository;
    }

    public Optional<Debtship> getDebtshipByUserAndDebtorId(long userId, long debtorId) {
        return debtshipRepository.getDebtshipByUserAndDebtorId(userId, debtorId);
    }

    public List<Debtship> getDebtshipsByDebtorIds(long userId, Long[] debtorIds) {
        return debtshipRepository.getDebtshipsByUserAndDebtorIds(userId, debtorIds);
    }

    public Debtship saveDebtship(MyUser user, MyUser debtor) {
        Debtship debtship = new Debtship();
        debtship.setUser(user);
        debtship.setDebtor(debtor);
        return debtshipRepository.save(debtship);
    }

    public void deleteDebtship(Debtship debtship) {
        debtshipRepository.delete(debtship);
    }
}
