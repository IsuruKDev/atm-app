package com.zinkworks.assignment.atm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/access")
public class MainController {

    @GetMapping(path = "/all")
    public String allAccess(){
        return "Public Content";
    }
}
