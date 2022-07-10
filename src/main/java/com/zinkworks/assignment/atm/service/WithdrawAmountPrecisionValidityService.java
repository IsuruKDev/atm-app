package com.zinkworks.assignment.atm.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WithdrawAmountPrecisionValidityService {

    public boolean isValidAmountRequested(BigDecimal withdrawAmount){

        BigDecimal remainder = new BigDecimal("0.00");
        if (remainder.equals(withdrawAmount.remainder(new BigDecimal("5.00"))))
            return true;

        return false;
    }
}
