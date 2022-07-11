package com.zinkworks.assignment.atm.controller;

import com.zinkworks.assignment.atm.domain.ATM;
import com.zinkworks.assignment.atm.exception.ATMFundNotEnoughException;
import com.zinkworks.assignment.atm.exception.ATMNotFoundException;
import com.zinkworks.assignment.atm.payload.ATMInitRequest;
import com.zinkworks.assignment.atm.payload.Message;
import com.zinkworks.assignment.atm.repository.ATMRepository;
import com.zinkworks.assignment.atm.service.ATMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/atm")
public class ATMController {

    @Autowired
    ATMService atmService;

    @Autowired
    ATMRepository atmRepository;

    @PostMapping(path = "/init")
    public ResponseEntity<?> initializeATM(@Valid @RequestBody ATMInitRequest initRequest){

        ATM atm = ATM
                .builder()
                .id(initRequest.getId())
                .atmCode(initRequest.getCode())
                .balance(initRequest.getBalance())
                .fiveEuro(initRequest.getFiveEuro())
                .tenEuro(initRequest.getTenEuro())
                .twentyEuro(initRequest.getTwentyEuro())
                .fiftyEuro(initRequest.getFiftyEuro())
                .build();

        return ResponseEntity
                .ok(atmService.fillCashWithATM(atm));

    }

    @GetMapping(path = "/{code}")
    public ResponseEntity<?> getATMByCode(@PathVariable("code") String code){
        ATM atm =atmRepository.findATMByAtmCode(code);

        if (atm != null)
            return ResponseEntity.ok(atm);
        throw new ATMNotFoundException("ATM code is invalid");

    }

    @GetMapping(path = "/balance/{code}")
    public ResponseEntity<?> getBalanceOfATM(@PathVariable("code") String code) {

        ATM atm = atmRepository.findATMByAtmCode(code);

        if (atm != null) {
            return ResponseEntity.ok(atm.getBalance());
        }
        throw new ATMNotFoundException("Invalid ATM code.");

    }
}
