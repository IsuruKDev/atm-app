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
public class AccountBalanceResponse {

    private String accountNumber;
    private BigDecimal accountBalance;
    private BigDecimal maximumWithdrawalAmount;
}
