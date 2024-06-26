/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amhs.mtcu.utils;

import java.util.ArrayList;
import java.util.List;
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
public class StringUtil_Test {

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
    public void joinStr() {
        joinStr_01();
        joinStr_02();
    }

    private void joinStr_01() {
        String expected = "AAAAAAAA AAAAAAAB AAAAAAAC AAAAAAAD";
        List<String> input = new ArrayList<>();
        input.add("AAAAAAAA");
        input.add("AAAAAAAB");
        input.add("AAAAAAAC");
        input.add("AAAAAAAD");

        String actual = StringUtil.joinStr(input, 7);
        assertEquals(expected, actual);
    }

    private void joinStr_02() {
        String expected = "AAAAAAAA AAAAAAAB AAAAAAAC AAAAAAAD AAAAAAAE AAAAAAAF AAAAAAAG\n";
        expected += "AAAAAAAH AAAAAAAI AAAAAAAJ AAAAAAAK AAAAAAAL AAAAAAAM AAAAAAAN\n";
        expected += "AAAAAAAO AAAAAAAP AAAAAAAQ";
        List<String> input = new ArrayList<>();
        input.add("AAAAAAAA");
        input.add("AAAAAAAB");
        input.add("AAAAAAAC");
        input.add("AAAAAAAD");
        input.add("AAAAAAAE");
        input.add("AAAAAAAF");
        input.add("AAAAAAAG");
        input.add("AAAAAAAH");
        input.add("AAAAAAAI");
        input.add("AAAAAAAJ");
        input.add("AAAAAAAK");
        input.add("AAAAAAAL");
        input.add("AAAAAAAM");
        input.add("AAAAAAAN");
        input.add("AAAAAAAO");
        input.add("AAAAAAAP");
        input.add("AAAAAAAQ");

        String actual = StringUtil.joinStr(input, 7);
        assertEquals(expected, actual);
    }

    public static void main(String[] args) {

        /*
         Result result = JUnitCore.runClasses(StringUtil_Test.class);
         for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
         }
         */
        // junit.textui.TestRunner.run(new TestSuite(StringUtil_Test.class));
        Result result = JUnitCore.runClasses(StringUtil_Test.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
            System.out.println(failure.getMessage());
            System.out.println(failure.getDescription().toString());
            System.out.println(failure.getTrace());
        }

        System.out.println("Test result: " + result.wasSuccessful());
    }
}
