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
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Test program that connects to either an MTA's P3 channel or to a P7 Message Store, and submits a test message to itself.
 *
 */
public class SendX400Probe {

    private static final boolean use_p3 = true;
    private static final boolean send_military_message = false;

    private static final String p3_channel_presentation_address = "\"593\"/URI+0000+URL+itot://laptop";
    private static final String p3_user_or_address = "/CN=IUTAMHAA/OU=IUTA/O=IUT-REGION/PRMD=IUTLAND/ADMD=ICAO/C=XX/";
    private static final String p3_user_password = "attech";
    private static final boolean submit_only = true;

    // private static final String recipient_or_address = "/CN=BAAAFTBA/OU=BAAA/O=BA-REGION/PRMD=AFTNLAND-1/ADMD=ICAO/C=XX/";
    private static final String recipient_or_address = "/CN=BAAAFTBA/OU=BAAA/O=BA-REGION/PRMD=AFTNLAND-1/ADMD=ICAO/C=XX/";
    private static final String invalid_recipient_or_address = "/CN=BAXXXXXX/OU=BAAA/O=BA-REGION/PRMD=AFTNLAND-3/ADMD=ICAO/C=XX/";

    public static void main(String[] args) {
        try {
            P3BindSession bind_session = open();
            
            sendProbe_1st(bind_session);
            sendProbe_2nd(bind_session);
            sendProbe_3rd(bind_session);
            sendProbe_4st(bind_session);
            sendProbe_5st(bind_session);
            sendProbe_6st(bind_session);
            // sendProbe_7st(bind_session);

            bind_session.unbind();
        } catch (X400APIException ex) {
            Logger.getLogger(SendX400Probe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void sendMessage() {
        P3BindSession bind_session = open();

        X400Msg x400msg = new X400Msg(bind_session, true);
        
        int ipn_request = X400Msg.IPN_NON_RECEIPT_NOTIFICATION;
        X400Msg.DR_Request requestReport = X400Msg.DR_Request.DR_DELIVERY_REPORT;

        try {
            x400msg.setTo(recipient_or_address, requestReport, ipn_request);
            x400msg.setIntParam(X400_att.X400_N_DL_EXPANSION_PROHIBITED, 1);
            x400msg.setIntParam(X400_att.X400_N_ALTERNATE_RECIPIENT_ALLOWED, 0);
            x400msg.setIntParam(X400_att.X400_N_RECIPIENT_REASSIGNMENT_PROHIBITED, 0);
            x400msg.setIntParam(X400_att.X400_N_IMPLICIT_CONVERSION_PROHIBITED, 0);
            x400msg.setIntParam(X400_att.X400_N_CONVERSION_WITH_LOSS_PROHIBITED, 0);
            x400msg.setStringparam(X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES, "ia5-text");
            x400msg.setIntParam(X400_att.X400_N_CONTENT_TYPE, 22);
            x400msg.setIntParam(X400_att.X400_N_CONTENT_LENGTH, 1800);
        
            String ipm_id = x400msg.getMessageIPMIdentifier();

            // Send the message to the Message Store
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
    
    private static void sendProbe_1st(P3BindSession bind_session) {
        X400Msg x400msg = new X400Msg(bind_session, true);
        
        int ipn_request = X400Msg.IPN_NON_RECEIPT_NOTIFICATION;
        X400Msg.DR_Request requestReport = X400Msg.DR_Request.DR_DELIVERY_REPORT;

        try {
            x400msg.setTo(recipient_or_address, requestReport, ipn_request);
            x400msg.setStringparam(X400_att.X400_S_CONTENT_IDENTIFIER, "CT421MS01");
            // x400msg.setIntParam(X400_att.X400_N_ALTERNATE_RECIPIENT_ALLOWED, 0);
            // x400msg.setIntParam(X400_att.X400_N_RECIPIENT_REASSIGNMENT_PROHIBITED, 0);
            // x400msg.setIntParam(X400_att.X400_N_IMPLICIT_CONVERSION_PROHIBITED, 0);
            // x400msg.setIntParam(X400_att.X400_N_CONVERSION_WITH_LOSS_PROHIBITED, 0);
            x400msg.setStringparam(X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES, "ia5-text");
            x400msg.setIntParam(X400_att.X400_N_CONTENT_TYPE, 22);
            x400msg.setIntParam(X400_att.X400_N_CONTENT_LENGTH, 1800);
        
            String ipm_id = x400msg.getMessageIPMIdentifier();
            // final String msg_sub_id = x400msg.getMessageIdentifier();
            // final String msg_st = x400msg.getSubmissionTime();
            x400msg.sendMsg(bind_session);
            System.out.println("Probe 1st has submitted");

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
    
    private static void sendProbe_2nd(P3BindSession bind_session) {
        X400Msg x400msg = new X400Msg(bind_session, true);
        
        int ipn_request = X400Msg.IPN_NON_RECEIPT_NOTIFICATION;
        X400Msg.DR_Request requestReport = X400Msg.DR_Request.DR_NON_DELIVERY_REPORT;

        try {
            x400msg.setStringparam(X400_att.X400_S_CONTENT_IDENTIFIER, "CT421MS02");
            x400msg.setTo(invalid_recipient_or_address, requestReport, ipn_request);
            x400msg.setStringparam(X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES, "ia5-text");
            x400msg.setIntParam(X400_att.X400_N_CONTENT_TYPE, 22);
            x400msg.setIntParam(X400_att.X400_N_CONTENT_LENGTH, 1800);
        
            String ipm_id = x400msg.getMessageIPMIdentifier();
            x400msg.sendMsg(bind_session);
            System.out.println("Probe 2rd has submitted");

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
    
    private static void sendProbe_3rd(P3BindSession bind_session) {
        X400Msg x400msg = new X400Msg(bind_session, true);
        
        // int ipn_request = X400Msg.IPN_NON_RECEIPT_NOTIFICATION;
        //X400Msg.DR_Request requestReport = X400Msg.DR_Request.DR_NON_DELIVERY_REPORT;
        

        try {
            x400msg.setTo(invalid_recipient_or_address, 
                    X400Msg.DR_Request.DR_NON_DELIVERY_REPORT, 
                    X400Msg.IPN_NON_RECEIPT_NOTIFICATION);
            x400msg.setTo(recipient_or_address, 
                    X400Msg.DR_Request.DR_DELIVERY_REPORT, 
                    X400Msg.IPN_NON_RECEIPT_NOTIFICATION);
            x400msg.setStringparam(X400_att.X400_S_CONTENT_IDENTIFIER, "CT421MS03");
            
            x400msg.setStringparam(X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES, "ia5-text");
            x400msg.setIntParam(X400_att.X400_N_CONTENT_TYPE, 22);
            x400msg.setIntParam(X400_att.X400_N_CONTENT_LENGTH, 1800);
        
            String ipm_id = x400msg.getMessageIPMIdentifier();
            x400msg.sendMsg(bind_session);
            System.out.println("Probe 3rd has submitted");

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
    
    private static void sendProbe_4st(P3BindSession bind_session) {
        X400Msg x400msg = new X400Msg(bind_session, true);
        
        int ipn_request = X400Msg.IPN_NON_RECEIPT_NOTIFICATION;
        X400Msg.DR_Request requestReport = X400Msg.DR_Request.DR_NON_DELIVERY_REPORT;

        try {
            x400msg.setTo(recipient_or_address, requestReport, ipn_request);
            x400msg.setStringparam(X400_att.X400_S_CONTENT_IDENTIFIER, "CT421MS04");
            x400msg.setStringparam(X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES, "ia5-text");
            x400msg.setIntParam(X400_att.X400_N_CONTENT_TYPE, 22);
            x400msg.setIntParam(X400_att.X400_N_CONTENT_LENGTH, 10000);
        
            String ipm_id = x400msg.getMessageIPMIdentifier();
            x400msg.sendMsg(bind_session);
            System.out.println("Probe 1st has submitted");

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
    
    private static void sendProbe_5st(P3BindSession bind_session) {
        X400Msg x400msg = new X400Msg(bind_session, true);
        
        int ipn_request = X400Msg.IPN_NON_RECEIPT_NOTIFICATION;
        X400Msg.DR_Request requestReport = X400Msg.DR_Request.DR_NON_DELIVERY_REPORT;

        try {
            x400msg.setTo(recipient_or_address, requestReport, ipn_request);
            x400msg.setStringparam(X400_att.X400_S_CONTENT_IDENTIFIER, "CT421MS05");
            x400msg.setStringparam(X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES, "ia5-text");
            x400msg.setIntParam(X400_att.X400_N_CONTENT_TYPE, 22);
            x400msg.setIntParam(X400_att.X400_N_CONTENT_LENGTH, 100000);
        
            String ipm_id = x400msg.getMessageIPMIdentifier();
            x400msg.sendMsg(bind_session);
            System.out.println("Probe 1st has submitted");

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
    
    private static void sendProbe_6st(P3BindSession bind_session) {
        X400Msg x400msg = new X400Msg(bind_session, true);
        
        try {

            int ipn_request = X400Msg.IPN_NON_RECEIPT_NOTIFICATION;
            X400Msg.DR_Request requestReport = X400Msg.DR_Request.DR_DELIVERY_REPORT;
            String temp = "ABCDEFGHIJKLMNOPRSTUYV";
            String addTmp = "/CN=BAAAF%s%s%s/OU=BAAA/O=BA-REGION/PRMD=AFTNLAND-1/ADMD=ICAO/C=XX/";
            DecimalFormat format = new DecimalFormat("000");
            for (int i = 0; i < 512; i++) {
                String value = format.format(i);
                int i1 = Integer.parseInt(String.valueOf(value.charAt(0)));
                int i2 = Integer.parseInt(String.valueOf(value.charAt(1)));
                int i3 = Integer.parseInt(String.valueOf(value.charAt(2)));
                String c1 = String.valueOf(temp.charAt(i1));
                String c2 = String.valueOf(temp.charAt(i2));
                String c3 = String.valueOf(temp.charAt(i3));
                String address = String.format(addTmp, c1, c2, c3);
                x400msg.setTo(address, requestReport, ipn_request);
            }
            x400msg.setStringparam(X400_att.X400_S_CONTENT_IDENTIFIER, "CT421MS05");
            x400msg.setStringparam(X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES, "ia5-text");
            x400msg.setIntParam(X400_att.X400_N_CONTENT_TYPE, 22);
            // x400msg.setIntParam(X400_att.X400_N_CONTENT_LENGTH, 100000);
        
            String ipm_id = x400msg.getMessageIPMIdentifier();
            x400msg.sendMsg(bind_session);
            System.out.println("Probe 1st has submitted");

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
    
    
    private static void sendProbe_7st(P3BindSession bind_session) {
        X400Msg x400msg = new X400Msg(bind_session, true);
        
        try {

            int ipn_request = X400Msg.IPN_NON_RECEIPT_NOTIFICATION;
            X400Msg.DR_Request requestReport = X400Msg.DR_Request.DR_NON_DELIVERY_REPORT;
            String temp = "ABCDEFGHIJKLMNOPRSTUYV";
            String addTmp = "/CN=BAAAF%s%s%s/OU=BAAA/O=BA-REGION/PRMD=AFTNLAND-1/ADMD=ICAO/C=XX/";
            DecimalFormat format = new DecimalFormat("000");
            for (int i = 0; i < 676; i++) {
                String value = format.format(i);
                int i1 = Integer.parseInt(String.valueOf(value.charAt(0)));
                int i2 = Integer.parseInt(String.valueOf(value.charAt(1)));
                int i3 = Integer.parseInt(String.valueOf(value.charAt(2)));
                String c1 = String.valueOf(temp.charAt(i1));
                String c2 = String.valueOf(temp.charAt(i2));
                String c3 = String.valueOf(temp.charAt(i3));
                String address = String.format(addTmp, c1, c2, c3);
                x400msg.setTo(address, requestReport, ipn_request);
            }
            x400msg.setStringparam(X400_att.X400_S_CONTENT_IDENTIFIER, "CT421MS05");
            x400msg.setStringparam(X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES, "ia5-text");
            x400msg.setIntParam(X400_att.X400_N_CONTENT_TYPE, 22);
            // x400msg.setIntParam(X400_att.X400_N_CONTENT_LENGTH, 100000);
        
            String ipm_id = x400msg.getMessageIPMIdentifier();
            x400msg.sendMsg(bind_session);
            System.out.println("Probe 7st has submitted");

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

        bind_session = new P3BindSession(p3_channel_presentation_address, p3_user_or_address, p3_user_password, submit_only);
        try {

            bind_session.bind();

        } catch (X400APIException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return bind_session;
    }
}
