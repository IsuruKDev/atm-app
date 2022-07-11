package com.zinkworks.assignment.atm.service;

import com.zinkworks.assignment.atm.domain.ATM;
import com.zinkworks.assignment.atm.domain.Account;
import com.zinkworks.assignment.atm.payload.DispenseNotesDetails;
import com.zinkworks.assignment.atm.payload.WithdrawResponse;
import com.zinkworks.assignment.atm.repository.ATMRepository;
import com.zinkworks.assignment.atm.repository.AccountRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
//@TestPropertySource(properties = { "app.atm.atmcode=ATM001" })
class AccountServiceTest {

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    ATMRepository atmRepository;

    @Spy
    @InjectMocks
    AccountService accountService;

    @InjectMocks
    AccountService accountService2;

    @Spy
    @InjectMocks
    ATMService atmService;

    private ATM atm;
    private Account account;

    private DispenseNotesDetails notesDetails;

    @BeforeEach
    void init(){

        ReflectionTestUtils.setField(atmService, "atmCode", "ATM001");
        atm = ATM
                .builder()
                .atmCode("ATM001")
                .balance(new BigDecimal("1500.00"))
                .fiftyEuro(10)
                .twentyEuro(30)
                .tenEuro(30)
                .fiveEuro(20)
                .build();

        account = Account
                .builder()
                .accountNumber("123456789")
                .id(1)
                .balance(new BigDecimal("800.00"))
                .overdraftAmount(new BigDecimal("200.00"))
                .pin("1234")
                .build();

        notesDetails = DispenseNotesDetails
                .builder()
                .fiftyEuros(4)
                .tenEuros(0)
                .fiveEuros(0)
                .twentyEuros(0)
                .build();
    }

    @Test @Disabled
    void withdrawMoneyTest() {

        BigDecimal withdrawAmount = new BigDecimal("200.00");
        BigDecimal balance = new BigDecimal("800.00");
        BigDecimal overdraft = new BigDecimal("200.00");

        Mockito.when(accountService.
                isMoneyEnoughToWithdrawFromTheAccount(balance,withdrawAmount,overdraft))
                .thenReturn(true);

        ATM atmAfter = ATM
                .builder()
                .atmCode("ATM001")
                .balance(new BigDecimal("1300.00"))
                .fiftyEuro(6)
                .twentyEuro(30)
                .tenEuro(30)
                .fiveEuro(20)
                .build();



        Mockito.when(atmService
                .withdrawMoneyFromATM(withdrawAmount, atm))
                .thenReturn(notesDetails);

        Mockito.when(atmService.getATMDetails())
                .thenReturn(atm);

        Account accountAfter = Account
                .builder()
                .accountNumber("123456789")
                .id(1)
                .balance(new BigDecimal("800.00"))
                .overdraftAmount(new BigDecimal("200.00"))
                .pin("1234")
                .build();
        Mockito.when(accountRepository.save(account))
                .thenReturn(accountAfter);

        Mockito.when(atmRepository.save(atm))
                .thenReturn(atmAfter);

        WithdrawResponse withdrawResponse = accountService2.withdrawMoney(account,withdrawAmount);

        assertNotNull(withdrawResponse);
        Assertions.assertThat(withdrawResponse.getFiftyEuros()).isEqualTo(4);



    }

    @Test
    void isMoneyEnoughToWithdrawFromTheAccount_True() {

        BigDecimal currentBalance=new BigDecimal("800.00");
        BigDecimal withdrawAmount=new BigDecimal("550.00");
        BigDecimal overdraft=new BigDecimal("200.00");

        boolean resut = accountService.isMoneyEnoughToWithdrawFromTheAccount(currentBalance,withdrawAmount,overdraft);

        assertTrue(resut);
    }

    @Test
    void isMoneyEnoughToWithdrawFromTheAccount_False() {

        BigDecimal currentBalance=new BigDecimal("800.00");
        BigDecimal withdrawAmount=new BigDecimal("1200.00");
        BigDecimal overdraft=new BigDecimal("200.00");

        boolean resut = accountService.isMoneyEnoughToWithdrawFromTheAccount(currentBalance,withdrawAmount,overdraft);

        assertFalse(resut);
    }
}