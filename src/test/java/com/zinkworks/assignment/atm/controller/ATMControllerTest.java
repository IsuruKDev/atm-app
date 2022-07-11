package com.zinkworks.assignment.atm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zinkworks.assignment.atm.exception.ATMNotFoundException;
import com.zinkworks.assignment.atm.payload.ATMInitRequest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ATMControllerTest {

    @Autowired
    MockMvc mockMvc;

    private ATMInitRequest initRequest;

    @BeforeEach
    void setUp(){
        initRequest = ATMInitRequest
                .builder()
                .id(4)
                .code("ATM003")
                .balance(new BigDecimal("1500.00"))
                .fiftyEuro(10)
                .twentyEuro(30)
                .tenEuro(30)
                .fiveEuro(20)
                .build();
    }

    @Test
    void initializeATMTest() throws Exception {
        mockMvc.perform(
                post("/api/atm/init")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(initRequest))
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\": 4,\n" +
                        "    \"atmCode\": \"ATM003\",\n" +
                        "    \"balance\": 1500.00,\n" +
                        "    \"fiveEuro\": 20,\n" +
                        "    \"tenEuro\": 30,\n" +
                        "    \"twentyEuro\": 30,\n" +
                        "    \"fiftyEuro\": 10\n" +
                        "}"));
    }

    @Test
    void getATMByCodeTest() throws Exception {

        String code = "ATM002";

        mockMvc.perform(get("/api/atm/{code}",code))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\": 3,\n" +
                        "    \"atmCode\": \"ATM002\",\n" +
                        "    \"balance\": 1500.00,\n" +
                        "    \"fiveEuro\": 20,\n" +
                        "    \"tenEuro\": 30,\n" +
                        "    \"twentyEuro\": 30,\n" +
                        "    \"fiftyEuro\": 10\n" +
                        "}"));

    }

    @Test
    void getATMByCodeTest_ExpectException() throws Exception {

        String code = "ATM005";

        mockMvc.perform(get("/api/atm/{code}",code))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ATMNotFoundException))
                .andExpect(result -> assertEquals("ATM code is invalid", result.getResolvedException().getMessage()));

    }

    @Test
    void getBalanceOfATMTest() throws Exception {
        String code = "ATM002";

        mockMvc.perform(get("/api/atm/balance/{code}",code))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("1500.00")));


    }

    @Test
    void getBalanceOfATMTest_ExpectException() throws Exception {
        String code = "ATM302";

        mockMvc.perform(get("/api/atm/balance/{code}",code))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ATMNotFoundException))
                .andExpect(result -> assertEquals("Invalid ATM code.", result.getResolvedException().getMessage()));

    }

    public static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}