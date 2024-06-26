/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel;

import com.isode.x400.highlevel.P3BindSession;
import com.isode.x400.highlevel.X400APIException;
import com.isode.x400.highlevel.X400Msg;
import com.isode.x400api.AMHS_att;
import com.isode.x400api.X400_att;
import java.text.DecimalFormat;

/**
 *
 * @author andh
 */
public class SendMessageWithHugeOfAddress {
    public static void main(String[] args) throws X400APIException {
        
        String str = "ABCDEFGHIJKLMNOPQRSTUV";
        
        DecimalFormat format = new DecimalFormat("000");

        String p3Address = "\"593\"/URI+0000+URL+itot://alaptop";
        String orAddress = "/CN=VHHHZQZA/OU=VHHH/O=HKGCAD/PRMD=HONGKONG/ADMD=ICAO/C=XX/";
        String pass = "attech";

        P3BindSession connection = new P3BindSession(p3Address, orAddress, pass, true);
        connection.bind();

        X400Msg x400msg = new X400Msg(connection);
        x400msg.setFrom(orAddress);

        for (int i = 0; i < 513; i++) {
            int c1 = i/100;
            int c2 = (i % 100) / 10;
            int c3 = (i % 100) % 10;
            
            String to = "/OU=VVNBZ" + str.charAt(c1) + str.charAt(c2) + str.charAt(c3) + "/O=AFTN/PRMD=VV/ADMD=ICAO/C=XX/";
            System.out.println(to);
            x400msg.setTo(to, X400Msg.DR_Request.DR_DELIVERY_REPORT, 1);
        }

        setStr(x400msg, AMHS_att.ATS_S_PRIORITY_INDICATOR, "FF");
        setStr(x400msg, AMHS_att.ATS_S_FILING_TIME, "190418");
        setStr(x400msg, AMHS_att.ATS_S_TEXT, "HELLO");

        x400msg.setSubject("IPN");
        x400msg.sendMsg(connection);
        
        System.out.println("Send OK");

    }

    private static void setStr(X400Msg x400, int attribute, String param) {
        if (param == null || param.isEmpty()) {
            return;
        }

        try {
            x400.setStringparam(attribute, param);
        } catch (X400APIException ex) {
            ex.printStackTrace();
        }
    }

    private static void setInt(X400Msg x400, int attribute, Integer param) {
        if (param == null) {
            return;
        }

        try {
            x400.setIntParam(attribute, param);
        } catch (X400APIException ex) {
            ex.printStackTrace();
        }
    }
}
