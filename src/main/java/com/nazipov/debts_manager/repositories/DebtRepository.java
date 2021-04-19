package com.nazipov.debts_manager.repositories;

import com.nazipov.debts_manager.entities.Debt;
import com.nazipov.debts_manager.entities.Debtship;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DebtRepository extends CrudRepository<Debt, Long> {
    List<Debt> findAllByDebtship(Debtship debtship, Pageable pageable);
    List<Debt> findAllByDebtshipAndIsPaidOff(Debtship debtship, boolean isPaidOff, Pageable pageable);
    @Query(value = "select sum(sum) as sum from debt where debtship_id = ?1 and is_paid_off = false", nativeQuery = true)
    Optional<Integer> getDebtsSum(long debtshipId);
}