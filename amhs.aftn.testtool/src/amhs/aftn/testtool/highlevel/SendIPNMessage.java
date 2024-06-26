/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amhs.aftn.testtool.highlevel;

import com.isode.x400.highlevel.P3BindSession;
import com.isode.x400.highlevel.X400APIException;
import com.isode.x400.highlevel.X400Msg;
import com.isode.x400api.X400_att;

/**
 *
 * @author andh
 */
public class SendIPNMessage {

    public static void main(String[] args) throws X400APIException {

        String p3Address = "\"593\"/URI+0000+URL+itot://laptop";
        String orAddress = "/CN=IUTAMHAA/OU=IUTA/O=IUT-REGION/PRMD=IUTLAND/ADMD=ICAO/C=XX/";
        String pass = "attech";
        // String to = "/CN=VHHHZQZA/OU=VHHH/O=HKGCAD/PRMD=HONGKONG/ADMD=ICAO/C=XX/";
        String to = "/CN=BAAAFTAA/OU=BAAA/O=BA-REGION/PRMD=AFTNLAND-1/ADMD=ICAO/C=XX/";

        P3BindSession connection = new P3BindSession(p3Address, orAddress, pass, true);
        connection.bind();

        X400Msg x400msg = new X400Msg(connection);
        x400msg.setFrom(orAddress);
        x400msg.setTo(to, X400Msg.DR_Request.DR_DELIVERY_REPORT, 1);

        setInt(x400msg, X400_att.X400_N_IS_IPN, 1);
        setStr(x400msg, X400_att.X400_S_SUBJECT_IPM, "1 140318091237Z*/CN=BAAAFTAA/OU=BAAA/O=BA-REGION/PRMD=AFTNLAND-1/ADMD=ICAO/C=XX/");
        // setStr(x400msg, X400_att.X400_S_RECEIPT_TIME, "140318084110Z");
        setInt(x400msg, X400_att.X400_N_NON_RECEIPT_REASON, 0);
        setInt(x400msg, X400_att.X400_N_DISCARD_REASON, 0);
        //setInt(x400msg, X400_att.X400_N_ACK_MODE, 0);
        // setStr(x400msg, X400_att.X400_S_SUPP_RECEIPT_INFO, "140318040513Z");
        // x400msg.setSubject("IPN");
        // int result = com.isode.x400api.X400ms.x400_ms_msgaddintparam(x400msg, X400_att.X400_N_ACK_MODE, 0);
        x400msg.sendMsg(connection);

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
