package com.nazipov.debts_manager.repositories;

import com.nazipov.debts_manager.entities.Debtship;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DebtshipRepository extends CrudRepository<Debtship, Long> {
    @Query(value = "select * from debtship where user_id = ?1 and debtor_id = ?2", nativeQuery = true)
    Optional<Debtship> getDebtshipByUserAndDebtorId(long userId, long debtorId);

    @Query(value = "select * from debtship where user_id = ?1 and debtor_id in ?2", nativeQuery = true)
    List<Debtship> getDebtshipsByUserAndDebtorIds(long userId, Long[] debtorIds);
}