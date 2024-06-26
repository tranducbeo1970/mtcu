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
import com.isode.x400.highlevel.P7BindSession;
import com.isode.x400.highlevel.X400APIException;
import com.isode.x400.highlevel.X400Msg;
import com.isode.x400api.X400_att;

/**
 * Test program that connects to either an MTA's P3 channel or to a P7 Message Store, and submits a test message to itself.
 *
 */
public class SendX400_RN {

    private static final boolean use_p3 = true;
    private static final String p7_message_store_presentation_address = "\"3001\"/URI+0000+URL+itot://laptop:3001";
    private static final String p7_user_or_address = "/CN=AAAAMHBA/OU=AAAA/O=AA-REGION/PRMD=AMHSLAND-1/ADMD=ICAO/C=XX/";
    private static final String p7_user_password = "attech";

    private static final String p3_channel_presentation_address = "\"593\"/URI+0000+URL+itot://laptop";
    private static final String p3_user_or_address = "/CN=AAAAMHBA/OU=AAAA/O=AA-REGION/PRMD=AMHSLAND-1/ADMD=ICAO/C=XX/";
    private static final String p3_user_password = "attech";
    private static final boolean submit_only = true;

    public static void main(String[] args) {
        // sendMessage();
        // Sending misroute
        sendRN("/CN=BAAAFTAA/OU=BAAA/O=BA-REGION/PRMD=AFTNLAND-1/ADMD=ICAO/C=XX/", 
                // "140618094822688Z*/CN=BAAAFTAA/OU=BAAA/O=BA-REGION/PRMD=AFTNLAND-1/ADMD=ICAO/C=XX/", 
                "240618094822688Z*/CN=BAAAFTAA/OU=BAAA/O=BA-REGION/PRMD=AFTNLAND-1/ADMD=ICAO/C=XX/", 
                X400Msg.DR_Request.DR_NON_DELIVERY_REPORT);
    }

    private static void sendRN(String address, String subjectId, X400Msg.DR_Request request) {
        P3BindSession bind_session = open();

        X400Msg x400msg = new X400Msg(bind_session);
        int ipn_request = X400Msg.IPN_NON_RECEIPT_NOTIFICATION;

        try {
            x400msg.setTo(address, request, ipn_request);
            
            x400msg.setIntParam(X400_att.X400_N_IS_IPN, 1);
            x400msg.setStringparam(X400_att.X400_S_SUBJECT_IPM, subjectId);
            x400msg.setStringparam(X400_att.X400_S_RECEIPT_TIME, "140613084110Z");

            
            x400msg.sendMsg(bind_session);
            bind_session.unbind();
            
            final String msg_sub_id = x400msg.getMessageIdentifier();
            final String msg_st = x400msg.getSubmissionTime();
            final String ipm_id = x400msg.getMessageIPMIdentifier();
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

        bind_session = use_p3
                ? new P3BindSession(p3_channel_presentation_address, p3_user_or_address, p3_user_password, submit_only)
                : new P7BindSession(p7_message_store_presentation_address, p7_user_or_address, p7_user_password);

        try {

            bind_session.bind();

        } catch (X400APIException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return bind_session;
    }
}
