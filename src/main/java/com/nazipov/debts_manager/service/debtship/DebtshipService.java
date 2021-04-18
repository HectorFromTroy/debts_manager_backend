package com.nazipov.debts_manager.service.debtship;

import com.nazipov.debts_manager.entities.Debtship;
import com.nazipov.debts_manager.entities.MyUser;
import com.nazipov.debts_manager.repositories.DebtshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DebtshipService {
    DebtshipRepository debtshipRepository;
    @Autowired
    public DebtshipService(DebtshipRepository debtshipRepository) {
        this.debtshipRepository = debtshipRepository;
    }

    public Optional<Debtship> getDebtshipByUserAndDebtorId(long userId, long debtorId) {
        return debtshipRepository.getDebtshipByUserAndDebtorId(userId, debtorId);
    }

    public List<Debtship> getDebtshipsByDebtorIds(long userId, Long[] debtorIds) {
        return debtshipRepository.getDebtshipsByUserAndDebtorIds(userId, debtorIds);
    }

    //TODO
//    public Optional<Debtship> getDebtshipById(long debtshipId) {
//        return debtshipRepository.findById(debtshipId);
//    }
//
//    public Iterable<Debtship> getAllDebtshipById(Long[] debtshipIds) {
//        return debtshipRepository.findAllById(Arrays.asList(debtshipIds));
//    }

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
