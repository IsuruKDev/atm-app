package com.zinkworks.assignment.atm.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDetailRequest {

    @NotBlank @Size(min = 4, max = 4)
    private String pin;

    @NotBlank @Size(min = 9, max = 9)
    private String accountNumber;
}
