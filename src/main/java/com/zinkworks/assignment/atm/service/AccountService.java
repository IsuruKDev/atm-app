package com.zinkworks.assignment.atm.service;

import com.zinkworks.assignment.atm.domain.ATM;
import com.zinkworks.assignment.atm.domain.Account;
import com.zinkworks.assignment.atm.exception.ATMFundNotEnoughException;
import com.zinkworks.assignment.atm.exception.AccountFundNotEnoughException;
import com.zinkworks.assignment.atm.payload.DispenseNotesDetails;
import com.zinkworks.assignment.atm.payload.WithdrawResponse;
import com.zinkworks.assignment.atm.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ATMService atmService;

    @Transactional
    public WithdrawResponse withdrawMoney(Account account, BigDecimal withdrawAmount){
        BigDecimal currentBalance = account.getBalance();
        ATM currentATM = atmService.getATMDetails();

        if (isMoneyEnoughToWithdrawFromTheAccount(currentBalance, withdrawAmount, account.getOverdraftAmount())){

            if (atmService.isMoneyAvailableOnATM(withdrawAmount, currentATM)){
                BigDecimal newAcctBalance = currentBalance.subtract(withdrawAmount);
                account.setBalance(newAcctBalance);
                DispenseNotesDetails notesDetails = atmService
                        .withdrawMoneyFromATM(withdrawAmount, currentATM);

                account = accountRepository.save(account);


                return WithdrawResponse
                        .builder()
                        .accountNumber(account.getAccountNumber())
                        .currentBalance(account.getBalance())
                        .message("Cash withdrawal is successful.")
                        .fiftyEuros(notesDetails.getFiftyEuros())
                        .twentyEuros(notesDetails.getTwentyEuros())
                        .tenEuros(notesDetails.getTenEuros())
                        .fiveEuros(notesDetails.getFiveEuros())
                        .build();
            }else {
                throw new ATMFundNotEnoughException("Funds are not enough at the ATM");
            }
        }else {
            throw new AccountFundNotEnoughException("Funds are not enogh at your account");
        }
    }

    public boolean isMoneyEnoughToWithdrawFromTheAccount(BigDecimal currentBalance,
                                                         BigDecimal withdrawAmount,
                                                         BigDecimal overdraft){
        BigDecimal total = currentBalance.add(overdraft).setScale(2);
        if (total.doubleValue()>=withdrawAmount.doubleValue())
            return true;
        return false;
    }
}
