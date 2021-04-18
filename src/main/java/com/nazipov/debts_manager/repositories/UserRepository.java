package com.nazipov.debts_manager.repositories;

import com.nazipov.debts_manager.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByUsername(String userName);
    @Query(value = "select * from users\n" +
            "where id in (select debtor_id from debtship\n" +
            "            where debtship.user_id = ?1);", nativeQuery = true)
    List<MyUser> getUserDebtorsById(long id);
}
