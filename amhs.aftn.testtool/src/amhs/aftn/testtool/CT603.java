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

import com.isode.x400.highlevel.BodypartGeneralText;
import com.isode.x400.highlevel.BodypartIA5Text;
import com.isode.x400.highlevel.P3BindSession;
import com.isode.x400.highlevel.P7BindSession;
import com.isode.x400.highlevel.X400APIException;
import com.isode.x400.highlevel.X400Msg;
import com.isode.x400api.AMHS_att;
import com.isode.x400api.BodyPart;
import com.isode.x400api.X400_att;

/**
 * Test program that connects to either an MTA's P3 channel or to a P7 Message Store, and submits a test message to itself.
 *
 */
public class CT603 {

    private static final boolean use_p3 = true;
    private static final boolean send_military_message = false;

    private static final String p7_message_store_presentation_address = "\"3001\"/URI+0000+URL+itot://laptop:3001";
    private static final String p7_user_or_address = "/OU=VVNBYYYY/O=AFTN/PRMD=VV/ADMD=ICAO/C=XX/";
    private static final String p7_user_password = "attech";

    private static final String p3_channel_presentation_address = "\"593\"/URI+0000+URL+itot://laptop";
    private static final String p3_user_or_address = "/OU=VVNBYYYY/O=AFTN/PRMD=VV/ADMD=ICAO/C=XX/";
    private static final String p3_user_password = "attech";
    private static final boolean submit_only = true;

    private static String recipient_or_address = "/OU=VVNBYYYY/O=AFTN/PRMD=VV/ADMD=ICAO/C=XX/";

    public static void main(String[] args) {
        
        recipient_or_address = "/OU=VVNBYY/O=AFTN/PRMD=VV/ADMD=ICAO/C=XX/";
        sendMessage();
    }

    private static void sendMessage() {
        P3BindSession bind_session = open();

        X400Msg x400msg = new X400Msg(bind_session);
        int ipn_request = X400Msg.IPN_NON_RECEIPT_NOTIFICATION;

        try {
            x400msg.setTo(recipient_or_address, X400Msg.DR_Request.DR_NON_DELIVERY_REPORT, ipn_request);

            // Add another File Transfer bodypart
            // BodypartIA5Text ia5 = new BodypartIA5Text("This is the content of the IA5 bodypart");
            // x400msg.addBodypart(ia5);
            // Set the content type 
            x400msg.setStringparam(AMHS_att.ATS_S_PRIORITY_INDICATOR, "SS");
            x400msg.setStringparam(AMHS_att.ATS_S_FILING_TIME, "021312");
            x400msg.setStringparam(AMHS_att.ATS_S_OPTIONAL_HEADING_INFO, "SSSSSSSS");
            x400msg.setIntParam(AMHS_att.ATS_N_EXTENDED, 1);
            
            x400msg.setIntParam(X400_att.X400_N_CONTENT_TYPE, 22);
            x400msg.setStringparam(X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES, "ia5-text");
            // x400msg.setStringparam(X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES, "1.0.10021.7.1.100 1.0.10021.7.1.1 1.0.10021.7.1.2 1.0.10021.7.1.6");
            // x400msg.setStringparam(X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES, "ia5-text 1.0.10021.7.1.100 1.0.10021.7.1.1 1.0.10021.7.1.2 1.0.10021.7.1.6");
            
            // If a Message IPM Identifier wasn't assigned, read the value that was calculated
            String ipm_id = x400msg.getMessageIPMIdentifier();
            
            BodypartIA5Text ia5Bd = new BodypartIA5Text("HHHH");
            x400msg.addBodypart(ia5Bd);
            
          
            x400msg.sendMsg(bind_session);

            // Unbind cleanly from the Message Store
            bind_session.unbind();

            // The message was correctly submitted to the Message Store
            // Retrieve the message submission ID (assigned by the MTA) and the submission time
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
