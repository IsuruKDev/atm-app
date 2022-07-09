package com.zinkworks.assignment.atm.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "accounts",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "account_number")
})
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "account_number", length = 9, nullable = false)
    private String accountNumber;

    @Column(length = 4, nullable = false)
    private String pin;

    @Column(precision = 10, scale = 2)
    private BigDecimal balance;

    @Column(precision = 10, scale = 2)
    private BigDecimal overdraftAmount;
}
