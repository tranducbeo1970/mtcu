/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amhs.aftn.testtool.highlevel.Message.gateway;


import amhs.aftn.testtool.highlevel.Message.Body;
import amhs.aftn.testtool.highlevel.Message.NormalMessage;
import amhs.aftn.testtool.highlevel.Message.Recipient;
import com.isode.x400api.AMHS_att;
import com.isode.x400api.Recip;
import com.isode.x400api.X400_att;
import com.isode.x400mtapi.MTMessage;
import org.apache.log4j.Logger;

/**
 *
 * @author andh
 */
public class IPMBuilder {

    private static final Logger logger = Logger.getLogger(IPMBuilder.class);

    public static void build(MTMessage mtMessage, NormalMessage parameter) {
        build(mtMessage, parameter, 0);
    }

    public static void build(MTMessage mtMessage, NormalMessage parameter, int mode) {
        buildEnvelope(mtMessage, parameter);
        buildAts(mtMessage, parameter);
        buildContent(mtMessage, parameter);
        buildRecipients(mtMessage, parameter, mode);
    }
    
    public static void addMtRecip(MTMessage mtMessage, int rno, int type, Recipient messageCfg) {
        Recip recip_obj = new Recip();
        com.isode.x400mtapi.X400mt.x400_mt_recipnew(mtMessage, type, recip_obj);
        MtCommon.set(recip_obj, X400_att.X400_N_ORIGINAL_RECIPIENT_NUMBER, rno);
        MtCommon.set(recip_obj, X400_att.X400_S_OR_ADDRESS, messageCfg.getOr(), messageCfg.getOr().length());
        MtCommon.set(recip_obj, X400_att.X400_N_MTA_REPORT_REQUEST, messageCfg.getMtaReportRequest());
        MtCommon.set(recip_obj, X400_att.X400_N_REPORT_REQUEST, messageCfg.getOriginReportRequest());
        MtCommon.set(recip_obj, X400_att.X400_N_NOTIFICATION_REQUEST, messageCfg.getReceiptNotification());
    }
    
    public static void addMtRecip(MTMessage mtMessage, int rno, int type, String address) {
        Recip recip_obj = new Recip();
        com.isode.x400mtapi.X400mt.x400_mt_recipnew(mtMessage, type, recip_obj);
        MtCommon.set(recip_obj, X400_att.X400_N_ORIGINAL_RECIPIENT_NUMBER, rno);
        MtCommon.set(recip_obj, X400_att.X400_S_OR_ADDRESS, address, address.length());
    }
    
    private static void buildEnvelope(MTMessage mtMessage, NormalMessage parameter) {

        // logger.info("Build envelope");
        // MtMessageConfig config = GatewayConfig.instance.getDeliverMessage().getMtMessageConfig();

        // message-identifier see 4.4.2.3.12, 4.4.2.3.13
        String msgId = MtCommon.generateMessageId();
        // parameter.setMessageIdentified(msgId);
        MtCommon.set(mtMessage, X400_att.X400_S_MESSAGE_IDENTIFIER, msgId);
        MtCommon.set(mtMessage, X400_att.X400_S_OR_ADDRESS, parameter.getOriginator(), -1);
        MtCommon.set(mtMessage, X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES, parameter.getOriginEIT());
        MtCommon.set(mtMessage, X400_att.X400_N_CONTENT_TYPE, parameter.getContentType());
        MtCommon.set(mtMessage, X400_att.X400_N_PRIORITY, parameter.getPriority());
        MtCommon.set(mtMessage, X400_att.X400_N_DISCLOSURE, parameter.getDisclosure());
        MtCommon.set(mtMessage, X400_att.X400_N_IMPLICIT_CONVERSION_PROHIBITED, parameter.getConversionProhibited());
        MtCommon.set(mtMessage, X400_att.X400_N_ALTERNATE_RECIPIENT_ALLOWED, parameter.getAlternativeRecipientAllow());
        MtCommon.set(mtMessage, X400_att.X400_N_RECIPIENT_REASSIGNMENT_PROHIBITED, parameter.getReassignmentProhibited());
        MtCommon.set(mtMessage, X400_att.X400_N_DL_EXPANSION_PROHIBITED, parameter.getDlExpasionProhibited());
        MtCommon.set(mtMessage, X400_att.X400_N_CONVERSION_WITH_LOSS_PROHIBITED, parameter.getConversionProhibited());
        MtCommon.set(mtMessage, X400_att.X400_S_CONTENT_IDENTIFIER, parameter.getContentId());

    }

    private static void buildRecipients(MTMessage mtMessage, NormalMessage parameter, int mode) {
        addMtRecip(mtMessage, 1, X400_att.X400_ORIGINATOR, parameter.getOriginator());
        int rno = 1;
        for (Recipient recip : parameter.getRecipients()) {
            addMtRecip(mtMessage, rno++, X400_att.X400_RECIP_STANDARD, recip);
        }
    }
    
    private static void buildAts(MTMessage mtMessage, NormalMessage parameter) {
        if (parameter.getAts() == null) return;
        MtCommon.set(mtMessage, AMHS_att.ATS_S_PRIORITY_INDICATOR, parameter.getAts().getPriority());
        MtCommon.set(mtMessage, AMHS_att.ATS_S_FILING_TIME, parameter.getAts().getFilingTime());
        MtCommon.set(mtMessage, AMHS_att.ATS_S_TEXT, parameter.getAts().getText());
        MtCommon.set(mtMessage, AMHS_att.ATS_S_OPTIONAL_HEADING_INFO, parameter.getAts().getOptionalHeading());
        MtCommon.set(mtMessage, AMHS_att.ATS_EOH_MODE, AMHS_att.ATS_EOH_MODE_NONE);

        if (parameter.getAts().getExtended() == 1) {
            String authorizedTime = MtCommon.getAuthorizedTimeFromFilingTime(parameter.getAts().getFilingTime());
            MtCommon.set(mtMessage, X400_att.X400_S_AUTHORIZATION_TIME, authorizedTime);
            MtCommon.set(mtMessage, X400_att.X400_S_ORIGINATORS_REFERENCE, parameter.getAts().getOptionalHeading());
            Integer precedence = toPrecedence(parameter.getAts().getPriority());
            MtCommon.set(mtMessage, X400_att.X400_S_PRECEDENCE_POLICY_ID, precedence);
        }
    }

    private static void buildContent(MTMessage mtMessage, NormalMessage message) {

        // logger.info("Build ipm content");
        // X.400 Message Identifier ( in RFC 2156 String form, e.g "[<ORAddress>;<localpart>]". The local identifier part is limited to 32 chars)
        // String ipmId = MtCommon.
        String ipmId = MtCommon.generateIpmId(message.getOriginator());
        MtCommon.set(mtMessage, X400_att.X400_S_IPM_IDENTIFIER, ipmId);
        MtCommon.set(mtMessage, X400_att.X400_S_SUBJECT, message.getSubject());


        // Add an IA5 attachment using AddStrParam
        if (message.getBodies() == null)  return;
        for (Body body : message.getBodies()) {
            switch (body.getType()) {
                case "General":
                    MtCommon.set(mtMessage, X400_att.X400_T_GENERAL_TEXT, body.getText(), -1);
                    break;

                case "Ia5-Text":
                    MtCommon.set(mtMessage, X400_att.X400_T_IA5TEXT, body.getText(), -1);
                    break;
            }
        }
    }

    private static Integer toPrecedence(String value) {
        switch (value) {
           case "SS":
                return 107;
            case "DD":
                return 71;
            case "FF":
                return 57;
            case "GG":
                return 28;
            case "KK":
                return 14;
        }
        return null;
    }
}
