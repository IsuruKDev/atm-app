package com.zinkworks.assignment.atm.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DispenseNotesDetails {

    private int fiftyEuros;
    private int twentyEuros;
    private int tenEuros;
    private int fiveEuros;
}
