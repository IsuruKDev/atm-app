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
public class WithDrawResponse {

    private String message;
    private BigDecimal currentBalance;
}
