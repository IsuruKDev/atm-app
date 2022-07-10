package com.zinkworks.assignment.atm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ATMNotFoundException extends RuntimeException{

    public ATMNotFoundException(String message){
        super(message);
    }
}
