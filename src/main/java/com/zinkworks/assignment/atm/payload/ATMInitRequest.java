package com.zinkworks.assignment.atm.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ATMInitRequest {

    private int id;

    @NotBlank @Size(max = 10)
    private String code;

    @Digits(integer = 6, fraction = 2)
    private BigDecimal balance;

    private int fiveEuro;
    private int tenEuro;
    private int twentyEuro;
    private int fiftyEuro;
}
