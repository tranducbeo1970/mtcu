
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report.tool;

import report.tool.database.DeliveryReport;
import com.isode.x400.highlevel.X400APIException;
import com.isode.x400api.Recip;

import com.isode.x400api.Session;
import com.isode.x400api.Traceinfo;
import com.isode.x400api.X400_att;
import com.isode.x400mtapi.MTMessage;
import com.isode.x400mtapi.X400mt;

/**
 *
 * @author ANDH
 */
public class MtConnection implements AutoCloseable {

    private boolean connected = false;
    private String channelName;
    private String logFile;
    private Session session;
    private int result;

    private static final MLogger logger = MLogger.getLogger(MtConnection.class);

    /**
     * Constructor
     *
     * @param channelName
     * @param logFile
     */
    public MtConnection(String channelName, String logFile) {
        this.channelName = channelName;
        this.logFile = logFile;
    }

    public void connect() throws X400APIException {

        if (this.connected) {
            return;
        }
        session = new Session();
        session.SetSummarizeOnBind(false);
//        logger.debug("Connect (%s)", this.channelName);

        result = X400mt.x400_mt_open(channelName, session);

        if (result != X400_att.X400_E_NOERROR) {
            // X400mt.x400_mt_close(session);
            // this.close();

            String errString = X400mt.x400_mt_get_string_error(session, result);
            logger.error("Open connection fail (code: %s - %s)", result, errString);
            // this.connected = false;
            throw new X400APIException(String.format("Connect fail (code: %s)", result));
        }

        logger.info("Connected");

        this.connected = true;
        result = X400mt.x400_mt_setstrdefault(session, X400_att.X400_S_LOG_CONFIGURATION_FILE, this.logFile, -1);
    }

    @Override
    public void close() {
        if (session != null) {
            result = X400mt.x400_mt_close(session);
            logger.error("Connection closed (code: %s)", result);
        }

        logger.info("Disconnected");
        session = null;
        this.connected = false;
    }

    public void send(DeliveryReport message) throws X400APIException {

        DeliverReport report = new DeliverReport();
        report.setOriginator(message.getOriginator());
        report.setMessageSubjectId(message.getSubjectId());
        report.setPriority(message.getPriority());

        final RptRecipient rp = new RptRecipient();
        rp.setAddress(message.getRecipient());
        rp.setNonDeliveryReason(message.getNonDeliveryReasonCode());
        rp.setNonDeliveryDiagnosticCode(message.getNonDeliveryDiagnosticCode());
        rp.setReportRequest(1);
        rp.setMtaReportRequest(1);
        report.add(rp);
        send(report);
        
        
        
//        final MTMessage mtMessage = new MTMessage();
//        result = X400mt.x400_mt_msgnew(session, X400_att.X400_MSG_REPORT, mtMessage);
//        if (result != X400_att.X400_E_NOERROR) {
//            // X400mt.x400_mt_close(session);
//            String errString = X400mt.x400_mt_get_string_error(session, result);
//            logger.error("Sending report fail (code: %s - %s)", result, errString);
//            // this.close();
//            throw new X400APIException(String.format("Create MTMessage fail (code: %s)", result));
//        }
//
//        try {
//            
//            String messageId = MtCommon.generateMessageId();
//            String arrivalTime = MtCommon.generateArrivalTime();
//
//            set(mtMessage, X400_att.X400_S_SUBJECT_IDENTIFIER, message.getSubjectId());
//            set(mtMessage, X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES, message.getEncodeInfomationType());
//
//            set(mtMessage, X400_att.X400_S_OR_ADDRESS, message.getOriginator());
//            set(mtMessage, X400_att.X400_S_MESSAGE_IDENTIFIER, messageId);
//
//            set(mtMessage, X400_att.X400_S_CONTENT_CORRELATOR_IA5_STRING, message.getContentCorrelatorIA5String());
//            set(mtMessage, X400_att.X400_S_CONTENT_IDENTIFIER, message.getContentId());
//            set(mtMessage, X400_att.X400_N_CONTENT_TYPE, message.getContentType());
//
//            addMtRecip(mtMessage, message.getOriginator(), X400_att.X400_RECIP_ENVELOPE, 1);
//
//            // getRecip(mtMessage, 1, arrivalTime);
//            // Add report recipient
//            int index = 1;
//            final Recip recip = new Recip();
//            int status = X400mt.x400_mt_recipnew(mtMessage, X400_att.X400_RECIP_REPORT, recip);
//            if (status != X400_att.X400_E_NOERROR) {
//                throw new X400APIException(String.format("Lỗi không tạo được địa chỉ người nhận (Recipient) (code: %s)", result));
//            }
//
////            set(recip, X400_att.X400_S_OR_ADDRESS, message.getRecipient(), -1);
//            set(recip, X400_att.X400_N_ORIGINAL_RECIPIENT_NUMBER, index);
////            set(recip, X400_att.X400_N_REPORT_REQUEST, 0);
////            set(recip, X400_att.X400_N_MTA_REPORT_REQUEST, 0);
////            set(recip, X400_att.X400_N_RESPONSIBILITY, 1);
////            set(recip, X400_att.X400_S_CONVERTED_ENCODED_INFORMATION_TYPES, "ia5-text", -1);
////            set(recip, X400_att.X400_S_MESSAGE_DELIVERY_TIME, this.deliveryTime, -1);
////                set(recip, X400_att.X400_N_TYPE_OF_USER, this.userType);
//
//            if (message.getArrivalTime() != null && !message.getArrivalTime().isEmpty()) {
//                set(recip, X400_att.X400_S_ARRIVAL_TIME, message.getArrivalTime(), -1);
//            } else {
//                set(recip, X400_att.X400_N_NON_DELIVERY_REASON, message.getNonDeliveryReasonCode());
//                set(recip, X400_att.X400_N_NON_DELIVERY_DIAGNOSTIC, message.getNonDeliveryDiagnosticCode());
//                set(recip, X400_att.X400_S_SUPPLEMENTARY_INFO, message.getSuplementInfo(), -1);
//            }
//
//            buildTraceInfo(mtMessage);
//            result = X400mt.x400_mt_msgsend(mtMessage);
//
//            if (result != X400_att.X400_E_NOERROR) {
//                throw new X400APIException(String.format("Delivery message fail (code: %s)", result));
//            }
//
//            logger.info("Đã gửi Report thành công");
//
//        } finally {
//            delele(mtMessage);
//        }

    }

    public void send(DeliverReport message) throws X400APIException {

        final MTMessage mtMessage = new MTMessage();
        result = X400mt.x400_mt_msgnew(session, X400_att.X400_MSG_REPORT, mtMessage);
        if (result != X400_att.X400_E_NOERROR) {
            String errString = X400mt.x400_mt_get_string_error(session, result);
            logger.error("Sending report fail (code: %s - %s)", result, errString);
            throw new X400APIException(String.format("Create MTMessage fail (code: %s)", result));
        }

        try {
            message.build(mtMessage);
            result = X400mt.x400_mt_msgsend(mtMessage);
            if (result != X400_att.X400_E_NOERROR) {
                throw new X400APIException(String.format("Delivery message fail (code: %s)", result));
            }
            
            logger.info("Report sent to %s", message.getOriginator());

        } finally {
            delele(mtMessage);
        }

    }

    public void sendDump() throws X400APIException {

        /*
        final MTMessage mtMessage = new MTMessage();
        result = X400mt.x400_mt_msgnew(session, X400_att.X400_MSG_REPORT, mtMessage);
        if (result != X400_att.X400_E_NOERROR) {
            String errString = X400mt.x400_mt_get_string_error(session, result);
            logger.error("Sending report fail (code: %s - %s)", result, errString);
            throw new X400APIException(String.format("Create MTMessage fail (code: %s)", result));
        }

        try {
            String messageId = MtCommon.generateMessageId();
            String arrivalTime = MtCommon.generateArrivalTime();

            set(mtMessage, X400_att.X400_S_SUBJECT_IDENTIFIER, "[/PRMD=VIETNAM/ADMD=ICAO/C=XX/;NCPT_AnDH..1748801-220707.083240]");
//            set(mtMessage, X400_att.X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES, "ia5-text");

            set(mtMessage, X400_att.X400_S_OR_ADDRESS, "/CN=VVTSOPTA/OU=VVTS/O=VVTS/PRMD=VIETNAM/ADMD=ICAO/C=XX/");
//             set(mtMessage, X400_att.X400_S_MESSAGE_IDENTIFIER, messageId);

            // set(mtMessage, X400_att.X400_S_CONTENT_CORRELATOR_IA5_STRING, message.getContentCorrelatorIA5String());
            // set(mtMessage, X400_att.X400_S_CONTENT_IDENTIFIER, message.getContentId());
//             set(mtMessage, X400_att.X400_N_CONTENT_TYPE, "");
            addMtRecip(mtMessage, "/CN=VVTSOPTA/OU=VVTS/O=VVTS/PRMD=VIETNAM/ADMD=ICAO/C=XX/", X400_att.X400_RECIP_ENVELOPE, 1);

            // getRecip(mtMessage, 1, arrivalTime);
            // Add report recipient
            int index = 1;
            final Recip recip = new Recip();
            int status = X400mt.x400_mt_recipnew(mtMessage, X400_att.X400_RECIP_REPORT, recip);
            if (status != X400_att.X400_E_NOERROR) {
                throw new X400APIException(String.format("Lỗi không tạo được địa chỉ người nhận (Recipient) (code: %s)", result));
            }

            set(recip, X400_att.X400_S_OR_ADDRESS, "/CN=VVTSOPTA/OU=VVTS/O=VVTS/PRMD=VIETNAM/ADMD=ICAO/C=XX/", -1);
            set(recip, X400_att.X400_N_ORIGINAL_RECIPIENT_NUMBER, index);
            set(recip, X400_att.X400_N_REPORT_REQUEST, 1);
            set(recip, X400_att.X400_N_MTA_REPORT_REQUEST, 2);
            set(recip, X400_att.X400_N_RESPONSIBILITY, 1);
            set(recip, X400_att.X400_S_CONVERTED_ENCODED_INFORMATION_TYPES, "ia5-text", -1);
//            set(recip, X400_att.X400_S_MESSAGE_DELIVERY_TIME, this.deliveryTime, -1);
//                set(recip, X400_att.X400_N_TYPE_OF_USER, this.userType);

//            if (message.getArrivalTime() != null && !message.getArrivalTime().isEmpty()) {
//                set(recip, X400_att.X400_S_ARRIVAL_TIME, message.getArrivalTime(), -1);
//            } else {
            set(recip, X400_att.X400_N_NON_DELIVERY_REASON, 1);
            set(recip, X400_att.X400_N_NON_DELIVERY_DIAGNOSTIC, 4);
//            set(recip, X400_att.X400_S_SUPPLEMENTARY_INFO, null, -1);
//            }

            buildTraceInfo(mtMessage);
            result = X400mt.x400_mt_msgsend(mtMessage);

            if (result != X400_att.X400_E_NOERROR) {
                String errString = X400mt.x400_mt_get_string_error(session, result);
                throw new X400APIException(String.format("Delivery message fail (code: %s - %s)", result, errString));
            }

            logger.info("Đã gửi Report thành công");

        } finally {
            delele(mtMessage);
        }
         */
        DeliverReport report = new DeliverReport();
        report.setOriginator("/CN=VVTSOPTA/OU=VVTS/O=VVTS/PRMD=VIETNAM/ADMD=ICAO/C=XX/");
        report.setMessageSubjectId("[/PRMD=VIETNAM/ADMD=ICAO/C=XX/;NCPT_AnDH..1174001-220707.172816]");
        report.setPriority(1);

//        for (AddressConversionLog add : deliverAsReportAddressList) {
        final RptRecipient rp = new RptRecipient();
        rp.setAddress("/CN=VVTSOPTA/OU=VVTS/O=VVTS/PRMD=VIETNAM/ADMD=ICAO/C=XX/");
        rp.setNonDeliveryReason(MtAttributes.RS_UNABLE_TO_TRANSFER);
        rp.setNonDeliveryDiagnosticCode(MtAttributes.D_UNRECOGNISED_OR_NAME);
        rp.setReportRequest(1);
        rp.setMtaReportRequest(1);
        report.add(rp);
//        }
        send(report);

    }

    private void buildTraceInfo(MTMessage mtMessage) {
        // get config
//        final TraceInfo config = Config.instance.getAftnChannel().getTraceInfo();

        // Instance new trace
        final Traceinfo traceInfo = new Traceinfo();

        final int status = com.isode.x400mtapi.X400mt.x400_mt_traceinfonew(mtMessage, traceInfo, X400_att.X400_TRACE_INFO);
        if (status != X400_att.X400_E_NOERROR) {
            return;
        }

        set(traceInfo, X400_att.X400_S_GLOBAL_DOMAIN_ID, "TEST-TOOL", -1);
        set(traceInfo, X400_att.X400_S_DSI_ARRIVAL_TIME, MtCommon.generateArrivalTime(), -1);
        set(traceInfo, X400_att.X400_N_DSI_ROUTING_ACTION, 1);
    }

    private void set(Traceinfo traceObject, int attribute, String value, int length) {
        // set(traceObject, attribute, value, length, 0);
        com.isode.x400api.X400.x400_traceinfoaddstrparam(traceObject, attribute, value, length);
    }

    private void set(Traceinfo traceObject, int attribute, Integer value, Integer mode) {
        if (value == null) {
            return;
        }

        com.isode.x400api.X400.x400_traceinfoaddintparam(traceObject, attribute, value);

    }

    private void set(Traceinfo traceObject, int attribute, Integer value) {
        set(traceObject, attribute, value, 0);
    }

    private void addMtRecip(MTMessage mtMessage, String address, int type, int rno) {
        final Recip recip = new Recip();

        int status = com.isode.x400mtapi.X400mt.x400_mt_recipnew(mtMessage, type, recip);
        if (status != X400_att.X400_E_NOERROR) {
            return;
        }

        set(recip, X400_att.X400_N_ORIGINAL_RECIPIENT_NUMBER, rno);
        set(recip, X400_att.X400_S_OR_ADDRESS, address, address.length());
        set(recip, X400_att.X400_N_REPORT_REQUEST, 2);
        set(recip, X400_att.X400_N_MTA_REPORT_REQUEST, 3);
        set(recip, X400_att.X400_S_FREE_FORM_NAME, address, address.length());
    }

    private void set(MTMessage mt, int attribute, String value) {
        if (value == null || value.isEmpty()) {
            return;
        }
        X400mt.x400_mt_msgaddstrparam(mt, attribute, value, value.length());
    }

    private void set(MTMessage mt, int attribute, Integer value) {
        set(mt, attribute, value, 0);
    }

    private void set(MTMessage mt, int attribute, Integer value, Integer mode) {
        if (value == null) {
            return;
        }

        X400mt.x400_mt_msgaddintparam(mt, attribute, value);

    }

    private void set(Recip recipObj, int attribute, String value, int length, Integer mode) {
        if (value == null || value.isEmpty()) {
            return;
        }
        com.isode.x400mtapi.X400mt.x400_mt_recipaddstrparam(recipObj, attribute, value, length);

    }

    private void set(Recip recipObj, int attribute, String value) {
        set(recipObj, attribute, value, value.length(), 0);
    }

    private void set(Recip recipObj, int attribute, Integer value, Integer mode) {
        if (value == null) {
            return;
        }

        com.isode.x400mtapi.X400mt.x400_mt_recipaddintparam(recipObj, attribute, value);

    }

    private void set(Recip recipObj, int attribute, Integer value) {
        set(recipObj, attribute, value, 0);
    }

    private void set(Recip recipObj, int attribute, String value, int length) {
        set(recipObj, attribute, value, length, 0);
    }

    private void delele(MTMessage mtMessage) {
        int removeMSG = X400mt.x400_mt_msgdel(mtMessage);
//        logger.error("Message deleted (code: %s)", removeMSG);
    }

    /**
     * @return the connected
     */
    public boolean isConnected() {
        return connected;
    }
}
