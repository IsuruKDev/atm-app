package com.zinkworks.assignment.atm.repository;

import com.zinkworks.assignment.atm.domain.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    void existsAccountByAccountNumberTest(){

        String accountNumber = "123456789";
        boolean result = accountRepository.existsAccountByAccountNumber(accountNumber);

        assertTrue(result);
    }

    @Test
    void notExistsAccountByAccountNumberTest(){

        String accountNumber = "123956789";
        boolean result = accountRepository.existsAccountByAccountNumber(accountNumber);

        assertFalse(result);
    }

    @Test
    void findAccountByAccountNumberTest(){
        String accountNumber = "123456789";
        Optional<Account> account = accountRepository.findAccountByAccountNumber(accountNumber);

        assertTrue(account.isPresent());
    }

    @Test
    void notFoundAccountByAccountNumberTest(){
        String accountNumber = "123456589";
        Optional<Account> account = accountRepository.findAccountByAccountNumber(accountNumber);

        assertFalse(account.isPresent());

    }

}