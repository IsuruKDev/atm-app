package com.zinkworks.assignment.atm.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WithdrawRequest {


    @NotBlank
    @Size(min = 4, max = 4)
    private String pin;

    @NotBlank @Size(min = 9, max = 9)
    private String accountNumber;

    @Digits(integer = 4, fraction = 2)
    private BigDecimal withdrawAmount;
}
