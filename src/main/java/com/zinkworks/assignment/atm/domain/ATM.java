package com.zinkworks.assignment.atm.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "atm",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "atm_code")
        })
public class ATM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "atm_code", nullable = false)
    private String atmCode;

    @Column(precision = 6, scale = 2)
    private BigDecimal balance;

    private int fiveEuro;
    private int tenEuro;
    private int twentyEuro;
    private int fiftyEuro;

    /*public BigDecimal getCurrentBalance(){
        int sum = (fiftyEuro*50)+(twentyEuro*20)+(tenEuro*10)+(fiveEuro*5);
        return new BigDecimal(sum).setScale(2);
    }*/

}
