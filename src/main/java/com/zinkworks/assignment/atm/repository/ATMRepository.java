package com.zinkworks.assignment.atm.repository;

import com.zinkworks.assignment.atm.domain.ATM;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface ATMRepository extends JpaRepository<ATM, Integer> {

    ATM findATMByAtmCode(String atmCode);
}
