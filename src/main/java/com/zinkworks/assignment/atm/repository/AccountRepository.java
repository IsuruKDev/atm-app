package com.zinkworks.assignment.atm.repository;

import com.zinkworks.assignment.atm.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

    boolean existsAccountByAccountNumber(String accountNumber);
    Optional<Account> findAccountByAccountNumber(String accountNumber);
}
