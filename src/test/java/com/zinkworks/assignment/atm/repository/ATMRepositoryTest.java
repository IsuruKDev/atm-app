package com.zinkworks.assignment.atm.repository;

import com.zinkworks.assignment.atm.domain.ATM;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ATMRepositoryTest {

    @Autowired
    ATMRepository atmRepository;

    @Test
    void findATMByAtmCodeTest() {
        String code = "ATM001";
        ATM atm= atmRepository.findATMByAtmCode(code);

        assertNotNull(atm);
    }

    @Test
    void notFoundATMByAtmCodeTest() {
        String code = "ATM301";
        ATM atm = atmRepository.findATMByAtmCode(code);

        assertNull(atm);
    }
}