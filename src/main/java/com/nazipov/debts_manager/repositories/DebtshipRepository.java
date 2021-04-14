package com.nazipov.debts_manager.repositories;

import com.nazipov.debts_manager.entities.Debtship;
import com.nazipov.debts_manager.entities.MyUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface DebtshipRepository extends CrudRepository<Debtship, Long> {
    @Query("select d from Debtship d where user_id = ?1")
    public Optional<Set<Debtship>> getUserDebtorsById(long id);
}
