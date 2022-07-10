package com.zinkworks.assignment.atm.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WithdrawResponse {

    private String message;
    private BigDecimal currentBalance;
    private String accountNumber;
    private int fiftyEuros;
    private int twentyEuros;
    private int tenEuros;
    private int fiveEuros;
}
