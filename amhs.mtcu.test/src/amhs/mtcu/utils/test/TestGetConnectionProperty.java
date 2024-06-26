/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amhs.mtcu.utils.test;

import amhs.database.CommonUtils;
import amhs.database.utils.gateway.CustomDbUtils;

/**
 *
 * @author andh
 */
public class TestGetConnectionProperty {

    public static void main(String[] args) {
        CommonUtils.configure("config/database.xml");
        CustomDbUtils.getConnectionProperties();
    }
    
}
