/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu;

import com.attech.amhs.mtcu.common.AmhsValidator;
import com.attech.amhs.mtcu.common.MtCommon;
import com.attech.amhs.mtcu.isode.AddressConvertResult;
import com.attech.amhs.mtcu.config.AMHSChannelConfig;
import com.isode.dsapi.DSAPIException;
import com.isode.x400.highlevel.X400APIException;
import com.isode.x400api.X400_att;
import com.isode.x400mtapi.MTMessage;
import com.attech.amhs.mtcu.database.entity.*;
import com.attech.amhs.mtcu.config.Config;
import com.attech.amhs.mtcu.config.EncodedInformationType;
import com.attech.amhs.mtcu.config.RecipientConfig;
import com.attech.amhs.mtcu.config.StringValue;
import com.attech.amhs.mtcu.database.dao.MessageDao;
import com.attech.amhs.mtcu.isode.ReceiveMessage;
import com.attech.amhs.mtcu.database.enums.ConvertResult;
import com.attech.amhs.mtcu.database.enums.MessageCategory;
import com.attech.amhs.mtcu.database.enums.MessageType;
import com.attech.amhs.mtcu.database.enums.Priority;
import com.attech.amhs.mtcu.isode.DSAConnection;
import com.attech.amhs.mtcu.isode.DeliverMessage;
import com.attech.amhs.mtcu.isode.DeliverReport;
import com.attech.amhs.mtcu.isode.Recipient;
import com.attech.amhs.mtcu.isode.ReceiveIPN;
import com.attech.amhs.mtcu.isode.ReceiveProbe;
import com.attech.amhs.mtcu.isode.ReceiveReport;
import com.attech.amhs.mtcu.common.StringUtil;
import com.attech.amhs.mtcu.database.Connection;
import com.attech.amhs.mtcu.isode.RptRecipient;
import com.isode.x400api.Session;
import com.isode.x400mtapi.X400mt;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ANDH
 */
public class AMHSProcessor {

    private final String E_WAITING_FOR_MESSAGE_FAIL = "Waiting for AMHS message fail (%s) ";
    private final String E_GETTING_MESSAGE_FAIL = "Getting message fail (%s)";
    private final String E_FINISH_GETTING_MESSAGE_FAIL = "Finish getting message fail (%s)";
    private final String E_DLEXPANSION_PROHIBITED = "Ditribution list expansion is prohibited";
    private final String E_UNSUPPORT_CONTENT_TYPE = "Unsupport content type (%s)";
    private final String E_EMPTY_EIT = "Empty encoded-information-type";
    private final String E_UNSUPPORTED_EIT = "unsupport encoded-information-type (%s)";
    private final String E_ATS_HEADER = "unable to convert to AFTN due to ATS-Message-Header or Heading Fields syntax error";
    private final String E_UNSUPPORT_BODYPART_TYPE = "unable to convert to AFTN due to unsupported body part type";
    private final String E_INVALID_CHARACTER = "IPM message contains invalid character";
    private final String E_CONVERSION_PROHIBITED = "unable to convert to AFTN cause of conversion prohibited";
    private final String E_MAX_RECIPIENT_EXCEEED = "unable to convert to AFTN due to number of recipients";
    private final String E_LINE_TOO_LONG = "unable to convert to AFTN due to line length";
    private final String E_CONTENT_TOO_LONG = "unable to convert to AFTN due to message text length";
    private final String E_MULTI_BODYPART = "unable to convert to AFTN due to multiple body parts";
    private final String E_CONVERT_ORIGINATOR_FAIL = "unable to convert to AFTN due to unrecognized originator O/R address";
    private final String E_DELIVER_SUCCESS = "Delivered";
    private final String E_CONVERT_SUCCESS = "Converted";
    private final String E_NRN_REJECT = "MTCU rejects NRN message (IPN) and reports it to control position";
    private final String E_MISROUTE_RN = "unable to convert RN to AFTN ACK service message due to misrouted RN";
    private final String E_RN_WITHOUT_SS_REJECT = "MTCU rejects IPN of message which doesnot have priority of SS";
    private final String SUBJECT_MESSAGE_CANNOT_BE_FOUND = "Subject message cannot be found";
    private final String UNKNOWN_ADDRESS = "Unknown address indicator %s";
    private final String DIFFERENCE_ADDRESS = "Original-Intended-Recipient is quite difference from actual recipient. DR is rejected and reported to control position";
    private final String NOT_CORRECT_NDR_TYPE = "NDR is not the Unrecognised O/R Name Report. NDR is rejected and reported to control position.";
    private final String ACTUAL_RECIPIENT_CANNOT_BE_CONVERT = "Actual-recipients-name cannot be converted";

    private static final Logger logger = LoggerFactory.getLogger(AMHSProcessor.class);

    private final SimpleDateFormat filingTimeFormat = new SimpleDateFormat("ddHHmm");
    private final MessageDao messageDao = new MessageDao();
    private AMHSChannelConfig channel;
    private List<AftnConfig> configAFTN;

    private int index = 0;

    public AMHSProcessor() {
    }

    public AMHSProcessor(AMHSChannelConfig config,List<AftnConfig> cf) {
        this();
        this.channel = config;
        this.configAFTN = cf;
        Connection.setGwoutName(config.getGoutName());
        
        /*
        System.out.println("!!!!!!!");
        for (AftnConfig config1 : configAFTN) {
            int id = config1.getId();
            String content = config1.getCCT();
            String add = config1.getADDRESS();
            
            System.out.println(Integer.toString(id) + "-" + content + "-" + add);

        }
        System.out.println("!!!!!!!");
        */
    }

    public void process() throws X400APIException, SQLException, DSAPIException, Exception {
        Session session = new Session();
        session.SetSummarizeOnBind(false);
        
        // DUC 19/10/2024
        // x400mt
        String channame = this.channel.getChannelName();
        logger.info("Connecting to {}", this.channel.getChannelName());

        int result = X400mt.x400_mt_open(this.channel.getChannelName(), session);
        
        X400mt.x400_mt_setstrdefault(session, X400_att.X400_S_LOG_CONFIGURATION_FILE, this.channel.getLogFile(), -1);
        
        if (result != X400_att.X400_E_NOERROR) {
            throw new X400APIException(String.format("Connect to %s fail (code: %s)", this.channel.getChannelName(), result), result);
        }
        else {
             logger.info("Connect OK, System's ready");
        }

        try {
            do {
                do {
                    logger.debug("Getting message " + index);
                    final MTMessage mtmessage = new MTMessage();

                    result = X400mt.x400_mt_msggetstart(session, mtmessage);

                    if (result == X400_att.X400_E_NO_MESSAGE) {
                        logger.debug("No message in queue");
                        break;
                    }

                    if (result != X400_att.X400_E_NOERROR) {
                        String errorMessage = String.format(E_GETTING_MESSAGE_FAIL, result);
                        logger.error(errorMessage);
                        throw new X400APIException(errorMessage, result);
                    }

                    try {
                        
                        /*
                        XU LY DIEN VAN AMHS LAY TU MTCU
                        
                        *
                        */                        
                        processMessage(mtmessage, session);

                        logger.info("Commit message status");
                        result = X400mt.x400_mt_msggetfinish(mtmessage, 0, 0, 0, null);

                        if (result != X400_att.X400_E_NOERROR) {
                            logger.error(E_FINISH_GETTING_MESSAGE_FAIL, result);
                            // throw new X400APIException(String.format(E_FINISH_GETTING_MESSAGE_FAIL, result));
                        }

                    } finally {

                        //if (channel.isDeleteMessageObject()) {
                        //logger.info("Delete MSG Object");
                        result = X400mt.x400_mt_msgdel(mtmessage);
                        logger.error(result == X400_att.X400_E_NOERROR ? "MSG removed" : "MSG removed fail (code: " + result + ")");
                        //}
                    }

                } while (result != X400_att.X400_E_NOERROR);

                logger.debug("Waiting for new message");
                result = com.isode.x400mtapi.X400mt.x400_mt_wait(session, this.channel.getWaitTimeout());
                if (result == X400_att.X400_E_TIMED_OUT) {
                    logger.debug("Waiting timeout ({})", result);
                    continue;
                }

                if (result != X400_att.X400_E_NOERROR) {
                    String errorMessage = String.format(E_WAITING_FOR_MESSAGE_FAIL, result);
                    logger.error(errorMessage);
                    throw new X400APIException(errorMessage);
                }

            } while (true);

        } finally {
            int closeCode = X400mt.x400_mt_close(session);
            if (closeCode != X400_att.X400_E_NOERROR) {
                logger.error("Close connection fail (code: {})", closeCode);
            }
        }
    }

    /*--------------------------------------------------
    
    
    
    --------------------------------------------------*/
    private void processMessage(MTMessage mtmessage, Session session) throws SQLException, X400APIException, DSAPIException {

        final int type = mtmessage.GetType();

        switch (type) {
            case X400_att.X400_MSG_PROBE:
                logger.info("Process PROBE Message");
                processProbe(mtmessage, session);
                break;

            case X400_att.X400_MSG_REPORT:
                logger.info("Process REPORT Message");
                processNDR(mtmessage, session);
                break;

            case X400_att.X400_MSG_MESSAGE:
                final int isIPN = MtCommon.getInt(mtmessage, X400_att.X400_N_IS_IPN);
                if (isIPN == 1) {
                    logger.info("Process IPN Message");
                    processIPN(mtmessage, session);
                    return;
                }

                logger.info("Process IPM Message");
                processIPM(mtmessage, session);
                break;

            default:
                logger.error("Unknown message type (type {})", type);
        }
    }
    /*--------------------------------------------------
    
    
    
    --------------------------------------------------*/
    private void processIPM(MTMessage mtmessage, Session session) throws X400APIException, SQLException, DSAPIException {

        final ReceiveMessage ipm = new ReceiveMessage(mtmessage);
        logger.info("--------------------------------\n{}", ipm.toString());
        logger.info("--------------------------------\n");
        final int error = validate(ipm);
        if (error != MtAttributes.E_NO_ERROR) {

            // Return Non Delivery Report to sender
            final String err = this.processError(ipm, error, session);

            // Save log to DB
            final MessageConversionLog messageConversionLog = ipm.createMessageConversionLog();
            messageConversionLog.setStatus(ConvertResult.FAIL);
            messageConversionLog.setRemark(err);
            messageDao.save(messageConversionLog);

            // Save log to File
            logger.error(err);
            return;

        }

        final int priority = MtCommon.priorityMapping(ipm.getAtsPriority());

        // Convert Origin
        final AddressConvertResult convertedResult = DSAConnection.getInstance().convertToAftnAddress(ipm.getOrAddress(), false);
        if (convertedResult == null) {

            // Return Non Delivery Report to sender
            DeliverReport report = ipm.createNonDeliverReport(MtAttributes.RS_UNABLE_TO_TRANSFER, MtAttributes.D_INVALID_ARGUMENTS, E_CONVERT_ORIGINATOR_FAIL);
            send(report, session);

            // Save log to DB
            final MessageConversionLog messageConversionLog = ipm.createMessageConversionLog();
            messageConversionLog.setStatus(ConvertResult.FAIL);
            messageConversionLog.setRemark(E_CONVERT_ORIGINATOR_FAIL);
            messageDao.save(messageConversionLog);

            // Save log to File
            logger.error(E_CONVERT_ORIGINATOR_FAIL);
            return;
        }

        final String aftnOrigin = convertedResult.getConvertedAddress();
        final List<Recipient> envelopeRecips = ipm.getEnvelopeRecipients();
        final List<String> aftnAddresses = new ArrayList<>();
        final List<Recipient> asymAddresses = new ArrayList<>();

        for (Recipient address : envelopeRecips) {
            final AddressConvertResult convertedResult2 = DSAConnection.getInstance().convertToAftnAddress(address.getAddress(), true);
            if (convertedResult2 == null) {
                logger.error("Convert recipient {} fail", address.getAddress());
                continue;
            }

            aftnAddresses.add(convertedResult2.getConvertedAddress());
            if (convertedResult2.verifyAsyncAddress()) {
                asymAddresses.add(address);
            }
            logger.debug("Convert {} --> {}{}",
                    new Object[]{
                        address.getAddress(),
                        convertedResult2.getConvertedAddress(),
                        convertedResult2.verifyAsyncAddress() ? "[ASYM]" : ""});
        }

        // Report asym address to controller position
        reportAsymmetricAddress(ipm, asymAddresses, session);

        //
        MessageConversionLog log = ipm.createMessageConversionLog();
        log.setStatus(ConvertResult.SUCCESS);
        log.setRemark(E_CONVERT_SUCCESS);

        // Convert recipent to AFTN address
        List<List<String>> aftnAddress = MtCommon.buildupAftnAddresses(aftnAddresses);

        // Convert content
        List<String> contents = processContent(ipm.getContent());

        List<GatewayOut> gatewayOuts = new ArrayList<>();

        Date date = new Date();
        for (String ctn : contents) {
            for (List<String> addStr : aftnAddress) {

                GatewayOut out = new GatewayOut();
                out.setAddress(String.join(" ", addStr));
                out.setAmhsid(ipm.getMessageId());
                out.setFilingTime(ipm.getAtsFilingTime());
                out.setInsertedTime(date);
                out.setOptionalHeading(ipm.getAtsOhi());
                out.setOriginator(aftnOrigin);
                out.setPriority(priority);
                out.setText(ctn);
                gatewayOuts.add(out);

                // Create converted log
                MessageConversionLog convertedLog = new MessageConversionLog();
                convertedLog.setCategory(MessageCategory.GENERAL);
                convertedLog.setContent(ctn);
                convertedLog.setConvertedTime(log.getConvertedTime());
                convertedLog.setDate(log.getDate());
                convertedLog.setFilingTime(ipm.getAtsFilingTime());
                convertedLog.setOhi(ipm.getAtsOhi());
                convertedLog.setOrigin(aftnOrigin);
                convertedLog.setPriority(Priority.valueOf(ipm.getAtsPriority()));
                convertedLog.setStatus(ConvertResult.SUCCESS);
                convertedLog.setType(MessageType.AFTN);
                for (String add : addStr) {
                    convertedLog.addAddressLog(new AddressConversionLog(add));
                }
                log.addChild(convertedLog);
            }
        }

        // Save gatewayout
        messageDao.save(gatewayOuts);

        // Save log
        messageDao.save(log);

        // Return delivery Report
        DeliverReport report = ipm.createDeliverReport();
        try {
            send(report, session);
        } catch (Exception ex) {
            logger.error("ERROR FOUND: ", ex);
        }
        logger.info(E_CONVERT_SUCCESS);
    }
    /*--------------------------------------------------
    
    
    
    --------------------------------------------------*/
    private void processIPN(MTMessage mtmessage, Session session) throws X400APIException, SQLException, DSAPIException {

        final ReceiveIPN ipn = new ReceiveIPN(mtmessage);

        // logger.info("Process IPN from " + ipn.getOrigin());
        logger.info("--------------------------------------------------------------------\n{}", ipn.toString());

        if (!ipn.isReceipt()) {

            // Report to controller
            this.report2Controller(String.format("%s\r\n\r\n%s", E_NRN_REJECT, ipn.toString()), session);

            // Save log to DB
            final MessageConversionLog log = ipn.createMessageConversionLog();
            log.setRemark(E_NRN_REJECT);
            log.setStatus(ConvertResult.FAIL);
            messageDao.save(log);

            // Write log to file
            logger.warn(E_NRN_REJECT);
            return;
        }

        // Get previous message
        final MessageConversionLog lastConvertedMessage = messageDao.getByIpmId(ipn.getSubjectIPM());
        if (lastConvertedMessage == null || lastConvertedMessage.getParents() == null) {

            // In case previouse converted message not found
            this.report2Controller(String.format("%s\r\n\r\n%s", E_MISROUTE_RN, ipn.toString()), session);

            // Save log to DB
            final MessageConversionLog log = ipn.createMessageConversionLog();
            log.setRemark(E_MISROUTE_RN);
            log.setStatus(ConvertResult.FAIL);
            messageDao.save(log);

            // Return non-delivery-report
            DeliverReport report = ipn.createNonDeliverReport(MtAttributes.RS_UNABLE_TO_TRANSFER, MtAttributes.D_INVALID_ARGUMENTS, E_MISROUTE_RN);
            send(report, session);

            // Write log to file
            logger.warn(E_MISROUTE_RN);
            return;
        }

        // Get original aftn message
        final MessageConversionLog aftnMessage = lastConvertedMessage.getParents();
        if (aftnMessage.getPriority() != Priority.SS) {

            // In case previouse converted message not found
            this.report2Controller(String.format("%s\r\n\r\n%s", E_RN_WITHOUT_SS_REJECT, ipn.toString()), session);

            // Save log to DB
            final MessageConversionLog log = ipn.createMessageConversionLog();
            log.setRemark(E_RN_WITHOUT_SS_REJECT);
            log.setStatus(ConvertResult.FAIL);
            messageDao.save(log);

            // Write log to file
            logger.warn(E_RN_WITHOUT_SS_REJECT);
            return;
        }

        final AddressConvertResult result = DSAConnection.getInstance().convertToAftnAddress(ipn.getOrigin(), false);
        if (result == null) {
            final String errMessage = String.format("Convert originator address %s fail", ipn.getOrigin());

            // Report this error to controller
            this.report2Controller(String.format("%s\r\n\r\n%s", errMessage, ipn.toString()), session);

            // Save log to DB
            final MessageConversionLog log = ipn.createMessageConversionLog();
            log.setRemark(errMessage);
            log.setStatus(ConvertResult.FAIL);
            messageDao.save(log);

            // Write log to file
            logger.warn(errMessage);
            return;
        }
        // String originator = result.getAddress();
        final String originator = result.getConvertedAddress();
        final String recip = aftnMessage.getOrigin();
        final String filingTime = ipn.getReceiptTime().substring(4, 10);
        // String content = generateAckMessage(aftnMessage.getFilingTime(), recip);
        final String content = aftnMessage.generateAckMessage();

        // output to gateway out
        final GatewayOut gatewayOut = new GatewayOut();
        gatewayOut.setAmhsid(ipn.getMessageId());
        gatewayOut.setPriority(0);
        gatewayOut.setOriginator(originator);
        gatewayOut.setAddress(recip);
        gatewayOut.setFilingTime(filingTime);
        gatewayOut.setText(content);
        messageDao.save(gatewayOut);

        // Output log
        final MessageConversionLog ipnMessageLog = ipn.createMessageConversionLog();
        ipnMessageLog.setRemark("Converted");
        ipnMessageLog.setStatus(ConvertResult.SUCCESS);

        final MessageConversionLog aftnMessageLog = new MessageConversionLog();
        aftnMessageLog.setType(MessageType.AFTN);
        aftnMessageLog.setCategory(MessageCategory.ACKNOWLEDGE);
        aftnMessageLog.setContent(content);
        aftnMessageLog.setConvertedDate(ipnMessageLog.getConvertedTime());
        aftnMessageLog.setFilingTime(filingTime);
        aftnMessageLog.setMessageId(ipn.getMessageId());
        aftnMessageLog.setOrigin(originator);
        aftnMessageLog.setPriority(Priority.SS);
        aftnMessageLog.setStatus(ConvertResult.SUCCESS);
        aftnMessageLog.addAddressLog(new AddressConversionLog(recip));
        ipnMessageLog.addChild(aftnMessageLog);
        messageDao.save(ipnMessageLog);
        logger.info(E_CONVERT_SUCCESS, ipn.getOrigin());
    }

    private void processNDR(MTMessage mtmessage, Session session) throws X400APIException, SQLException, DSAPIException {
        final ReceiveReport report = new ReceiveReport(mtmessage);
        logger.info("--------------------------------------------------------------------\n{}", report.getReportContent());

        if (report.getUnrecognizeAddresses().isEmpty()) {
            processReportLog(report, NOT_CORRECT_NDR_TYPE, session);
            return;
        }

        // Get AMHS log message
        final MessageConversionLog log = messageDao.getByMessageId(report.getSubjectId());
        if (log == null || log.getParents() == null) {
            processReportLog(report, SUBJECT_MESSAGE_CANNOT_BE_FOUND, session);
            return;
        }

        final MessageConversionLog originAFTN = log.getParents();
        final AddressConvertResult result = DSAConnection.getInstance().convertToAftnAddress(report.getOrigin(), false);
        if (result == null || !originAFTN.getOrigin().equalsIgnoreCase(result.getConvertedAddress())) {
            final String err = (result == null)
                    ? String.format(UNKNOWN_ADDRESS, report.getOrigin()) : DIFFERENCE_ADDRESS;
            processReportLog(report, err, session);
            return;
        }

        final List<String> unknownAddresses = new ArrayList<>();
        final List<String> unrecognizedAddresses = report.getUnrecognizeAddresses();
        final Set<AddressConversionLog> aftnAddresses = originAFTN.getAddressLogs();
        for (AddressConversionLog aftnAddress : aftnAddresses) {
            final String addStr = aftnAddress.getAddress();
            for (String unknownAddress : unrecognizedAddresses) {
                if (!unknownAddress.contains(addStr)) {
                    continue;
                }
                unknownAddresses.add(addStr);
                break;
            }
        }

        if (unknownAddresses.isEmpty()) {
            processReportLog(report, ACTUAL_RECIPIENT_CANNOT_BE_CONVERT, session);
            return;
        }

        // Delivery AFTN message
        final GatewayOut out = new GatewayOut();
        out.setAddress(originAFTN.getOrigin());
        out.setFilingTime(MtCommon.generateFilingTime());
        out.setOriginator(Config.instance.getSysAFTNAddress());
        out.setPriority(originAFTN.getPriority().getValue());
        out.setText(originAFTN.generateUnknowAddressMessage(unknownAddresses));
        messageDao.save(out);

        // Create log record
        final MessageConversionLog reportMessageLog = report.createMessageConversionLog();
        log.setRemark("Converted");
        log.setStatus(ConvertResult.SUCCESS);

        final MessageConversionLog aftnMessageLog = new MessageConversionLog();
        aftnMessageLog.setType(MessageType.AFTN);
        aftnMessageLog.setCategory(MessageCategory.UNKNOWN);
        aftnMessageLog.setContent(out.getText());
        aftnMessageLog.setFilingTime(out.getFilingTime());
        aftnMessageLog.setOrigin(out.getOriginator());
        aftnMessageLog.setPriority(originAFTN.getPriority());
        aftnMessageLog.addAddressLog(new AddressConversionLog(originAFTN.getOrigin()));
        aftnMessageLog.setStatus(ConvertResult.SUCCESS);
        reportMessageLog.addChild(aftnMessageLog);

        logger.info(E_DELIVER_SUCCESS);
    }

    public boolean phankenhduockhong(String regex, String add) {
        boolean b = false;
        // List of strings to search within
        //String add = "VVTS";
        // Regex pattern to match string1 or string2
        //String regex = "^VVNB|^VVTS";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);

        // Iterate over the list and find matches
        Matcher matcher = pattern.matcher(add);
        if (matcher.find()) {
            //System.out.println("Found a match in: " + add);
            b = true;
        } else {
            //System.out.println("No match in: " + add);
            b = false;
        }
        return b;
    }

    
    
    private boolean testPhankenhProbeAftn(String addamhs) {
        boolean phanduoc = false;
        System.out.println("@@@@@ TRY ROUTING TEST @@@@@");
        for (AftnConfig config1 : configAFTN) {
            int id = config1.getId();
            String cct = config1.getCCT();
            String tmp = config1.getAddress_pattern();
            
            
            AddressConvertResult result = null;
            try {
                result = DSAConnection.getInstance().convertToAftnAddress(addamhs, false);
            } catch (DSAPIException ex) {
                java.util.logging.Logger.getLogger(AMHSProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (result == null) {
                final String errMessage = String.format("Convert originator address %s fail", addamhs);
                // Write log to file
                logger.warn(errMessage);
                return false;
            }
            
            String aftnaddress = result.getConvertedAddress();          // AFTN Scheme
            if(phankenhduockhong(tmp,aftnaddress)) {
                System.out.println(Integer.toString(id) + "-" + cct + "-" + tmp + " address test [" + addamhs + "] ["  + aftnaddress + "] -> GOOD");
                phanduoc = true;
            } else {
                System.out.println(Integer.toString(id) + "-" + cct + "-" + tmp + " address test [" + addamhs + "] ["  + aftnaddress + "] -> FAIL");
            }

        }
        System.out.println("@@@@@@@");
        
        return phanduoc;
    }
    
    // DUC PROBE
    private void processProbe(MTMessage mtmessage, Session session) throws SQLException, X400APIException, DSAPIException {
        final ReceiveProbe probe = new ReceiveProbe(mtmessage);

        if (MtAttributes.BI_CONTENT_TYPE_IPM_1988 != probe.getContentType()) {
            final String err = String.format(E_UNSUPPORT_CONTENT_TYPE, probe.getContentType());
            processProbeLog(probe, MtAttributes.D_CONTENT_SYNTAX_ERROR, err, session);
            return;
        }

        // Checking encoded-information-type
        if (probe.getEncodeInformationType() != null && !Config.instance.getAmhsChannel().getAllowEIT().getEncodedType().contains(probe.getEncodeInformationType())) {
            final String err = String.format(E_UNSUPPORTED_EIT, probe.getEncodeInformationType());
            processProbeLog(probe, MtAttributes.D_ENCODED_INFORMATION_TYPES_UNSUPPORTED, err, session);
            return;
        }

        if (probe.getImplicitConversionProhibited() != null && probe.getImplicitConversionProhibited() == 1) {
            processProbeLog(probe, MtAttributes.D_IMPLICIT_CONVERSION_PROHIBITED, E_CONVERSION_PROHIBITED, session);
            return;
        }

        int limit = Config.instance.getAmhsChannel().getCharacterLimit();
        if (probe.getContentLength() != null && probe.getContentLength() > limit) {
            processProbeLog(probe, MtAttributes.D_CONTENT_TOO_LONG, E_CONTENT_TOO_LONG, session);
            return;
        }

        final int maximumAddressAllow = Config.instance.getAmhsChannel().getAddressLimit();
        if (probe.getEnvelopeRecipients().size() > maximumAddressAllow) {
            processProbeLog(probe, MtAttributes.D_TOO_MANY_RECIPIENTS, E_MAX_RECIPIENT_EXCEEED, session);
            return;
        }

        final AddressConvertResult converted = DSAConnection.getInstance().convertToAftnAddress(probe.getOrigin(), false);
        if (converted == null) {
            processProbeLog(probe, MtAttributes.D_INVALID_ARGUMENTS, E_CONVERT_ORIGINATOR_FAIL, session);
            return;
        }

        /*
        final List<Recipient> deliverableList = new ArrayList<>();
        for (Recipient add : probe.getEnvelopeRecipients()) {
            if (!add.allowDeliveriedReport()) {
                continue;
            }
            deliverableList.add(add);
        }
         */
        
        //MTMessage mtmessag = new 
        int mta_report_request=0 , report_request=0;
        String add = "";
        // Kiem tra cac dia chi trong Message co phan kenh duoc khong
        List<Recipient> _envelopeRecipients = probe.getEnvelopeRecipients();
        for (int j = 0; j < _envelopeRecipients.size() ; j ++) {
            
            mta_report_request =  _envelopeRecipients.get(j).getMtaReportRequest();
            report_request = _envelopeRecipients.get(j).getReportRequest();
            add = _envelopeRecipients.get(j).getAddress();
            
            //logger.info("Test probe Address {} " , add +  ", Mta report request {" + Integer.toString(i) + "}, Report Request {" + Integer.toString(ii) +"}");
            
            boolean pk = testPhankenhProbeAftn(add);
            _envelopeRecipients.get(j).setPhankenhduocaftn(pk);
            // NDR
            // DR
            
        }
        
        /*
        for (int j = 0; j < _envelopeRecipients.size() ; j ++) {
            
            mta_report_request =  _envelopeRecipients.get(j).getMtaReportRequest();
            report_request = _envelopeRecipients.get(j).getReportRequest();
            add = _envelopeRecipients.get(j).getAddress();
            //logger.info("Test probe Address {} " , add +  ", Mta report request {" + Integer.toString(i) + "}, Report Request {" + Integer.toString(ii) +"}");
            
            boolean pk = _envelopeRecipients.get(j).isPhankenhduocaftn();
            
            // NDR
            // DR
            //if (i == 1 && ii == 1 && !pk) {
            //    logger.info("Create Report NDR for {}", add);
            //    DeliverReport report = probe.createNonDeliverReport();                 // Tao NDR
            //    send(report, session);
            //}
            
        }
         */
        /*
        Delivery Report
        Subject ID: [/PRMD=VIETNAM/ADMD=ICAO/C=XX/;vietnam.am.1358801-240619.150331]
        It wasnot successfully sent to: /CN=VVTSCCCC/OU=VVTS/O=VVTS/PRMD=VIETNAM/ADMD=ICAO/C=XX/
        Non-delivery reason code: 1
        Non-delivery diagnostics code: 0
        Suplement info: null
        */
        
        
        logger.info("Create Report NDR");
        DeliverReport report = probe.createNonDeliverReport(MtAttributes.RS_UNABLE_TO_TRANSFER, MtAttributes.D_UNRECOGNISED_OR_NAME, "Report From Gateway");
        send(report, session);
//        message.returnNonDeliverReport(connection,    
        
        
        logger.info("Create Report DR");
        report = probe.createDeliverReport();                 // Tao DR
        send(report, session);                  // Gui DR
        //probe.returnDeliverReport(connection, deliverableList);

        final StringBuilder remarkBuilder = new StringBuilder("Converted");

        final MessageConversionLog log = probe.createMessageConversionLog();
        log.setStatus(ConvertResult.SUCCESS);
        log.setRemark(remarkBuilder.toString());
        messageDao.save(log);

    }

    private String processError(ReceiveMessage message, int error, Session session) throws X400APIException {

        String err = "";
        int nonDeliveryReason = 0;
        int nonDeliveryDiagnosticCode = 0;
        String suplementInfo = null;

        switch (error) {
            case MtAttributes.E_DISTRIBUTION_PROHIBITED:
                err = E_DLEXPANSION_PROHIBITED;
                nonDeliveryReason = MtAttributes.RS_UNABLE_TO_TRANSFER;
                nonDeliveryDiagnosticCode = MtAttributes.D_DL_EXPANSION_PROHIBITED;
                break;

            case MtAttributes.E_MESSAGE_UNSUPPORT_CONTENT_TYPE:
                nonDeliveryReason = MtAttributes.RS_UNABLE_TO_TRANSFER;
                nonDeliveryDiagnosticCode = MtAttributes.D_CONTENT_TYPE_NOT_SUPPORTED;
                err = String.format(E_UNSUPPORT_CONTENT_TYPE, message.getContentType());
                break;

            case MtAttributes.E_MESSAGE_UNSUPPORT_ENCODED_INFORMATION_TYPE:
                nonDeliveryReason = MtAttributes.RS_UNABLE_TO_TRANSFER;
                nonDeliveryDiagnosticCode = MtAttributes.D_ENCODED_INFORMATION_TYPES_UNSUPPORTED;
                final String oeit = message.getOriginEncodeInformationType();
                err = (oeit == null || oeit.isEmpty()) ? E_EMPTY_EIT : String.format(E_UNSUPPORTED_EIT, oeit);
                break;

            case MtAttributes.E_ATS_MESSAGE_HEADER_ERROR:
                nonDeliveryReason = MtAttributes.RS_UNABLE_TO_TRANSFER;
                nonDeliveryDiagnosticCode = MtAttributes.D_CONTENT_SYNTAX_ERROR;
                err = E_ATS_HEADER;
                break;

            case MtAttributes.E_BODYPART_TYPE_NOT_SUPPORT:
                err = E_UNSUPPORT_BODYPART_TYPE;
                suplementInfo = E_UNSUPPORT_BODYPART_TYPE;
                nonDeliveryReason = MtAttributes.RS_UNABLE_TO_TRANSFER;
                nonDeliveryDiagnosticCode = MtAttributes.D_CONTENT_SYNTAX_ERROR;
                break;

            case MtAttributes.E_CONTENT_CONTAIN_INVALID_CHARACTER:
                err = E_INVALID_CHARACTER;
                nonDeliveryReason = MtAttributes.RS_CONVERSION_NOT_PERFORM;
                nonDeliveryDiagnosticCode = MtAttributes.D_ALPHABETIC_SYMBOLS_LOSS;
                break;

            case MtAttributes.E_CONTENT_CONTAIN_INVALID_INFO:
                err = E_INVALID_CHARACTER;
                nonDeliveryReason = MtAttributes.RS_CONVERSION_NOT_PERFORM;
                nonDeliveryDiagnosticCode = MtAttributes.D_MULTIPLE_INFORMATION_LOSS;
                break;

            case MtAttributes.E_CONTENT_CONTAIN_INVALID_SYMBOLS:
                err = E_INVALID_CHARACTER;
                nonDeliveryReason = MtAttributes.RS_CONVERSION_NOT_PERFORM;
                nonDeliveryDiagnosticCode = MtAttributes.D_PUNCTUATION_SYMBOLS_LOSS;
                break;

            case MtAttributes.E_CONVERSION_PROHITED:
                err = E_CONVERSION_PROHIBITED;
                suplementInfo = "unable to convert to AFTN";
                nonDeliveryReason = MtAttributes.RS_CONVERSION_NOT_PERFORM;
                nonDeliveryDiagnosticCode = MtAttributes.D_IMPLICIT_CONVERSION_PROHIBITED;
                break;

            case MtAttributes.E_EXCEED_MAXIMUM_OF_RECIPIENTS:
                err = E_MAX_RECIPIENT_EXCEEED;
                suplementInfo = err;
                nonDeliveryReason = MtAttributes.RS_UNABLE_TO_TRANSFER;
                nonDeliveryDiagnosticCode = MtAttributes.D_TOO_MANY_RECIPIENTS;
                break;

            case MtAttributes.E_LINE_TOO_LONG:
                err = E_LINE_TOO_LONG;
                nonDeliveryReason = MtAttributes.RS_CONVERSION_NOT_PERFORM;
                nonDeliveryDiagnosticCode = MtAttributes.D_LINE_TOO_LONG;
                break;

            case MtAttributes.E_MESSAGE_TO_LONG:
                err = E_CONTENT_TOO_LONG;
                suplementInfo = E_CONTENT_TOO_LONG;
                nonDeliveryReason = MtAttributes.RS_UNABLE_TO_TRANSFER;
                nonDeliveryDiagnosticCode = MtAttributes.D_CONTENT_TOO_LONG;
                break;

            case MtAttributes.E_MULTI_BODYPART:
                err = E_MULTI_BODYPART;
                suplementInfo = E_MULTI_BODYPART;
                nonDeliveryReason = MtAttributes.RS_UNABLE_TO_TRANSFER;
                nonDeliveryDiagnosticCode = MtAttributes.D_CONTENT_SYNTAX_ERROR;
                break;

            case MtAttributes.E_INVALID_CHARACTERSET:
                err = E_UNSUPPORT_BODYPART_TYPE;
                suplementInfo = E_UNSUPPORT_BODYPART_TYPE;
                nonDeliveryReason = MtAttributes.RS_UNABLE_TO_TRANSFER;
                nonDeliveryDiagnosticCode = MtAttributes.D_CONTENT_SYNTAX_ERROR;
                break;
        }

        // DeliverReport report = message.createNonDeliverReport(error, nonDeliveryReason, suplementInfo);
        DeliverReport report = message.createNonDeliverReport(nonDeliveryReason, nonDeliveryDiagnosticCode, suplementInfo);
        send(report, session);
//        message.returnNonDeliverReport(connection, nonDeliveryReason, nonDeliveryDiagnosticCode, suplementInfo);
        // logger.error("%s. Conversion discarded", err);
        return String.format("%s. Conversion discarded", err);
    }

    private List<String> processContent(String content) {
        return StringUtil.splitContent(StringUtil.correctLineLength(StringUtil.autoCorrectCharacter(content)));
    }

    private int validate(ReceiveMessage ipm) {

        if (ipm.getDlExpansionProhibited() && ipm.getNumberOfDLAddress() > 0) {
            return MtAttributes.E_DISTRIBUTION_PROHIBITED;
        }

        // Checking content type
        if (MtAttributes.BI_CONTENT_TYPE_IPM_1988 != ipm.getContentType()) {
            return MtAttributes.E_MESSAGE_UNSUPPORT_CONTENT_TYPE;
        }

        // Checking encoded-information-type
        final String oeit = ipm.getOriginEncodeInformationType();
        if (oeit == null || oeit.isEmpty()) {
            return MtAttributes.E_MESSAGE_UNSUPPORT_ENCODED_INFORMATION_TYPE;
        } else {
            EncodedInformationType characterSet = Config.instance.getAmhsChannel().getAllowEIT();
            String[] encoders = oeit.split(" ");
            List<String> validSet = characterSet.getEncodedType();
            for (String encode : encoders) {
                if (validSet.contains(encode)) {
                    continue;
                }
                return MtAttributes.E_MESSAGE_UNSUPPORT_ENCODED_INFORMATION_TYPE;
            }
        }

        if (ipm.getImplicitConversionProhibited()) {
            return MtAttributes.E_CONVERSION_PROHITED;
        }

        if (!AmhsValidator.validatePriority(ipm.getAtsPriority())
                || !AmhsValidator.validateOHI(ipm.getAtsOhi(), ipm.getAtsPriority()) // Case 402
                || !AmhsValidator.validateFilingTime(ipm.getAtsFilingTime())) {
            return MtAttributes.E_ATS_MESSAGE_HEADER_ERROR;
        }

        int error = validateBodyPart(ipm);
        if (error != MtAttributes.E_NO_ERROR) {
            return error;
        }

        if (ipm.getContent().length() > Config.instance.getAmhsChannel().getCharacterLimit()) {
            return MtAttributes.E_MESSAGE_TO_LONG;
        }

        if (!validateCharacterSet(ipm.getContent())) {
            return MtAttributes.E_INVALID_CHARACTERSET;
        }

        if (ipm.getEnvelopeRecipients().size() > Config.instance.getAmhsChannel().getAddressLimit()) {
            return MtAttributes.E_EXCEED_MAXIMUM_OF_RECIPIENTS;
        }

        if (ipm.getConversionWithLossProhibited()) {

            // Case 408, Process 4.5.2.1.6 c), d) and e)
            error = AmhsValidator.validateCharacter(ipm.getContent());
            if (error != MtAttributes.E_NO_ERROR) {
                return error;
            }

            // Case 407, Process 4.5.2.1.6 a)
            if (!AmhsValidator.validateContentLine(ipm.getContent())) {
                return MtAttributes.E_LINE_TOO_LONG;
            }
        }

        return MtAttributes.E_NO_ERROR;
    }

    private int validateBodyPart(ReceiveMessage mtMessage) {

        if (mtMessage.getNumberOfAttachment() > 1) {
            return MtAttributes.E_MULTI_BODYPART; // Multi body-part error
        }
        // BodyPart bodyPart = new BodyPart();
        if (mtMessage.getBodyPartType() == X400_att.X400_T_IA5TEXT) {
            return MtAttributes.E_NO_ERROR;
        }
        if (mtMessage.getBodyPartType() == X400_att.X400_T_GENERAL_TEXT) {
            final String charset = mtMessage.getBodyPartCharacterSet();
            if (charset == null || charset.isEmpty()) {
                return MtAttributes.E_BODYPART_TYPE_NOT_SUPPORT;
            }
            String[] charsets = charset.split(" ");
            for (String c : charsets) {
                if (!Config.instance.getAmhsChannel().getAllowedCharSet().contains(c)) {
                    return MtAttributes.E_BODYPART_TYPE_NOT_SUPPORT;
                }
            }
            return MtAttributes.E_NO_ERROR;
        }
        return MtAttributes.E_BODYPART_TYPE_NOT_SUPPORT;
    }

    private boolean validateCharacterSet(String content) {
        int length = content.length();
        for (int index = 0; index < length; index++) {
            if (content.charAt(index) < 127) {
                continue;
            }
            return false;
        }
        return true;
    }

    private void reportAsymmetricAddress(ReceiveMessage message, List<Recipient> addresses, Session session) throws X400APIException {

        if (!Config.instance.isReportAsymAddress() || addresses.isEmpty()) {
            return;
        }

        // make content
        final StringBuilder builder = new StringBuilder();
        builder.append("Delivery of message \r\n");
        builder.append("Type: IPM\r\n");
        builder.append(String.format("Message ID: %s\r\n", message.getMessageId()));
        builder.append(String.format("Content ID: %s\r\n", message.getContentId()));
        builder.append(String.format("From: %s\r\n", message.getOrAddress()));
        builder.append(String.format("To:\r\n"));
        for (Recipient add : message.getEnvelopeRecipients()) {
            builder.append(String.format("     %s\r\n", add.getAddress()));
        }

        builder.append("\r\n\r\n");
        builder.append("Asymmetric address conversion detected on following addresses:\r\n\r\n");
        for (Recipient tmp : addresses) {
            builder.append(String.format("MF-Address: %s\r\n", tmp.getAddress()));
            // builder.append(String.format("AF-Address: %s\r\n", tmp.getAftnAddress()));
            // builder.append(String.format("Backwarding MF-Address: %s\r\n", tmp.getBackwardAddress()));
            builder.append("\r\n");
        }

        report2Controller(builder.toString(), session);

        logger.debug("Reported asymmetric address conversion message to controller");
    }

    private void report2Controller(String content, Session session) throws X400APIException {

        final List<StringValue> controllers = Config.instance.getControllers();
        final RecipientConfig config = Config.instance.getReportRecipient();
        final String origin = Config.instance.getAtnAddress();

        if (controllers == null
                || controllers.isEmpty()
                || origin == null
                || origin.isEmpty()) {
            logger.error("Report to control position fail. Invalid controllers address or gateway address");
            return;
        }

        // Delivery
        final DeliverMessage ipmMessage = new DeliverMessage();
        ipmMessage.setSubject("AMHS service information");
        ipmMessage.setAtsMessage(true);
        ipmMessage.setOriginator(origin);
        ipmMessage.setPriority(X400_att.X400_PRIORITY_NORMAL);
        ipmMessage.setContent(content);
        ipmMessage.setAtsPriority("FF");
        ipmMessage.setAtsFilingTime(filingTimeFormat.format(new Date()));
        ipmMessage.setAtsContent(content);

        logger.info("Report to controller: ");
        for (StringValue controller : controllers) {
            ipmMessage.addRecip(new Recipient(controller.getValue(), config));
            logger.info("CTRLER: {}", controller.getValue());
        }

        send(ipmMessage, session);
//        ipmMessage.deliver(connection);
    }

    private void processProbeLog(ReceiveProbe probe, Integer undeliverDianogsticReason, String err, Session session) throws X400APIException, SQLException {

        // Return non-delivery-report
//        probe.returnNonDeliverReport(connection, MtAttributes.RS_UNABLE_TO_TRANSFER, undeliverDianogsticReason, null, null);
        DeliverReport report = probe.createNonDeliverReport(MtAttributes.RS_UNABLE_TO_TRANSFER, undeliverDianogsticReason, null);
        send(report, session);

        // Append log to DB
        final MessageConversionLog probleLog = probe.createMessageConversionLog();
        probleLog.setStatus(ConvertResult.FAIL);
        probleLog.setRemark(err);
        messageDao.save(probleLog);

        // Append log to file
        logger.error(err);
    }   
    
    private void processReportLog(ReceiveReport report, String message, Session session) throws X400APIException, SQLException {
        if (Config.instance.isReportWrongReportType()) {
            this.report2Controller(String.format("%s\r\n\r\n%s", message, report.getReportContent()), session);
        }

        // Create log record
        final MessageConversionLog reportLog = report.createMessageConversionLog();
        reportLog.setStatus(ConvertResult.FAIL);
        reportLog.setRemark(message);
        messageDao.save(reportLog);

        // Append log to file
        logger.warn(message);
    }

    private void send(DeliverReport message, Session sessionss) throws X400APIException {

        if (message.getRecips() == null || message.getRecips().isEmpty()) {
            logger.info("No address to delivery report -> Do not Send anything");
            return;
        }

        final MTMessage mtMessage = new MTMessage();
        int result = X400mt.x400_mt_msgnew(sessionss, X400_att.X400_MSG_REPORT, mtMessage);
        if (result != X400_att.X400_E_NOERROR) {
            logger.error("Create report fail ({})", result);
            throw new X400APIException(String.format("Create report fail (%s)", result));
        }

        for (RptRecipient rpt : message.getRecips()) {
            logger.info("Report address : {} MtaReportRequest: {} ReportRequest : {} RN : {}",
                    new Object[]{rpt.getAddress(),
                        rpt.getMtaReportRequest(),
                        rpt.getReportRequest(),
                        (rpt.getReceiptNotification()!=null)?rpt.getReceiptNotification():"null" });
        }

        message.build(mtMessage);

        result = X400mt.x400_mt_msgsend(mtMessage);
        logger.error("Delete report to {} ({}) ", message.getOriginator(), result);

        if (result != X400_att.X400_E_NOERROR) {
            // logger.error(">> RPT: %s (%s)", message.getOriginator(), result);
            // final String log = String.format("Delivery report failed (%s)", message.getOriginator());
            // throw new X400APIException(log);
        }

        result = X400mt.x400_mt_msgdel(mtMessage);
        logger.error("Delete report after sending ({})", result);
    }

    private void send(DeliverMessage message, Session sessionss) throws X400APIException {
        final MTMessage mtMessage = new MTMessage();
        int result = X400mt.x400_mt_msgnew(sessionss, X400_att.X400_MSG_MESSAGE, mtMessage);
        if (result != X400_att.X400_E_NOERROR) {
            logger.error("Create message fail ({})", result);
            throw new X400APIException(String.format("Create message fail (%s)", result));
        }

        message.build(mtMessage);
        result = X400mt.x400_mt_msgsend(mtMessage);
        if (result != X400_att.X400_E_NOERROR) {
            logger.error("Sending IPM message fail ({})", result);
            // throw new X400APIException(String.format("Delivery message failed (%s)", result));
        }

        result = X400mt.x400_mt_msgdel(mtMessage);
        logger.error("Delete report after sending ({})", result);

//        result = X400mt.x400_mt_msgsend(mtMessage);
//        if (result != X400_att.X400_E_NOERROR) {
//            logger.error(String.format("Delivery report failed to address %s (%s)", message.getOriginator(), result));
//            // final String log = String.format("Delivery report failed (%s)", message.getOriginator());
//            // throw new X400APIException(log);
//        }
    }
}
