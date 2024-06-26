/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amhs.aftn.testtool.highlevel.Message.gateway;

import amhs.aftn.testtool.highlevel.Message.Report;
import amhs.aftn.testtool.highlevel.Message.ReportRecipient;
import com.isode.x400api.Recip;
import com.isode.x400api.X400_att;
import com.isode.x400mtapi.MTMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

public class ReportBuilder {

    private static final Logger logger = Logger.getLogger(ReportBuilder.class);
    
    private static final SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");

    private static String arrivalTime;

    public static boolean buildReport(MTMessage mtMessage, Report report) {

        MtCommon.set(mtMessage, X400_att.X400_S_OR_ADDRESS, report.getOriginator(), -1);
        MtCommon.set(mtMessage, X400_att.X400_S_MESSAGE_IDENTIFIER, MtCommon.generateMessageId(), -1);
        MtCommon.set(mtMessage, X400_att.X400_S_SUBJECT_IDENTIFIER, report.getSubjectID(), -1);
        MtCommon.set(mtMessage, X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES, report.getOriginEIT(), -1);
        MtCommon.set(mtMessage, X400_att.X400_S_CONTENT_IDENTIFIER, report.getContentID(), -1);
        MtCommon.set(mtMessage, X400_att.X400_N_CONTENT_TYPE, report.getContentType());
        
        arrivalTime = format.format(new Date()) + "Z";

        IPMBuilder.addMtRecip(mtMessage, 1, X400_att.X400_RECIP_ENVELOPE, report.getOriginator());
        int rno = 1;
        for (ReportRecipient recip : report.getRecipients()) {
            add(mtMessage, recip, rno++);
        }
        return true;
    }

    private static void add(MTMessage mtMessage, ReportRecipient recip, int index) {

        Recip recip_obj = new Recip();
        int status = com.isode.x400mtapi.X400mt.x400_mt_recipnew(mtMessage, X400_att.X400_RECIP_REPORT, recip_obj);
        if (status != X400_att.X400_E_NOERROR) {
            logger.error(String.format("Cannot add non-deliver-report recipient (code %s)", status));
            return;
        }

        MtCommon.set(recip_obj, X400_att.X400_S_OR_ADDRESS, recip.getOr(), -1);
        MtCommon.set(recip_obj, X400_att.X400_N_ORIGINAL_RECIPIENT_NUMBER, index);
        MtCommon.set(recip_obj, X400_att.X400_N_REPORT_REQUEST, 1);
        MtCommon.set(recip_obj, X400_att.X400_N_MTA_REPORT_REQUEST, 1);
        // MtCommon.set(recip_obj, X400_att.X400_N_NOTIFICATION_REQUEST, recip.getReceiptNotification());
        MtCommon.set(recip_obj, X400_att.X400_S_ARRIVAL_TIME, arrivalTime, -1);
        MtCommon.set(recip_obj, X400_att.X400_S_SUPPLEMENTARY_INFO, recip.getSupplementInfo(), -1);
        // MtCommon.set(recip_obj, X400_att.X400_S_MESSAGE_DELIVERY_TIME, recip.getArrivalTime(), -1);
        // MtCommon.set(recip_obj, X400_att.X400_N_TYPE_OF_USER, recip.getUserType());
        MtCommon.set(recip_obj, X400_att.X400_N_NON_DELIVERY_REASON, recip.getNonDeliveryReasonCode());
        MtCommon.set(recip_obj, X400_att.X400_N_NON_DELIVERY_DIAGNOSTIC, recip.getNonDeliveryDiagnosticCode());
    }
}
