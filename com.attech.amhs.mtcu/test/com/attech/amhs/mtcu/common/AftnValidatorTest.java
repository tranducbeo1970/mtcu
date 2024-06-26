/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.attech.amhs.mtcu.common;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andh
 */
public class AftnValidatorTest {
    
    public AftnValidatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void test_ValidateHeader() {
        System.out.println("Validate Header");
        String header = "ZCZC VGB3388 282359   ";
        boolean expResult = true;
        boolean result = AftnValidator.validateHeaderCharacter(header);
        assertEquals(expResult, result);
    }
    
}
