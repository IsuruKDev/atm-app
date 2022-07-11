package com.zinkworks.assignment.atm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zinkworks.assignment.atm.payload.AccountDetailRequest;
import com.zinkworks.assignment.atm.payload.WithdrawRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    private AccountDetailRequest accountDetailRequest;
    private WithdrawRequest withdrawRequest;

    @BeforeEach
    void setUp(){

        accountDetailRequest = AccountDetailRequest
                .builder()
                .accountNumber("123456789")
                .pin("1234")
                .build();

        withdrawRequest = WithdrawRequest
                .builder()
                .accountNumber("987654321")
                .pin("4321")
                .withdrawAmount(new BigDecimal("120.00"))
                .build();
    }

    @Test
    void getAccountBalanceTest() throws Exception {

        mockMvc.perform(
                get("/api/account/balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(accountDetailRequest))
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"accountNumber\": \"123456789\",\n" +
                        "    \"accountBalance\": -200.00,\n" +
                        "    \"maximumWithdrawalAmount\": 0.00\n" +
                        "}"));
    }

    @Test
    void getAccountBalanceTest_IncorrectPin() throws Exception {

        accountDetailRequest.setPin("8521");

        mockMvc.perform(
                        get("/api/account/balance")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(accountDetailRequest))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers.containsString("Pin is incorrect.")));
    }

    @Test
    void withdrawRequestTest() throws Exception {

        mockMvc.perform(
                post("/api/account/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(withdrawRequest))
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"message\": \"Cash withdrawal is successful.\",\n" +
                        "    \"currentBalance\": 990.00,\n" +
                        "    \"accountNumber\": \"987654321\",\n" +
                        "    \"fiftyEuros\": 0,\n" +
                        "    \"twentyEuros\": 6,\n" +
                        "    \"tenEuros\": 0,\n" +
                        "    \"fiveEuros\": 0\n" +
                        "}"));

    }

    @Test
    void withdrawRequestTest_IncorrectPin() throws Exception {

        withdrawRequest.setPin("6987");
        mockMvc.perform(
                        post("/api/account/withdraw")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(withdrawRequest))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers.containsString("Pin is incorrect.")));

    }

    @Test
    void withdrawRequestTest_FundError() throws Exception {

        withdrawRequest.setPin("4321");
        withdrawRequest.setWithdrawAmount(new BigDecimal("123.00"));
        mockMvc.perform(
                        post("/api/account/withdraw")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(withdrawRequest))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers.containsString("The requested fund should be multiples of euro 5")));

    }

    public static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}