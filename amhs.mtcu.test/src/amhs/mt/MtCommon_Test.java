/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.mt;

import java.text.SimpleDateFormat;
import java.util.Date;
import static junit.framework.Assert.assertEquals;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 *
 * @author andh
 */
public class MtCommon_Test {
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
    public void getAuthorizedTimeFromFilingTime() {
        
        final SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Date date = new Date();
        String filingTime = "011810";
        String year = format.format(date);
        String expected  = year + filingTime + "00Z";
        getAuthorizedTimeFromFilingTime(filingTime, date, expected);
    }
    
    private void getAuthorizedTimeFromFilingTime(String filingTime, Date date, String expected) {
        String result = MtCommon.getAuthorizedTimeFromFilingTime(filingTime);
        assertEquals(expected, result);
    }
    
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(MtCommon_Test.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
            System.out.println(failure.getMessage());
            System.out.println(failure.getDescription().toString());
            System.out.println(failure.getTrace());
        }

        System.out.println("Test result: " + result.wasSuccessful());
    }
}
