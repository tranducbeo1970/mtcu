/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import amhs.aftn.testtool.highlevel.XmlSerializer;
import amhs.database.CommonUtils;
import com.isode.x400.highlevel.X400APIException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author andh
 */
public class CT602 {
    
    private static final SimpleDateFormat date = new SimpleDateFormat("hhMMssSSS");
    
    public static void main(String[] args) throws X400APIException {
        CommonUtils.configure("config/database.xml");
        
        List<String> accountList = new ArrayList<>();
        accountList.add("message/accounts/AAAAMHAA.account.xml");
        accountList.add("message/accounts/ABAAMHAC.account.xml");
        accountList.add("message/accounts/ABBAMHAA.account.xml");
        accountList.add("message/accounts/ABCAMHAA.account.xml");
        accountList.add("message/accounts/ACAAFTAA.account.xml");

        clean(accountList);
                
        Sending.sendAFTN("message/CT602/CT602M01.aftn.xml");
        save("message/accounts/AAAAMHAA.account.xml");
        
        Sending.sendAFTN("message/CT602/CT602M02.aftn.xml");
        save("message/accounts/ABAAMHAC.account.xml");
        
        Sending.sendAFTN("message/CT602/CT602M03.aftn.xml");
        save("message/accounts/ABBAMHAA.account.xml");
        
        Sending.sendAFTN("message/CT602/CT602M04.aftn.xml");
        save("message/accounts/ABCAMHAA.account.xml");
        
        Sending.sendAFTN("message/CT602/CT602M05.aftn.xml");
        save("message/accounts/ACAAFTAA.account.xml");
        
        Sending.sendAFTN("message/CT602/CT602M06.aftn.xml");
        save("message/accounts/AAAAMHAA.account.xml");
        save("message/accounts/ABAAMHAC.account.xml");
        save("message/accounts/ABBAMHAA.account.xml");
        save("message/accounts/ABCAMHAA.account.xml");
        save("message/accounts/ACAAFTAA.account.xml");
        
        Sending.sendAFTN("message/CT602/CT602M07.aftn.xml");
        save("message/accounts/AAAAMHAA.account.xml");
        save("message/accounts/ABAAMHAC.account.xml");
        save("message/accounts/ABBAMHAA.account.xml");
        save("message/accounts/ABCAMHAA.account.xml");
        save("message/accounts/ACAAFTAA.account.xml");
        
        Sending.sendAFTN("message/CT602/CT602M08.aftn.xml");
        save("message/accounts/AAAAMHAA.account.xml");
        save("message/accounts/ABAAMHAC.account.xml");
        save("message/accounts/ABBAMHAA.account.xml");
        save("message/accounts/ABCAMHAA.account.xml");
        save("message/accounts/ACAAFTAA.account.xml");
        
        Sending.sendAFTN("message/CT602/CT602M09.aftn.xml");
        save("message/accounts/AAAAMHAA.account.xml");
        save("message/accounts/ABAAMHAC.account.xml");
        save("message/accounts/ABBAMHAA.account.xml");
        save("message/accounts/ABCAMHAA.account.xml");
        save("message/accounts/ACAAFTAA.account.xml");
        
        Sending.sendAFTN("message/CT602/CT602M10.aftn.xml");
        save("message/accounts/AAAAMHAA.account.xml");
        save("message/accounts/ABAAMHAC.account.xml");
        save("message/accounts/ABBAMHAA.account.xml");
        save("message/accounts/ABCAMHAA.account.xml");
        save("message/accounts/ACAAFTAA.account.xml");
    }
    
    private static void save(String account) throws X400APIException {
        Account acc = (Account) XmlSerializer.deserialize(account, Account.class);
        NormalMessage message = X400MS.getMessage(account, 10);
        if  (message != null) {
            XmlSerializer.serialize(Out.output + "\\"+ acc.getName() + "." + date.format(new Date()) + ".xml", message);
        }
    }
    
    private static void save(List<String> accounts) throws X400APIException {
        for (String account : accounts) {
            save(account);
        }
    }
    private static void clean(List<String> accounts) throws X400APIException {
        for (String account : accounts) {
            X400MS.deleteMessage(account);
        }
    }
    
}
