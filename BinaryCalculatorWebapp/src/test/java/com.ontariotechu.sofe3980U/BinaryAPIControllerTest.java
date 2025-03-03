package com.ontariotechu.sofe3980U;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BinaryAPIController.class)
public class BinaryAPIControllerTest {

    @Autowired
    private MockMvc mvc;

    // Tests for addition
    @Test
    public void add() throws Exception {
        this.mvc.perform(get("/add").param("operand1", "111").param("operand2", "1010"))
                .andExpect(status().isOk())
                .andExpect(content().string("10001"));
    }

    @Test
    public void add2() throws Exception {
        this.mvc.perform(get("/add_json").param("operand1", "111").param("operand2", "1010"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(111))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(1010))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(10001))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("add"));
    }

    @Test
    public void addEmptyStrings() throws Exception {
        this.mvc.perform(get("/add").param("operand1", "").param("operand2", ""))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    public void addInvalidInput() throws Exception {
        this.mvc.perform(get("/add").param("operand1", "abcd").param("operand2", "abcd"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    public void addLeadingZeros() throws Exception {
        this.mvc.perform(get("/add").param("operand1", "000111").param("operand2", "000010"))
                .andExpect(status().isOk())
                .andExpect(content().string("1001"));
    }

    // Tests for OR operation
    @Test
    public void orOperation() throws Exception {
        this.mvc.perform(get("/or").param("operand1", "1100").param("operand2", "1010"))
                .andExpect(status().isOk())
                .andExpect(content().string("1110")); // Result of OR: 1100 | 1010 = 1110
    }

    @Test
    public void orEmptyOperands() throws Exception {
        this.mvc.perform(get("/or").param("operand1", "").param("operand2", ""))
                .andExpect(status().isOk())
                .andExpect(content().string("0")); // OR operation with empty operands defaults to 0
    }

    // Tests for AND operation
    @Test
    public void andOperation() throws Exception {
        this.mvc.perform(get("/and").param("operand1", "1100").param("operand2", "1010"))
                .andExpect(status().isOk())
                .andExpect(content().string("1000")); // Result of AND: 1100 & 1010 = 1000
    }

    @Test
    public void andLeadingZeros() throws Exception {
        this.mvc.perform(get("/and").param("operand1", "0001100").param("operand2", "0001010"))
                .andExpect(status().isOk())
                .andExpect(content().string("1000")); // Leading zeros should be ignored
    }

    @Test
    public void andEmptyOperands() throws Exception {
        this.mvc.perform(get("/and").param("operand1", "").param("operand2", ""))
                .andExpect(status().isOk())
                .andExpect(content().string("0")); // AND operation with empty operands defaults to 0
    }

    // Tests for multiplication
    @Test
    public void multiplyOperation() throws Exception {
        this.mvc.perform(get("/multiply").param("operand1", "110").param("operand2", "101"))
                .andExpect(status().isOk())
                .andExpect(content().string("11110")); // Result of 110 * 101 = 11110
    }



    @Test
    public void multiplyEmpty() throws Exception {
        this.mvc.perform(get("/multiply").param("operand1", "").param("operand2", ""))
                .andExpect(status().isOk())
                .andExpect(content().string("0")); // Multiplication with empty operands defaults to 0
    }
}