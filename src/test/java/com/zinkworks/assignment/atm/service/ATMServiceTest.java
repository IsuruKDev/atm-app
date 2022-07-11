package com.zinkworks.assignment.atm.service;

import com.zinkworks.assignment.atm.domain.ATM;
import com.zinkworks.assignment.atm.payload.DispenseNotesDetails;
import com.zinkworks.assignment.atm.repository.ATMRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ATMServiceTest {

    @MockBean
    ATMRepository atmRepository;

    @InjectMocks
    ATMService atmService;

    private ATM atm;

    @BeforeEach
    void init(){

        atm = ATM
                .builder()
                .atmCode("ATM001")
                .balance(new BigDecimal("1500.00"))
                .fiftyEuro(10)
                .twentyEuro(30)
                .tenEuro(30)
                .fiveEuro(20)
                .build();
    }

    @Test
    void fillCashWithATMTest() {

        Mockito.when(atmRepository.save(atm)).thenReturn(atm);

        ATM result = atmService.fillCashWithATM(atm);
        assertNotNull(result);
    }

    @Test
    void isMoneyAvailableOnATM_True() {
        BigDecimal withdrawAmount = new BigDecimal("1500.00");
        boolean result = atmService.isMoneyAvailableOnATM(withdrawAmount,atm);
        assertTrue(result);
    }

    @Test
    void isMoneyAvailableOnATM_False() {
        BigDecimal withdrawAmount = new BigDecimal("1600.00");
        boolean result = atmService.isMoneyAvailableOnATM(withdrawAmount,atm);
        assertFalse(result);
    }

    @Test @Disabled
    void getATMDetailsTest() {
        String code = "ATM001";
        Mockito.when(atmRepository.findATMByAtmCode(code)).thenReturn(atm);

        ATM result = atmService.getATMDetails();
        assertNotNull(result);
    }

    @Test
    void withdrawMoneyFromATMTest() {
        BigDecimal withdrawAmount = new BigDecimal("800.00");
        Mockito.when(atmRepository.save(atm)).thenReturn(atm);

        DispenseNotesDetails notesDetails = atmService.withdrawMoneyFromATM(withdrawAmount,atm);

        assertNotNull(notesDetails);
        Assertions.assertThat(notesDetails.getFiftyEuros()).isEqualTo(16);
    }
}