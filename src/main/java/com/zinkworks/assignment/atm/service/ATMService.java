package com.zinkworks.assignment.atm.service;

import com.zinkworks.assignment.atm.domain.ATM;
import com.zinkworks.assignment.atm.domain.Account;
import com.zinkworks.assignment.atm.exception.ATMNotFoundException;
import com.zinkworks.assignment.atm.payload.DispenseNotesDetails;
import com.zinkworks.assignment.atm.repository.ATMRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ATMService {

    @Autowired
    ATMRepository atmRepository;

    @Value("${app.atm.atmcode}")
    String atmCode;

    public ATM fillCashWithATM(ATM atm){
        return atmRepository.save(atm);
    }

    public boolean isMoneyAvailableOnATM(BigDecimal withdrawAmount, ATM atm){
        if (atm.getBalance().doubleValue()>=withdrawAmount.doubleValue())
            return true;

        return false;
    }

    public ATM getATMDetails(){
        return atmRepository.findATMByAtmCode(atmCode);
    }

    public DispenseNotesDetails withdrawMoneyFromATM(BigDecimal withdrawAmount, ATM atm){

        double amount = withdrawAmount.doubleValue();
        DispenseNotesDetails notesDetails = new DispenseNotesDetails();

        if (atm.getFiftyEuro()>1 && amount>50){
            int dispenseFifties = (int)amount/50;
            atm.setFiftyEuro(atm.getFiftyEuro()-dispenseFifties);
            notesDetails.setFiftyEuros(dispenseFifties);
            amount = amount-(dispenseFifties*50);

        }
        if (atm.getTwentyEuro()>1 && amount>20){
            int dispenseTwenties = (int)amount/20;
            atm.setTwentyEuro(atm.getTwentyEuro()-dispenseTwenties);
            notesDetails.setTwentyEuros(dispenseTwenties);
            amount = amount-(dispenseTwenties*20);
        }
        if (atm.getTenEuro()>1 && amount>10){
            int dispenseTens = (int)amount/10;
            atm.setTenEuro(atm.getTenEuro()-dispenseTens);
            notesDetails.setTenEuros(dispenseTens);
            amount = amount-(dispenseTens*10);
        }
        if (atm.getFiveEuro()>1 && amount>5){
            int dispenseFives = (int)amount/5;
            atm.setFiveEuro(atm.getFiveEuro()-dispenseFives);
            notesDetails.setFiveEuros(dispenseFives);
            amount = amount-(dispenseFives*5);
        }


        BigDecimal newBalance = atm.getBalance().subtract(withdrawAmount);
        atm.setBalance(newBalance);
        atmRepository.save(atm);
        return notesDetails;
    }

}
