/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import amhs.aftn.testtool.highlevel.XmlSerializer;

/**
 *
 * @author andh
 */
public class AccountTesting {
    public static void main(String [] args) {
        Account account = new Account();
        account.setName("IUTAMHAA");
        account.setOr("/CN=IUTAMHAA/OU=IUTA/O=IUT-REGION/PRMD=IUTLAND/ADMD=ICAO/C=XX/");
        account.setPassword("attech");
        account.setPresentationAddress("\"593\"/URI+0000+URL+itot://laptop");
        
        XmlSerializer.serialize("D:\\IUTAMHAA.account.xml", account);
        System.out.println("Saved");
    }
}
