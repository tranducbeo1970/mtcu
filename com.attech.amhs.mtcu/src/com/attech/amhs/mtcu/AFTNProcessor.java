/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu;

import com.attech.amhs.mtcu.common.MtCommon;
import com.attech.amhs.mtcu.isode.AddressConvertResult;
import com.attech.amhs.mtcu.database.dao.AFTNMessageDao;
import com.attech.amhs.mtcu.database.dao.GatewayOutDao;
import com.attech.amhs.mtcu.database.dao.MessageDao;
import com.attech.amhs.mtcu.config.Config;
import com.attech.amhs.mtcu.config.StringValue;
import com.attech.amhs.mtcu.common.PriorityUtil;
import com.attech.amhs.mtcu.aftn.AckInfo;
import com.attech.amhs.mtcu.aftn.Message;
import com.attech.amhs.mtcu.aftn.Type;
import static com.attech.amhs.mtcu.aftn.Type.ACKNOWLEDGE;
import static com.attech.amhs.mtcu.aftn.Type.UNKNOWN;
import com.attech.amhs.mtcu.aftn.UnknownAddressInfo;
import com.attech.amhs.mtcu.common.InvalidOperatingException;
import com.attech.amhs.mtcu.config.AFTNChannelConfig;
import com.attech.amhs.mtcu.config.DefaultMessageValue;
import com.attech.amhs.mtcu.config.RecipientConfig;
import com.attech.amhs.mtcu.database.Connection;
import com.attech.amhs.mtcu.database.dao.GatewayInDao;
import com.attech.amhs.mtcu.database.entity.AddressConversionLog;
import com.attech.amhs.mtcu.database.entity.AftnMessage;
import com.attech.amhs.mtcu.database.entity.GatewayIn;
import com.attech.amhs.mtcu.database.entity.GatewayInFails;
import com.attech.amhs.mtcu.database.entity.GatewayOut;
import com.attech.amhs.mtcu.database.entity.MessageConversionLog;
import com.attech.amhs.mtcu.database.enums.ConvertResult;
import com.attech.amhs.mtcu.database.enums.MessageCategory;
import com.attech.amhs.mtcu.isode.DSAConnection;
import com.attech.amhs.mtcu.isode.DeliverIpnMessage;
import com.attech.amhs.mtcu.isode.DeliverMessage;
import com.attech.amhs.mtcu.isode.DeliverReport;
import com.attech.amhs.mtcu.isode.MTConnection1;
import com.attech.amhs.mtcu.isode.Recipient;
import com.attech.amhs.mtcu.isode.RptRecipient;
import com.isode.dsapi.DSAPIException;
import com.isode.x400.highlevel.X400APIException;
import com.isode.x400api.X400_att;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ANDH
 */
public class AFTNProcessor {

    private final String MESSAGE_SUBJECT = "AFTN service information";
    private final String ERROR_NO_CONTROL_POSITION = "Report fail. No control position found at configuration";
    // private final String REPORT_SUCCESS = "Reported AFTN message to control position due to %s";
    private final String ORIGINATOR_ERROR = "Originator address %s cannot be converted";
    // private final String UNKNOWN_ADDRESS_GENERATED = "Unknown addressee indicator message has been generated for %s";
    private final String CONVERT_RECIPIENT_ERROR = "System cannot convert recipient address: %s";
    private final String CONVERT_RECIPIENT_UNKNOW_ERROR = "The following AFTN address cannot be converted to AMHS address:\r\n%s";
    private final String CONVERT_SUCESSFULLY = "[{} {}] is converted successfully";
    private final String SUBJECT_MESSAGE_CANNOT_BE_FOUND = "Subject message cannot be found";
    private final String SUBJECT_MESSAGE_IS_MORE_THAN_ONE = "Subject message found more than one";
    
    private final String UNKNOWN_ADDRESS = "Unknown recipient address";

    private static final Logger logger = LoggerFactory.getLogger(AFTNProcessor.class);

    private final int recordLimitNum;
    private final int interval;
    private final long retryInterval;
    
    private final String channelName;
    private final String logFile;
    private final List<StringValue> controllers;
    private final String origin;
    private final MessageDao messageDao = new MessageDao();
    private final GatewayOutDao gatewayoutDao = new GatewayOutDao();
    private final GatewayInDao gatewayInDao = new GatewayInDao();
    private final AFTNMessageDao aftnMessageDao = new AFTNMessageDao();

    /**
     * Constructor
     *
     * @param config
     */
    public AFTNProcessor(AFTNChannelConfig config) {

        this.controllers = Config.instance.getControllers();
        this.origin = Config.instance.getAtnAddress();
        this.channelName = Config.instance.getAftnChannel().getChannelName();
        this.logFile = Config.instance.getAftnChannel().getLogFile();

        this.interval = config.getIntervalTime();
        this.retryInterval = config.getRetryInterval();
        this.recordLimitNum = config.getMaxRecordFetching();
        // this.mtConnection = new MTConnection1(channelName, logFile);
        Connection.setGwinName(config.getGwinName());

        // addressConvertor = new DSAConnection();
    }

    public void process() {
        try {

            List<GatewayIn> gatewayIn;

            while (true) {
                logger.debug("Getting aftn messages");
                gatewayIn = gatewayInDao.getGatewayInMessages(recordLimitNum);
                if (gatewayIn == null || gatewayIn.isEmpty()) {
                    keepWaiting(interval);
                    continue;
                }

                while (gatewayIn != null && !gatewayIn.isEmpty()) {
                    for (GatewayIn gw : gatewayIn) {
                        try {
                            process(gw);
                        } catch (DSAPIException ex) {
                            logger.error("ERROR FOUND!", ex);
                            this.reportFailMessage(gw, ex.getMessage());
                            gatewayInDao.remove(gw);
                        } catch (SQLException ex) {
                            logger.error("ERROR FOUND!", ex);
                            throw ex;
                        } catch (X400APIException ex) {
                            if (ex.getNativeErrorCode() == 4 && ex.getMessage().equalsIgnoreCase("Connect fail (Internal Error)")) {
                                // Connection error. Sleep then retry
                                logger.error(ex.getMessage());
                                keepWaiting(retryInterval);
                                break;
                            } else {
                                logger.error("Deliver message fail: {}", ex.getMessage());
                                this.reportFailMessage(gw, ex.getMessage());
                                gatewayInDao.remove(gw);
                            }
                        } catch (Exception ex) {
                            logger.error("ERROR FOUND!", ex);
                            this.reportFailMessage(gw, ex.getMessage());
                            gatewayInDao.remove(gw);
                        }
                    }
                    gatewayIn.clear();
                    gatewayIn = gatewayInDao.getGatewayInMessages(recordLimitNum);
                }
            }
        } catch (SQLException ex) {
            logger.error("ERROR FOUND!", ex);
            logger.warn("Retry in {}", retryInterval);
            keepWaiting(retryInterval);
        } catch (Exception ex) {
            logger.error("ERROR FOUND!", ex);
        }
    }

    public void process(GatewayIn gatewayIn) throws SQLException, X400APIException, DSAPIException, Exception {

        try (MTConnection1 mtConnection = new MTConnection1(channelName, logFile)) {
            logger.info("Receive AFTN message\n"
                    + "--------------------------------------------------------------------\n{}",
                    gatewayIn.getText());

            logger.info("Connected to {}", channelName);
            mtConnection.connect();

            String err;
            if (!validate(gatewayIn)) {
                // Output to error message tables
                err = "AFTN message format is invalid";
                this.log(gatewayIn, ConvertResult.FAIL, err);
                this.reportFailMessage(gatewayIn, err);
                this.reportControlPosition(gatewayIn.getText(), err, mtConnection);
                gatewayInDao.remove(gatewayIn);
                logger.error(err);
                return;
            }

            // Parsing message
            final Message message = new Message(gatewayIn.getText());
            message.setEnvelopeAddress(gatewayIn.getAddress());
            if (!message.isValid()) {
                err = message.getErrorMessage();
                this.log(gatewayIn, ConvertResult.FAIL, err);
                this.reportControlPosition(gatewayIn.getText(), err, mtConnection);
                gatewayInDao.remove(gatewayIn);
                logger.error(err);
                return;
            }

            final Type type = message.getMessageType();

            switch (type) {
                case ACKNOWLEDGE:
                    logger.info("ACKNOWLEDGE found ({})", message.getOriginId());
                    processAcknowledgeMessage(message, mtConnection);
                    break;

                case UNKNOWN:
                    logger.info("UNKNOWN message found ({}) ", message.getOriginId());
                    processAddressUnknownIndicator(message, mtConnection);
                    break;

                default:
                    logger.info("GENERAL message found ({})", message.getOriginId());
                    /*-----------------------------------------------
                     Xu ly dien van nhan duoc tu gwin
                    
                    -----------------------------------------------*/
                    processNormalMessage(message, mtConnection);
                    break;
            }

            gatewayInDao.remove(gatewayIn);

        } catch (Exception ex) {
            throw ex;
        } finally {
        }
    }

    // PRIVATE METHODS
    private void reportFailMessage(GatewayIn gatewayIn, String errorMessage) throws SQLException {
        GatewayInFails failMessage = new GatewayInFails(gatewayIn);
        failMessage.setErrorDetail(errorMessage);
        messageDao.save(failMessage);

    }

    /*--------------------------------------------------
    
    Xu ly dien van AFTN THUONG
    
    --------------------------------------------------*/
    private void processNormalMessage(Message message, MTConnection1 mtConnection)  throws DSAPIException, X400APIException, SQLException, InvalidOperatingException {
        final DefaultMessageValue config = Config.instance.getAftnChannel().getDefaultMessageValue();   
        
        final int priority = PriorityUtil.toAMHSPriority(message.getPriority());
        String errorMessage = "";

        // Convert origin Addresses
        final String aftnOrigin = message.getOriginator();
        AddressConvertResult result = DSAConnection.getInstance().convertToAmhsAddress(aftnOrigin, true);

        if (result == null) {
            errorMessage = String.format(ORIGINATOR_ERROR, aftnOrigin);
            this.log(message, ConvertResult.FAIL, errorMessage);
            this.reportControlPosition(message.getMessage(), errorMessage, mtConnection);
            logger.error(errorMessage);
            throw new InvalidOperatingException(errorMessage);
        }
        final String amhsOrigin = result.getConvertedAddress();
        logger.debug("Originator {}:{}", aftnOrigin, amhsOrigin);

        final List<String> unknownAddresses_str_list = new ArrayList<>();
        logger.debug("Convert recipient");

        final List<String> recipients_basic = new ArrayList<>();
        final List<String> recipients_extend = new ArrayList<>();

        
        int ext = config.getAtsExtended();
        
        /*--------------------------------------------
    
        
        Lay dia chi trong dien van AFTN 
        Chuyen doi sang AMHS
        
        --------------------------------------------*/
        for (String address : message.getEnvelopeMessages()) {
            if (address.isEmpty()) {
                continue;
            }

            
            //result = DSAConnection.getInstance().convertToAmhsAddress(address, false);
            result = DSAConnection.getInstance().convertToAmhsAddress(address, true);
            if (result == null) {
                logger.warn(">{}: FAIL", address);
                unknownAddresses_str_list.add(address);
                continue;
            }
            
            
            
            // DUC 20/10/2024-------------------------------------------------------------------------------------------------
            String s = (result.isIhe())?"YES":"NO";
            String s1 = (result.isDirect())?"YES":"NO";
            logger.info("Convert " + address + " to " + result.getConvertedAddress() + ", IHE=" + s + " DIRECT=" + s1 + " Support EXTENED " + Integer.toString(ext));
                        
            if (ext == 1) {

                // Them cac dia chi vao string list
                if (result.isIhe() && result.isDirect()) {
                    recipients_extend.add(result.getConvertedAddress());
                } else {
                    recipients_basic.add(result.getConvertedAddress());
                }
            } else {
                recipients_basic.add(result.getConvertedAddress());
            }
            
            logger.debug(">{}:{}", address, result.getConvertedAddress());
        }
        

        // Generate SVC UNKNOWN ADS INDICATOR
        if (!unknownAddresses_str_list.isEmpty()) {
            logger.debug("Generating unknown address");
            final String unknowAddressStr = buildAddress(0, unknownAddresses_str_list);
            this.generateSvcUnknownAddressIndicator(message, unknownAddresses_str_list);
            this.reportControlPosition(message.getMessage(), String.format(CONVERT_RECIPIENT_UNKNOW_ERROR, unknowAddressStr), mtConnection);
        }

        
        
        if (!recipients_basic.isEmpty()) {
            Send(false, message, mtConnection, priority, amhsOrigin, recipients_basic, unknownAddresses_str_list);

            // Logging and return
            if (recipients_basic.isEmpty()) {
                final String error = String.format(CONVERT_RECIPIENT_ERROR, String.join(", ", message.getEnvelopeMessages()));
                this.log(message, ConvertResult.FAIL, error);
                logger.error(error);
                // return;
                throw new InvalidOperatingException(error);
            }
        }
        
        if (ext == 1) {
            if (!recipients_extend.isEmpty()) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    java.util.logging.Logger.getLogger(AFTNProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
                Send(true, message, mtConnection, priority, amhsOrigin, recipients_extend, unknownAddresses_str_list);
                // Logging and return
                if (recipients_extend.isEmpty()) {
                    final String error = String.format(CONVERT_RECIPIENT_ERROR, String.join(", ", message.getEnvelopeMessages()));
                    this.log(message, ConvertResult.FAIL, error);
                    logger.error(error);
                    // return;
                    throw new InvalidOperatingException(error);
                }
            }
        }

        
        
        
        // ******************************** BAT DAU TAO MESSSAGE DE SEND ****************************************
        
        
        // DUC 26/10/2024 
        // Tao dien van phat di 
        // DeliverMessage class de tao dien 
        // Phat tat ca dien 
/*        
        final DeliverMessage deliveryMessage = new DeliverMessage();
        deliveryMessage.setPriority(priority);
        deliveryMessage.setOriginator(amhsOrigin);
        deliveryMessage.setAftnMessage(message);
        

        final RecipientConfig recipientCfg = (message.getMessageType() == Type.ACKNOWLEDGE || message.getMessageType() == Type.UNKNOWN)
                ? Config.instance.getReportRecipient()
                : (message.getPriority().equalsIgnoreCase("SS") ? Config.instance.getPriorityRecipient() : Config.instance.getConvertRecipient());

        
        // Them cac dia chi 
        for (String recipient : recipients_basic) {
            deliveryMessage.addRecip(new Recipient(recipient, recipientCfg));
        }

        
        // SET DIEN VAN LOAI GI
        // Chua can
        //deliveryMessage.setExtended(true);
        
        //
        //  
        // Build dien van va phat di trong nay
        // 
        //
        
        mtConnection.send(deliveryMessage);         // Build message here
        logger.debug("Delivered");
        
        
        //
        //  Da phat xong
        //

        
        final String remark = unknownAddresses_str_list.isEmpty()
                ? "Converted"
                : String.format("Some recipient cannot be converted: %s ", String.join(", ", unknownAddresses_str_list));

        final Date convertedTime = new Date();
        final MessageConversionLog aftnLog = message.getMessageConversionLog();

        switch (message.getMessageType()) {
            case ACKNOWLEDGE:
                aftnLog.setCategory(MessageCategory.GENERAL);
                break;
            case UNKNOWN:
                aftnLog.setCategory(MessageCategory.UNKNOWN);
                break;
            default:
                aftnLog.setCategory(MessageCategory.GENERAL);
                break;
        }

        aftnLog.setStatus(ConvertResult.SUCCESS);
        aftnLog.setRemark(remark);
        aftnLog.setConvertedDate(convertedTime);

        final MessageConversionLog amhsLog = deliveryMessage.createMessageConversionLog();
        amhsLog.setStatus(ConvertResult.SUCCESS);
        amhsLog.setConvertedDate(convertedTime);
        aftnLog.addChild(amhsLog);

        messageDao.save(aftnLog);
        logger.info(CONVERT_SUCESSFULLY, message.getFilingTime(), message.getOriginator());
        
  */      
        
        
        
    }
    /*------------------------------------------------

    
    ------------------------------------------------*/
    private void Send(boolean extend,Message message, MTConnection1 mtConnection,int priority,String amhsOrigin,List<String> recipients_address,List<String> unknownAddresses_str_list) {
        
        final DeliverMessage deliveryMessage = new DeliverMessage();
        deliveryMessage.setPriority(priority);
        deliveryMessage.setOriginator(amhsOrigin);
        deliveryMessage.setAftnMessage(message);
        

        final RecipientConfig recipientCfg = (message.getMessageType() == Type.ACKNOWLEDGE || message.getMessageType() == Type.UNKNOWN)
                ? Config.instance.getReportRecipient()
                : (message.getPriority().equalsIgnoreCase("SS") ? Config.instance.getPriorityRecipient() : Config.instance.getConvertRecipient());

        
        // Them cac dia chi 
        for (String recipient : recipients_address) {
            deliveryMessage.addRecip(new Recipient(recipient, recipientCfg));
        }

        
        try {
            // SET DIEN VAN LOAI GI
            // Chua can
            
            deliveryMessage.setExtended(extend);
            
            //
            //
            // Build dien van va phat di trong nay
            //
            //
            
            mtConnection.send(deliveryMessage);         // Build message here
        } catch (X400APIException ex) {
            java.util.logging.Logger.getLogger(AFTNProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        logger.debug("Delivered");
        
        
        //
        //  Da phat xong
        //

        
        final String remark = unknownAddresses_str_list.isEmpty()
                ? "Converted"
                : String.format("Some recipient cannot be converted: %s ", String.join(", ", unknownAddresses_str_list));

        final Date convertedTime = new Date();
        final MessageConversionLog aftnLog = message.getMessageConversionLog();

        switch (message.getMessageType()) {
            case ACKNOWLEDGE:
                aftnLog.setCategory(MessageCategory.GENERAL);
                break;
            case UNKNOWN:
                aftnLog.setCategory(MessageCategory.UNKNOWN);
                break;
            default:
                aftnLog.setCategory(MessageCategory.GENERAL);
                break;
        }

        aftnLog.setStatus(ConvertResult.SUCCESS);
        aftnLog.setRemark(remark);
        aftnLog.setConvertedDate(convertedTime);

        final MessageConversionLog amhsLog = deliveryMessage.createMessageConversionLog();
        amhsLog.setStatus(ConvertResult.SUCCESS);
        amhsLog.setConvertedDate(convertedTime);
        aftnLog.addChild(amhsLog);

        try {
            messageDao.save(aftnLog);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(AFTNProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        logger.info(CONVERT_SUCESSFULLY, message.getFilingTime(), message.getOriginator());
        
    }
            
    
    /*------------------------------------------------

    
    ------------------------------------------------*/
    private void processAcknowledgeMessage(Message message, MTConnection1 mtConnection) 
            throws DSAPIException, X400APIException, SQLException, InvalidOperatingException {

        if (Config.instance.isProcessAckAsIPM()) {
            processNormalMessage(message, mtConnection);
            return;
        }

        final AckInfo ackInfo = message.getAckInfo();
        final List<MessageConversionLog> refAftnMessages = messageDao.getReferenceMessage(ackInfo.getRefTime(), ackInfo.getRefOriginator());

        if (refAftnMessages == null || refAftnMessages.isEmpty() || refAftnMessages.get(0).getParents() == null) {
            processNormalMessage(message, mtConnection);
            reportControlPosition(message.getMessage(), SUBJECT_MESSAGE_CANNOT_BE_FOUND, mtConnection);
            return;
        }

        if (refAftnMessages.size() > 1) {
            processNormalMessage(message, mtConnection);
            reportControlPosition(message.getMessage(), SUBJECT_MESSAGE_IS_MORE_THAN_ONE, mtConnection);
            return;
        }

        final MessageConversionLog refAftnMessage = refAftnMessages.get(0);
        final MessageConversionLog refAmhsMessage = refAftnMessage.getParents();

        boolean isRequestIPN = false;
        String amhsOrigin = "";
        for (AddressConversionLog amhsAddress : refAmhsMessage.getAddressLogs()) {
            if (!amhsAddress.getAddress().contains(message.getOriginator())) {
                continue;
            }

            final Integer ipnRequest = amhsAddress.getNotifyRequest();
            if (ipnRequest == null || (ipnRequest != 3 && ipnRequest != 7)) {
                continue;
            }

            isRequestIPN = true;
            amhsOrigin = amhsAddress.getAddress();
            break;
        }

        if (amhsOrigin.isEmpty()) {
            processNormalMessage(message, mtConnection);
            return;
        }

        if (!isRequestIPN) {
            processNormalMessage(message, mtConnection);
            reportControlPosition(message.getMessage(), SUBJECT_MESSAGE_CANNOT_BE_FOUND, mtConnection);
            logger.warn("Subject message hasnot request IPN");
            return;
        }

        // Get amhs address
        final String receptTime = MtCommon.getReceiptTimeFromFilingTime(message.getFilingTime());
        final String recipient = refAmhsMessage.getOrigin();

        // Deliver IPN message
        final DeliverIpnMessage ipnMessage = new DeliverIpnMessage();
        ipnMessage.setSubjectIpmId(refAmhsMessage.getIpmId());
        // ipnMessage.setPriority(MtAttributes.PRIORITY_URGENT);
        ipnMessage.setPriority(refAmhsMessage.getPriority().getValue());
        ipnMessage.setOrigin(amhsOrigin);
        ipnMessage.setRecipient(new Recipient(recipient, Config.instance.getReportRecipient()));
        ipnMessage.setReceiptTime(receptTime);
        ipnMessage.setConversionProhibited(1);
        ipnMessage.setAckMode(0);
        mtConnection.send(ipnMessage);

        logger.debug("Delivery IPN to %s", refAmhsMessage.getOrigin());
        final Date convertedDate = new Date();

        // Build log
        final MessageConversionLog ipnLog = ipnMessage.createMessageConversionLog();
        ipnLog.setStatus(ConvertResult.SUCCESS);
        ipnLog.setConvertedDate(convertedDate);

        final MessageConversionLog aftnLog = message.getMessageConversionLog();
        aftnLog.setConvertedDate(convertedDate);
        aftnLog.setStatus(ConvertResult.SUCCESS);
        aftnLog.setRemark("Converted");
        aftnLog.addChild(ipnLog);
        messageDao.save(aftnLog);
        logger.info(CONVERT_SUCESSFULLY, message.getFilingTime(), message.getOriginator());
    }

    private void processAddressUnknownIndicator(Message message, MTConnection1 mtConnection) 
            throws DSAPIException, X400APIException, SQLException, InvalidOperatingException {

        final UnknownAddressInfo unknowAddressInfo = message.getUnknownAddressInfo();
        final MessageConversionLog refAftnMessage = getReferenceMessage(unknowAddressInfo);

        if (refAftnMessage != null && refAftnMessage.getParents() != null) {

            final RecipientConfig config = Config.instance.getReportRecipient();
            final Date covnertedDate = new Date();
            final MessageConversionLog aftnMessage = message.getMessageConversionLog();
            String errorMessage = "";

            aftnMessage.setConvertedDate(covnertedDate);

            final String aftnOrigin = message.getOriginator();

            final AddressConvertResult result = DSAConnection.getInstance().convertToAmhsAddress(aftnOrigin, true);
            if (result == null) {
                errorMessage = String.format(ORIGINATOR_ERROR, aftnOrigin);
                this.log(message, ConvertResult.FAIL, errorMessage);
                this.reportControlPosition(message.getMessage(), errorMessage, mtConnection);
                logger.error(errorMessage);
                throw new InvalidOperatingException(errorMessage);
                // return;
            }

            final String amhsOrigin = result.getConvertedAddress();
            final MessageConversionLog refAmhsMessage = refAftnMessage.getParents();
            final List<AddressConversionLog> deliverAsReportAddressList = new ArrayList<>();
            final List<String> deliverAsIpmAddressList = new ArrayList<>();
            final List<String> unknownAftnAddress = unknowAddressInfo.getUnknownAddresses();

            // Checking address
            final Set<AddressConversionLog> addressConversionLogs = refAmhsMessage.getAddressLogs();
            boolean isRequestReport = false;
            for (String unkAddress : unknownAftnAddress) {
                isRequestReport = false;

                for (AddressConversionLog addressLog : addressConversionLogs) {
                    if (!addressLog.getAddress().contains(unkAddress)) {
                        continue;
                    }

                    /*
                    if (MtAttributes.ORIGIN_NO_REPORT != addressLog.getReportRequest()
                            && MtAttributes.MTA_NON_DELIVERY_REPORT != addressLog.getMtaReportRequest()) { */
                    if (MtAttributes.ORIGIN_NO_REPORT != addressLog.getReportRequest()) {
                        deliverAsReportAddressList.add(addressLog);
                        isRequestReport = true;
                    }

                    break;
                }

                if (isRequestReport) {
                    continue;
                }

                deliverAsIpmAddressList.add(unkAddress);
            }

/*
            
DUC 03/11/2024
Tao bao cao dien van UNKNOWN            

*/            
            
            // Build NDR report
            if (!deliverAsReportAddressList.isEmpty()) {

                final DeliverReport report = new DeliverReport();
                report.setOriginator(refAmhsMessage.getOrigin());
                report.setMessageSubjectId(refAmhsMessage.getMessageId());
                report.setPriority(refAmhsMessage.getPriority().getValue());

                for (AddressConversionLog add : deliverAsReportAddressList) {
                    final RptRecipient rp = new RptRecipient();
                    rp.setAddress(add.getAddress());
                    rp.setNonDeliveryReason(MtAttributes.RS_UNABLE_TO_TRANSFER);
                    rp.setNonDeliveryDiagnosticCode(MtAttributes.D_UNRECOGNISED_OR_NAME);
                    rp.setSuplementInfo(UNKNOWN_ADDRESS);
                    rp.setReportRequest(add.getReportRequest() == null ? config.getReportRequest() : add.getReportRequest().intValue());
                    rp.setMtaReportRequest(add.getMtaReportRequest() == null ? config.getMtaReportRequest() : add.getMtaReportRequest().intValue());
                    report.add(rp);
                }
                mtConnection.send(report);

                logger.debug("Delivery UNK message as none-delivery-report to {}", refAmhsMessage.getOrigin());

                final MessageConversionLog reportConversionLog = report.createMessageConversionLog();
                reportConversionLog.setConvertedDate(covnertedDate);
                reportConversionLog.setStatus(ConvertResult.SUCCESS);
                aftnMessage.addChild(reportConversionLog);
            }

            if (!deliverAsIpmAddressList.isEmpty()) {
                unknowAddressInfo.setUnknownAddresses(deliverAsIpmAddressList);
                final String content = unknowAddressInfo.toString();
                final DeliverMessage ipmMessage = new DeliverMessage();
                ipmMessage.setAtsMessage(true);
                ipmMessage.setOriginator(amhsOrigin);
                ipmMessage.addRecip(new Recipient(refAmhsMessage.getOrigin(), config));
                ipmMessage.setPriority(PriorityUtil.toAMHSPriority(message.getPriority()));
                ipmMessage.setSubject(String.format("%s %s (AFTN service information)", message.getFilingTime(), message.getOriginator()));
                ipmMessage.setContent(content);
                ipmMessage.setAtsPriority(message.getPriority());
                ipmMessage.setAtsFilingTime(message.getFilingTime());
                ipmMessage.setAtsOHI(message.getAdditionInfo());
                ipmMessage.setAtsContent(content);
                mtConnection.send(ipmMessage);

                logger.debug("Delivery UNK message as IPM to {}", refAmhsMessage.getOrigin());
                final MessageConversionLog ipmConversionLog = ipmMessage.createMessageConversionLog();
                ipmConversionLog.setConvertedDate(covnertedDate);
                ipmConversionLog.setStatus(ConvertResult.SUCCESS);
                aftnMessage.addChild(ipmConversionLog);
            }

            aftnMessage.setStatus(ConvertResult.SUCCESS);
            aftnMessage.setRemark("Converted");
            messageDao.save(aftnMessage);
            logger.info(CONVERT_SUCESSFULLY, message.getFilingTime(), message.getOriginator());
            return;
        }

        processNormalMessage(message, mtConnection);
    }

    private void generateSvcUnknownAddressIndicator(Message message, List<String> unknownAddresses) throws SQLException {

        if (unknownAddresses == null || unknownAddresses.isEmpty()) {
            return;
        }

        final String unknownMessageContent = message.buildUnknown(unknownAddresses);
        final String originator = Config.instance.getSysAFTNAddress();
        final Integer pri = PriorityUtil.toInteger(message.getPriority());
        final GatewayOut gatewayOut = new GatewayOut();
        gatewayOut.setAddress(message.getOriginator());
        gatewayOut.setFilingTime(MtCommon.generateFilingTime());
        gatewayOut.setInsertedTime(new Date());
        gatewayOut.setOriginator(originator);
        gatewayOut.setPriority(pri);
        gatewayOut.setText(unknownMessageContent);
        gatewayoutDao.save(gatewayOut);

        final StringBuilder unknowBuilder = new StringBuilder();
        for (String add : unknownAddresses) {
            unknowBuilder.append(add).append(" ");
        }
        logger.warn("Unknown addressee indicator message has been generated for {}", unknowBuilder.toString());
    }

    /**
     * *
     * Reporting error to control position(s) that defined in the configuration file
     *
     * @param content
     * @param error
     * @param mtConnection
     */
    private void reportControlPosition(String content, String error, MTConnection1 mtConnection) {

        if (controllers == null || controllers.isEmpty() || origin == null || origin.isEmpty()) {
            logger.error(ERROR_NO_CONTROL_POSITION);
            return;
        }

        try {
            final String messageContent = String.format("%s\r\n\r\n%s", error, content);
            final DeliverMessage message = new DeliverMessage();
            message.setSubject(MESSAGE_SUBJECT);
            message.setContent(messageContent);
            message.setAtsMessage(false);
            message.setOriginator(origin);
            message.setPriority(X400_att.X400_PRIORITY_NORMAL);

            final RecipientConfig recipientConfig = Config.instance.getReportRecipient();
            controllers.stream().forEach((item) -> {
                message.addRecip(new Recipient(item.getValue(), recipientConfig));
            });

            mtConnection.send(message);
            logger.warn("Reported AFTN message to control position due to {}", error);
        } catch (Exception ex) {
            logger.error("Report to control postion(s) fail due to " + error, ex);
        }
    }

    private void log(GatewayIn gatewayin, ConvertResult status, String remark) throws SQLException {
        final MessageConversionLog log = new MessageConversionLog(gatewayin);
        log.setStatus(status);
        log.setRemark(remark);
        messageDao.save(log);
    }

    private void log(Message message, ConvertResult status, String remark) throws SQLException {
        final MessageConversionLog log = message.getMessageConversionLog();
        log.setStatus(status);
        log.setRemark(remark);
        messageDao.save(log);
    }

    private String buildAddress(int offset, List<String> addresses) {
        final StringBuilder builder = new StringBuilder();
        String seperator = "";
        int index = 0;
        int breakLimit = offset <= 6 ? 7 : 6;
        for (String recipient : addresses) {
            if (recipient.isEmpty()) {
                continue;
            }
            builder.append(seperator).append(recipient);
            index++;
            if (index % breakLimit == 0) {
                builder.append("\r\n");
                seperator = "";
                breakLimit = 7;
            } else {
                seperator = " ";
            }
        }

        if (!builder.toString().endsWith("\r\n")) {
            builder.append("\r\n");
        }

        return builder.toString();
    }

    private boolean validate(GatewayIn gwin) {
        final String content = gwin.getText();
        return !(content == null || content.isEmpty());
    }

    private MessageConversionLog getReferenceMessage(UnknownAddressInfo msg) throws SQLException {

        if (msg == null) {
            return null;
        }

        String referenceFilingTime, referenceOriginator;

        // Get from AFTN deliveried mesage tables
        if (!msg.isIdentifiedByOriginGroup()) {

            // AFTNMessageDao aftnMessageDao = new AFTNMessageDao();
            logger.debug("Get reference message [cid: {} seq: {}]", msg.getRefChannelID(), msg.getRefChannelSeq());
            if (msg.getRefChannelID() == null || msg.getRefChannelSeq() == null) {
                return null;
            }

            final AftnMessage deliveriedMessage = aftnMessageDao.getDeliveriedMessage(msg.getRefChannelID(), msg.getRefChannelSeq());
            if (deliveriedMessage == null) {
                logger.warn("Reference message not found [cid: {} seq: {}]", msg.getRefChannelID(), msg.getRefChannelSeq());
                return null;
            }

            referenceFilingTime = deliveriedMessage.getFilingTime();
            referenceOriginator = deliveriedMessage.getOrigin();
        } else {
            referenceFilingTime = msg.getRefFilingTime();
            referenceOriginator = msg.getRefOriginator();
        }

        // Get AFTN message
        logger.debug("Finding previous message [{} {}]", referenceFilingTime, referenceOriginator);
        final List<MessageConversionLog> log = messageDao.getReferenceMessage(referenceFilingTime, referenceOriginator);
        if (log == null || log.isEmpty()) {
            logger.info("Subject message cannot be found");
            return null;
        }

        if (log.size() > 1) {
            logger.warn("Subject message count is more than one ({} items)", log.size());
            return null;
        }

        return log.get(0);
    }

    private void keepWaiting(long time) {
        try {
            logger.debug("Retry processing in {} seconds.", time);
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            logger.error("ERROR FOUND!", ex);
        }
    }
}
