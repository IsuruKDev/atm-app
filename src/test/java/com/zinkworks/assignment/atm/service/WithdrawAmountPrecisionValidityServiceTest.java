package com.zinkworks.assignment.atm.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class WithdrawAmountPrecisionValidityServiceTest {

    @InjectMocks
    WithdrawAmountPrecisionValidityService service;

    @Test
    void isValidAmountRequested_True() {

        BigDecimal withdrawAmount = new BigDecimal("200.00");
        boolean result = service.isValidAmountRequested(withdrawAmount);

        assertTrue(result);
    }

    @Test
    void isValidAmountRequested_False() {

        BigDecimal withdrawAmount = new BigDecimal("202.00");
        boolean result = service.isValidAmountRequested(withdrawAmount);

        assertFalse(result);
    }
}