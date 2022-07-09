package com.zinkworks.assignment.atm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AccountFundNotEnoughException extends RuntimeException{

    public AccountFundNotEnoughException(String message){
        super(message);
    }
}
