package com.zinkworks.assignment.atm.controller;

import com.zinkworks.assignment.atm.domain.Account;
import com.zinkworks.assignment.atm.payload.AccountDetailRequest;
import com.zinkworks.assignment.atm.payload.WithDrawResponse;
import com.zinkworks.assignment.atm.payload.WithdrawRequest;
import com.zinkworks.assignment.atm.repository.AccountRepository;
import com.zinkworks.assignment.atm.service.AccountService;
import com.zinkworks.assignment.atm.service.WithdrawAmountPrecisionValidityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    WithdrawAmountPrecisionValidityService withdrawAmountPrecisionValidityService;

    @GetMapping("/balance")
    public ResponseEntity<?> getAccountBalance(@Valid AccountDetailRequest detailRequest){

        Optional<Account> optionalAccount = accountRepository
                .findAccountByAccountNumber(detailRequest.getAccountNumber());

        if (optionalAccount.isPresent() && optionalAccount.get()
                .getPin().equals(detailRequest.getPin())){

            return ResponseEntity
                    .ok(optionalAccount.get().getBalance());
        }

        return ResponseEntity
                .badRequest()
                .body("Pin is incorrect.");

    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdrawRequest(@Valid WithdrawRequest withdrawRequest){

        Optional<Account> optionalAccount = accountRepository
                .findAccountByAccountNumber(withdrawRequest.getAccountNumber());

        if (optionalAccount.isPresent() && !optionalAccount.get()
                .getPin().equals(withdrawRequest.getPin())){

            return ResponseEntity
                    .badRequest()
                    .body("Pin is incorrect.");
        }else {

            if (withdrawAmountPrecisionValidityService
                    .isValidAmountRequested(withdrawRequest.getWithdrawAmount())){
                Account account = accountService.withdrawMoney(optionalAccount.get(),withdrawRequest.getWithdrawAmount());

                return ResponseEntity.ok(WithDrawResponse
                        .builder()
                        .message("Cash withdrawal is successfull..")
                        .currentBalance(account.getBalance())
                        .build());

            }else {
                return ResponseEntity
                        .badRequest()
                        .body("The requested fund should be multiples of euro 5");
            }


        }

    }




}
