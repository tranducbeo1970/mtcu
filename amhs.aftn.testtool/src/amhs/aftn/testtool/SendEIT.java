/* 
 * Copyright (c) 2008-2010, Isode Limited, London, England.
 * All rights reserved.
 * 
 * Acquisition and use of this software and related materials for any
 * purpose requires a written licence agreement from Isode Limited,
 * or a written licence from an organisation licenced by Isode Limited
 * to grant such a licence.
 */
package amhs.aftn.testtool;

import com.isode.x400.highlevel.P3BindSession;
import com.isode.x400.highlevel.X400APIException;
import com.isode.x400.highlevel.X400Msg;
import com.isode.x400api.AMHS_att;
import com.isode.x400api.X400_att;

/**
 * Test program that connects to either an MTA's P3 channel or to a P7 Message Store, and submits a test message to itself.
 *
 */
public class SendEIT {

    private static final boolean use_p3 = true;
    private static final boolean send_military_message = false;

    private static final String p3_channel_presentation_address = "\"593\"/URI+0000+URL+itot://laptop";
    private static final String p3_user_or_address = "/CN=IUTAMHAA/OU=IUTA/O=IUT-REGION/PRMD=IUTLAND/ADMD=ICAO/C=XX/";
    private static final String p3_user_password = "attech";
    private static final boolean submit_only = true;

    // private static final String recipient_or_address = "/CN=IUTAMHAA/OU=IUTA/O=IUT-REGION/PRMD=IUTLAND/ADMD=ICAO/C=XX/";
    private static final String recipient_or_address = "/CN=BAAAFTAA/OU=BAAA/O=BA-REGION/PRMD=AFTNLAND-1/ADMD=ICAO/C=XX/";

    public static void main(String[] args) {
        // sendMessage("ia5-text", "CT423MS01", X400Msg.DR_Request.DR_DELIVERY_REPORT);
        // sendMessage("0", "CT423MS02", X400Msg.DR_Request.DR_DELIVERY_REPORT);
        // sendMessage("2.6.3.4.2", "CT423MS03", X400Msg.DR_Request.DR_NON_DELIVERY_REPORT);
        sendMessage("2.6.3.4.0", "CT423MS04", X400Msg.DR_Request.DR_NON_DELIVERY_REPORT);
        // sendMessage("{id-cs-eit-authority 1}", "CT423MS05", X400Msg.DR_Request.DR_DELIVERY_REPORT);
        // sendMessage("ia5-text", "CT423MS06", X400Msg.DR_Request.DR_DELIVERY_REPORT);
        // sendMessage("ia5-text", "CT423MS07", X400Msg.DR_Request.DR_DELIVERY_REPORT);
        // sendMessage("ia5-text", "CT423MS08", X400Msg.DR_Request.DR_DELIVERY_REPORT);
        // sendMessage("ia5-text", "CT423MS09", X400Msg.DR_Request.DR_DELIVERY_REPORT);
        // sendMessage("ia5-text", "CT423MS10", X400Msg.DR_Request.DR_DELIVERY_REPORT);
    }

    private static void sendMessage(String edit, String contentID, X400Msg.DR_Request reportRequest) {
        P3BindSession bind_session = open();
        
        X400Msg x400msg = new X400Msg(bind_session);
        int ipn_request = X400Msg.IPN_NON_RECEIPT_NOTIFICATION;

        try {
            x400msg.setTo(recipient_or_address, reportRequest, ipn_request);
            x400msg.setStringparam(X400_att.X400_S_CONTENT_IDENTIFIER, contentID);
            x400msg.setStringparam(AMHS_att.ATS_S_PRIORITY_INDICATOR, "SS");
            x400msg.setStringparam(AMHS_att.ATS_S_FILING_TIME, "021312");
            x400msg.setStringparam(AMHS_att.ATS_S_OPTIONAL_HEADING_INFO, "SSSSSSSS");
            x400msg.setStringparam(AMHS_att.ATS_S_TEXT, "ATS_EOH_MODE_LF");
            
            x400msg.setIntParam(AMHS_att.ATS_N_EXTENDED, 0);
            com.isode.x400api.X400ms.x400_ms_msgaddintparam(x400msg, AMHS_att.ATS_EOH_MODE, AMHS_att.ATS_EOH_MODE_NONE);
            x400msg.setIntParam(X400_att.X400_N_CONTENT_TYPE, 22);
            x400msg.setStringparam(X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES, edit);
            x400msg.setIntParam(X400_att.X400_N_DL_EXPANSION_PROHIBITED, 0);
            x400msg.setIntParam(X400_att.X400_N_IMPLICIT_CONVERSION_PROHIBITED, 0);
            
            x400msg.sendMsg(bind_session);
            bind_session.unbind();
            String ipm_id = x400msg.getMessageIPMIdentifier();
            final String msg_sub_id = x400msg.getMessageIdentifier();
            final String msg_st = x400msg.getSubmissionTime();
            System.out.println("Message submitted.\nMessage Submission ID: " + msg_sub_id + "\nIPM ID: " + ipm_id + "\nSubmission time: " + msg_st);

        } catch (X400APIException e1) {
            e1.printStackTrace();
            System.out.println("Exception code = " + e1.getNativeErrorCode());
            try {

                bind_session.unbind();

            } catch (X400APIException e) {
                close(bind_session);
            }
            System.exit(1);
        }
    }

    private static void close(P3BindSession session) {
        try {

            session.unbind();

        } catch (X400APIException e) {
            e.printStackTrace();
        }
    }

    private static P3BindSession open() {

        P3BindSession bind_session;

        bind_session = 
                 new P3BindSession(p3_channel_presentation_address, p3_user_or_address, p3_user_password, submit_only)
                ;

        try {

            bind_session.bind();

        } catch (X400APIException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return bind_session;
    }
}
