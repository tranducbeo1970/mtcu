/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.attech.amhs.mtcu.database.dao;

import com.attech.amhs.mtcu.database.Connection;
import com.attech.amhs.mtcu.database.entity.GatewayInFails;
import java.util.Date;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author andh
 */
public class GatewayInDaoTest {
    
    public GatewayInDaoTest() {
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
     * Test of save method, of class GatewayInDao.
     */
    @Test
    public void testSave_GatewayInFails() throws Exception {
        Connection.configure("database.xml");
        GatewayInFails fail = new GatewayInFails();
        fail.setAddress("VVTSAMHS");
        fail.setCpa("A");
        fail.setCreatedTime(new Date());
        fail.setErrorDetail("Cannot inserted");
        fail.setPriority(1);
        fail.setSource("VVTSAMHS");
        fail.setText("THIS IS A GATEWAYING FAIL TEST");
        fail.setTime(new Date());
        GatewayInDao dao = new GatewayInDao();
        dao.save(fail);
        Assert.assertTrue(true);
    }
    
}
