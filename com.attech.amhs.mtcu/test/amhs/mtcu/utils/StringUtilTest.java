/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.mtcu.utils;

import com.attech.amhs.mtcu.common.StringUtil;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author andh
 */
public class StringUtilTest {
    
    public StringUtilTest() {
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

    /**
     * Test of splitContent method, of class StringUtil.
     */
    @Test
    public void testSplitContent() {
        System.out.println("splitContent");
        String content = builder2();
        List<String> result = StringUtil.splitContent(content);
        System.out.println(result.size());
        
        for(String r : result) {
            System.out.println("length: " + r.length());
            System.out.println("content: " + r);
        }
        // assertEquals(expResult, result);
        // fail("The test case is a prototype.");
    }
    
    
    private String builder1() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0;i<15000;i++){
            builder.append("A");
        }
        return builder.toString();
    }
    
    private String builder2() {
        StringBuilder builder = new StringBuilder();
        int index = 0;
        while(builder.length() < 15000) {
            if (index > 0 && index % 67 == 0) {
                builder.append("\r\n");
                index = 0;
            } else {
                builder.append("A");
                index++;
            }
        }
        
        return builder.toString();
    }
}
