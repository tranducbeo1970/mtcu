/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool;

import amhs.aftn.testtool.highlevel.XmlSerializer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andh
 */
public class SendingTest {
    public static void main(String [] args) {
        SendingItem items = new SendingItem();
        items.setCaseNo("CT201");
        items.setDescription("HELLO");
        List<SendingDetailItem> itemssss = new ArrayList<>();
        SendingDetailItem item = new SendingDetailItem();
        item.setAccountName("AAAAAAAAAAA");
        item.setMessageName("SSSSSSSSSSS");
        item.setPath("AAAAAAAAAAAAA'");
        item.setSendingMode("M");
        itemssss.add(item);
        
        items.setSendingDetails(itemssss);
        
        XmlSerializer.serialize("D:\\Sending.xml", items);
        
        System.out.println("OK");
    }
}
