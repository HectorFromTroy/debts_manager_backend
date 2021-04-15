package com.nazipov.debts_manager.repositories;

import com.nazipov.debts_manager.entities.Debt;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DebtRepository extends CrudRepository<Debt, Long> {
    @Transactional
    @Modifying
    @Query(value = "delete from debt\n" +
            "where debt.id = ?2\n" +
            "and (select debtship.id from debtship\n" +
            "    where debtship.id = debt.debtship_id\n" +
            "    and (user_id = ?1\n" +
            "    or (debtor_id = ?1\n" +
            "    and (select id from users\n" +
            "        where id = debtship.user_id\n" +
            "        and username is null) notnull))) notnull ;", nativeQuery = true)
    public void deleteIfAllow(long userId, long debtId);

//    @Query(value = "update debt\n" +
//            "set repay_sum = 400, repay_date = '2021-04-15', repay_description = 'vernul epte'\n" +
//            "where debt.id = 400 and (select debtship.id from debtship\n" +
//            "              where debtship.id = debt.debtship_id\n" +
//            "                and (user_id = 100\n" +
//            "                  or (debtor_id = 100\n" +
//            "                      and (select id from users\n" +
//            "                           where id = debtship.user_id\n" +
//            "                             and username is null) notnull))) notnull ;", nativeQuery = true)
//    public void repayDebtIfAllow();
}
