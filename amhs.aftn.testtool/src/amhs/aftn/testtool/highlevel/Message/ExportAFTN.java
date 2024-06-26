/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amhs.aftn.testtool.highlevel.Message;

import amhs.aftn.testtool.highlevel.XmlSerializer;
import amhs.database.CommonUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author andh
 */
public class ExportAFTN {

    private static final String output = "D:\\Projects\\AMHS\\output";
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss.SSS");

    public static void main(String[] args) {

        CommonUtils.configure("config/database.xml");
        DOMConfigurator.configure("config/log.xml");

        amhs.aftn.Message actualA = AFTNUtil.getMessage(5);
        if (actualA != null) {
            Out.print("Saving");
            String filename = String.format("%s\\%s.%s.xml", output, actualA.getFilingTime(), actualA.getOriginator());
            XmlSerializer.serialize(filename, actualA);
            Out.print(true);
        }
        
        List<String> accounts = new ArrayList<>();
        accounts.add("message/accounts/ABAAMHAA.account.xml");
        accounts.add("message/accounts/ABAAMHAB.account.xml");

        for (String str : accounts) {
            Account acc = (Account) XmlSerializer.deserialize(str, Account.class);
            NormalMessage messageA = X400MailUA.gettingMessage(str, 5);
            if (messageA != null) {
                Out.print("Saving");
                String filename = String.format("%s\\%s.%s.xml", output, acc.getName(), format.format(new Date()));
                XmlSerializer.serialize(filename, messageA);
                Out.print(true);
            }
        }
    }
}
