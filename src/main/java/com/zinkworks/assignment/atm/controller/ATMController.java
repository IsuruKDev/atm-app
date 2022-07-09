package com.zinkworks.assignment.atm.controller;

import com.zinkworks.assignment.atm.domain.ATM;
import com.zinkworks.assignment.atm.payload.ATMInitRequest;
import com.zinkworks.assignment.atm.repository.ATMRepository;
import com.zinkworks.assignment.atm.service.ATMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/atm")
public class ATMController {

    @Autowired
    ATMService atmService;

    @Autowired
    ATMRepository atmRepository;

    @PutMapping(path = "/init")
    public ResponseEntity<?> initializeATM(@Valid ATMInitRequest initRequest){

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
        Optional<ATM> atm = null;

        if (code != null && !code.isEmpty()){
            atm = atmRepository.findATMByAtmCode(code);
            if(atm.isPresent()) {
                return ResponseEntity.ok(atm);
            }else {
                return ResponseEntity
                        .notFound()
                        .build();
            }
        }else {
            return ResponseEntity
                    .badRequest()
                    .body("code cannot be empty.");
        }
    }

    @GetMapping(path = "/balance/{code}")
    public ResponseEntity<?> getBalanceOfATM(@PathVariable("code") String code){

        if (code != null && !code.isEmpty()){
            BigDecimal atmBalance = atmRepository.findBalanceByAtmCode(code);
            return ResponseEntity
                    .ok(atmBalance);
        }

        return ResponseEntity
                .badRequest()
                .body("Invalid ATM code..");
    }

}
