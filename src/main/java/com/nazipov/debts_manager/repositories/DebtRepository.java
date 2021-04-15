package com.nazipov.debts_manager.repositories;

import com.nazipov.debts_manager.entities.Debt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebtRepository extends CrudRepository<Debt, Long> {
}
