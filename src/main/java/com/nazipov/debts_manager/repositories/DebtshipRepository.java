package com.nazipov.debts_manager.repositories;

import com.nazipov.debts_manager.entities.Debtship;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface DebtshipRepository extends CrudRepository<Debtship, Long> {
    @Query(value = "select * from debtship where user_id = ?1", nativeQuery = true)
    public Optional<Set<Debtship>> getUserDebtorsById(long id);

    @Query(value = "select * from debtship where user_id = ?1 and debtor_id = ?2", nativeQuery = true)
    public Optional<Debtship> getDebtshipByUserAndDebtorId(long userId, long debtorId);
}
