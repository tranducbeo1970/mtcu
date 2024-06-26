/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amhs.aftn.testtool.highlevel.Message;

import amhs.aftn.testtool.highlevel.XmlSerializer;
import com.isode.rbac.swig.JRSecurityLabel;
import com.isode.x400.highlevel.Bodypart;
import com.isode.x400.highlevel.BodypartFTBP;
import com.isode.x400.highlevel.BodypartForwardedMessage;
import com.isode.x400.highlevel.BodypartGeneralText;
import com.isode.x400.highlevel.BodypartIA5Text;
import com.isode.x400.highlevel.P3BindSession;
import com.isode.x400.highlevel.ReceiveMsg;
import com.isode.x400.highlevel.X400APIException;
import com.isode.x400.highlevel.X400Msg;
import static com.isode.x400.highlevel.test.ReceiveX400Mail.showMessage;
import com.isode.x400api.Message;
import com.isode.x400api.X400_att;

/**
 *
 * @author andh
 */
public class Receiving {

    public static void main(String[] args) throws X400APIException {
        String account = "message/accounts/IUTAMHAA.account.xml";
        String message = "message/CTXXX/CT101.xml";

        System.out.print("Load account ");
        Account acc = (Account) XmlSerializer.deserialize(account, Account.class);
        System.out.println("[O]");

        P3BindSession connection = Sending.open(acc);

        do {
            int wait = 1;
            System.out.println("\n>>> Waiting " + wait + " seconds for a new message");
            int status = connection.waitForNewMessages(60);
            if (status == X400_att.X400_E_NOERROR) {
                System.out.println("\nFound a new message, reading it");
                ReceiveMsg rm = new ReceiveMsg(connection);
                showMessage(rm);
            } else {
                if (status == X400_att.X400_E_TIMED_OUT) {
                    System.out.println("Timed out - no new messages");
                } else if (status == X400_att.X400_E_NO_MESSAGE) {
                    System.out.println("No more messages");
                } else {
                    System.out.println("Failed to set the wait new - " + status);
                }
                break;
            }
        } while (true);
    }

    public static void showMessage(ReceiveMsg rm) throws X400APIException {
        int status = 0;

        if (status == X400_att.X400_E_NOERROR) {

            System.out.println("The type is '" + rm.getTypeAsString() + "'");

            if (rm.GetType() == X400_att.X400_MSG_MESSAGE) {
                
                
                
                System.out.println("The message is from '" + rm.getFrom() + "'");
                System.out.println("The message primary recipients are '" + rm.getToRecipients() + "'");
                if (rm.getCcRecipients() != null) {
                    System.out.println("The message copy recipients are '" + rm.getCcRecipients() + "'");
                }
                if (rm.getBccRecipients() != null) {
                    System.out.println("The message blind copy recipients are '" + rm.getBccRecipients() + "'");
                }
                System.out.println("The Subject is '" + rm.getSubject() + "'");
                System.out.println("The Date is '" + rm.getMessageSubmissionTimeAsDate().toString() + "'");
                System.out.println("The Priority is '" + rm.getX400Priority().toString() + "'");

                // X.411 Security Labels are optional 
                JRSecurityLabel securityLabel = rm.getJRSecurityLabel();
                if (securityLabel != null) {
                    System.out.println("The Security Label is '" + securityLabel.getXML() + "'");
                }

                // Show information about the bodyparts, and their contents when possible
                int numOfBodyparts = rm.getNumberOfBodyparts();
                if (numOfBodyparts == 0) {
                    System.out.println("There are no bodyparts");
                } else if (numOfBodyparts == 1) {
                    System.out.println("There is one bodypart");
                } else {
                    System.out.println("There are " + numOfBodyparts + " bodyparts");
                }

//                for (int i = 1; i <= numOfBodyparts; i++) {
//                    Bodypart bp = rm.getBodypart(i);
//                    System.out.println("Bodypart number " + i + " is of type " + bp.getTypeAsString());
//                    if (bp instanceof BodypartIA5Text) {
//                        BodypartIA5Text bpt = (BodypartIA5Text) bp;
//                        System.out.println(bpt.getStringParam(X400_att.X400_S_ENCRYPTED_DATA) + " " + bpt.getTextContent());
//                        if (save_bodyparts_in_files) {
//                            bp.saveBP("bodyparts/ia5.bp");
//                        }
//                    } else if (bp instanceof BodypartGeneralText) {
//                        BodypartGeneralText gtbp = (BodypartGeneralText) bp;
//                        System.out.println(gtbp.getCharsetString() + "Bodypart size = " + gtbp.getSize() + "\n" + gtbp.getStringRepresentation());
//                        if (save_bodyparts_in_files) {
//                            gtbp.saveBP("bodyparts/gt.bp");
//                        }
//                    } else if (bp instanceof BodypartFTBP) {
//                        BodypartFTBP ftbp = (BodypartFTBP) bp;
//                        System.out.println(ftbp.getStringRepresentation());
//                        if (ftbp.getCreationDate() != null) {
//                            System.out.println("The creation date of the file is " + ftbp.getCreationDate());
//                        }
//                        if (save_bodyparts_in_files) {
//                            ftbp.saveFTBPInDir("bodyparts");
//                        }
//                    } else if (bp.getType() == Bodypart.Bodypart_Type.BODYPART_BINARY) {
//                        System.out.println("Bodypart size = " + bp.getSize());
//                        if (save_bodyparts_in_files) {
//                            bp.saveBP("bodyparts/binary");
//                        }
//                    } else if (bp.getType() == Bodypart.Bodypart_Type.BODYPART_MESSAGE) {
//                        Message fwdMsg = new Message();
//                        com.isode.x400api.X400ms.x400_ms_msggetmessagebody(rm, i, fwdMsg);
//                        BodypartForwardedMessage fwd = new BodypartForwardedMessage(bp.getBodypartObject());
//                        fwd.setFwdMessage(fwdMsg);
//                        System.out.println(fwd.getStringRepresentation());
//                    }
//                }
            } else if (rm.GetType() == X400_att.X400_MSG_REPORT) {
                System.out.println("The DR content is:\n" + rm.getReportContentAsText());
            }

            // finish with the message
            rm.finishWithMessage(0, 0);
        } else {
            System.out.println("Problems reading the message: status = " + status);
        }
    }
    
    public static Integer getInt(X400Msg msg, int att) {
        System.out.print("Setting attribute " + att);
        int result = com.isode.x400api.X400ms.x400_ms_msggetintparam(msg, att);
        String rs = result == X400_att.X400_E_NOERROR ? "[O]" : "[X]";
        System.out.println(rs);
        return msg.GetIntValue();
    }
    
    public static String getStr(X400Msg msg, int att) {
        StringBuffer buffer = new StringBuffer();
        System.out.print("Setting attribute " + att);
        int result = com.isode.x400api.X400ms.x400_ms_msggetstrparam(msg, att, buffer);
        String rs = result == X400_att.X400_E_NOERROR ? "[O]" : "[X]";
        return buffer.toString();
    }
}
