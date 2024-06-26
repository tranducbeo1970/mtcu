/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.aftn;

import com.attech.amhs.mtcu.common.AftnValidator;
import com.attech.amhs.mtcu.database.entity.AddressConversionLog;
import com.attech.amhs.mtcu.database.entity.MessageConversionLog;
import com.attech.amhs.mtcu.database.enums.ConvertResult;
import com.attech.amhs.mtcu.database.enums.MessageCategory;
import com.attech.amhs.mtcu.database.enums.MessageType;
import com.attech.amhs.mtcu.database.enums.Priority;
import com.attech.amhs.mtcu.common.StringUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ANDH
 */
@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.NONE)
public class Message {

    private String message;

    // Header
    private String channelId; // Store the circleID + sequence number
    private String serviceIndicator;

    private String priority;

    // Address
    private List<String> addresses;
    private List<String> envelopeMessages;
    private String filingTime;

    // Origin
    private String originator;
    private String originId; // Filing time + originator
    private String additionInfo; // need more implementation

    private String content;
    private List<String> contents;

    // End content
    private Type messageType; // 0-N/A; 1-Service Mesage; 2-Check Mesage; 3- Acknowlegement message
    private List<Errors> errs;

    private UnknownAddressInfo unknownAddressInfo;
    private AckInfo ackInfo;
//    private Validator validator = new Validator();

    public Message() {
        this.errs = new ArrayList<>();
        this.addresses = new ArrayList<>();
        this.contents = new ArrayList<>();
        this.envelopeMessages = new ArrayList<>();
        this.messageType = Type.GENERAL;
    }

    public Message(String message) {
        this();
        this.message = message;
        parsing(message);
    }

    public String getSubject() {
        return String.format("%s - %s", this.channelId, this.originId);
    }

    public String getErrorMessage() {
        final StringBuilder builder = new StringBuilder();
        if (this.errs.isEmpty()) {
            return "";
        }

        for (Errors err : this.errs) {
            builder.append(err).append("\n");
        }
        return builder.toString();
    }

    public String buildUnknown(List<String> unknownAddress) {

        // Reimplement later
        // String[] recipients = this.envelopeAddress.split(" ");
        // List<String> recipientList = Arrays.asList(recipients);
        final String address = buildAddress(this.priority.length(), this.envelopeMessages);
        final String unknowAddress = buildAddress("UNKNOWN".length(), unknownAddress);

        final StringBuilder builder = new StringBuilder();
        builder.append(String.format("SVC ADS %s %s\r\n", this.getFilingTime(), this.getOriginator()));
        builder.append(this.priority).append(" ").append(address);
        builder.append("UNKNOWN").append(" ").append(unknowAddress);
        builder.append("\r\n").append("\r\n").append("\r\n").append("\r\n");
        return builder.toString();
    }

    public boolean isValid() {
        return this.getErrs().isEmpty();
    }

    public MessageConversionLog getMessageConversionLog() {
        final MessageConversionLog conversionLog = new MessageConversionLog();

        switch (this.messageType) {
            case ACKNOWLEDGE:
                conversionLog.setCategory(MessageCategory.ACKNOWLEDGE);
                break;
            case UNKNOWN:
                conversionLog.setCategory(MessageCategory.UNKNOWN);
                break;
            default:
                conversionLog.setCategory(MessageCategory.GENERAL);
                break;

        }

        conversionLog.setContent(this.content);
        conversionLog.setConvertedDate(new Date());
        conversionLog.setFilingTime(this.filingTime);
        conversionLog.setMessageId(this.originId);
        conversionLog.setOhi(this.additionInfo);
        conversionLog.setOrigin(this.originator);
        conversionLog.setPriority(Priority.valueOf(this.priority));
        conversionLog.setStatus(ConvertResult.SUCCESS);
        conversionLog.setType(MessageType.AFTN);

        for (String enAddress : this.envelopeMessages) {
            conversionLog.addAddressLog(new AddressConversionLog(enAddress));
        }
        return conversionLog;
    }

    public void setEnvelopeAddress(String envelopeAddress) {
        if (envelopeAddress.isEmpty()) {
            return;
        }

        final String[] envelopeAddresses = envelopeAddress.split(" ");
        for (String enAddress : envelopeAddresses) {
//            if (!AftnValidator.validateSingleAddressFormat(enAddress)) {
//                continue;
//            }
            this.envelopeMessages.add(enAddress);
        }
    }

    // PRIVATE METHODS
    private void parsing(String message) {

        // Reformat content
        String messageContent = message.replace("\u0007", "").trim();
        while (messageContent.contains("  ")) {
            messageContent = messageContent.replace("  ", " ").trim();
        }

        // Validate message 
        if (messageContent.isEmpty()) {
            this.addError(Errors.NO_MESSAGE);
            return;
        }

        if (!messageContent.startsWith(MessageAttributes.MSG_CONTENT_START)) {
            this.addError(Errors.OMMIT_START_SIGNAL);
            return;
        }

        if (!messageContent.endsWith(MessageAttributes.MSG_CONTENT_END)) {
            messageContent += "\r\n" + MessageAttributes.MSG_CONTENT_ADDED_ENDING;
        }

        if (MessageAttributes.MSG_MAXIMUM_CHARACTER < messageContent.length()) {
            // do nothing
        }

        messageContent = messageContent.replace("\r\n", "\n");
        // String[] lines = messageContent.split("\r\n");
        String[] lines = messageContent.split("\n");

        if (lines.length < 3) {
            this.addError(Errors.INVALID_FORMAT);
            return;
        }

        final String header = lines[0];

        // Get Header
        parsingHeader(header);

        if (!this.isValid()) {
            return;
        }

        // In case its a check message
        String line = lines[1];
        if (line.equals(MessageAttributes.MSG_CONTENT_CHECKING)) {

            // Set message type
            this.setMessageType(Type.CHECK);

            // Get content
            final StringBuilder builder = new StringBuilder();
            final int length = lines.length - 1;
            for (int i = 1; i < length; i++) {
                builder.append(lines[i]).append("\r\n");
            }

            this.setContent(builder.toString());
            return;
        }

        // Get address fields
        int index = 1;
        final StringBuilder addressBuilder = new StringBuilder();
        while (!AftnValidator.validateOrigin(line) || index >= lines.length) {
            addressBuilder.append(line.trim()).append(" ");
            // index++;
            line = lines[++index];
        }

        // Parsing address
        parsingAddress(addressBuilder.toString());

        if (!this.isValid()) {
            return;
        }

        // Parsing Originator
        line = lines[index++];
        // index++;

        parsingOriginator(line);

        // Get content fields
        // final StringBuilder builder = new StringBuilder();
        for (int i = index; i < lines.length - 1; i++) {
            this.contents.add(lines[i]);
            // builder.append(lines[i]).append("\r\n");
        }
        // this.setContent(builder.toString());

        this.content = String.join("\r\n", this.contents);

        processContent(this.contents);
    }

    private void processContent(List<String> lines) {

        //List<String> lines = message.getTextLines();
        if (lines == null || lines.isEmpty()) {
            return;
        }

        String line = lines.get(0);

        // ACK MESSAGE
        if (AftnValidator.validateAckMessage(line) && this.priority.equalsIgnoreCase("SS")) {
            String[] strs = line.split(" ");
            if (strs == null || strs.length < 3) {
                return;
            }

            this.messageType = Type.ACKNOWLEDGE;
            this.ackInfo = new AckInfo(strs[1], strs[2]);
            return;
        }

        // UNK MESSAGE
        if (line.startsWith("SVC ADS ") && this.content.contains("UNKNOWN")) {
            parseUknownAddressMessage(lines);
        }

    }

    private void parseUknownAddressMessage(List<String> contents) {

        int index = 0;
        String line = contents.get(index++);

        String[] strs = line.split(" ");
        final int length = strs.length;
        if (length < 3) {
            return;
        }

        this.unknownAddressInfo = new UnknownAddressInfo();
        this.unknownAddressInfo.setRefIdentifierLine(line);

        if (length == 3) {
            this.unknownAddressInfo.setIdentifiedByOriginGroup(false);
            String som = strs[2];
            char ch = som.charAt(2);

            // logger.debug("Identified by circuit group");
            if (!Character.isDigit(ch)) {
                if (!StringUtil.isNumeric(som.substring(3))) {
                    return;
                }
                this.unknownAddressInfo.setRefChannelID(som.substring(0, 3));
                this.unknownAddressInfo.setRefChannelSeq(Integer.parseInt(som.substring(3)));
            } else {
                if (!StringUtil.isNumeric(som.substring(2))) {
                    return;
                }
                this.unknownAddressInfo.setRefChannelID(som.substring(0, 2));
                this.unknownAddressInfo.setRefChannelSeq(Integer.parseInt(som.substring(2)));
            }

            this.unknownAddressInfo.setRefChannelID(strs[2]);
        } else {
            this.unknownAddressInfo.setIdentifiedByOriginGroup(true);
            this.unknownAddressInfo.setRefFilingTime(strs[2]);
            this.unknownAddressInfo.setRefOriginator(strs[3]);
        }

        final int contentLength = contents.size();
        final StringBuilder textLineBuilder = new StringBuilder();
        while (index < contentLength) {
            String orgLine = contents.get(index).trim();

            if (orgLine.startsWith("UNKNOWN")) {
                break;
            }

            this.unknownAddressInfo.addRefAddressLine(orgLine);
            textLineBuilder.append(orgLine).append(" ");
            index++;
        }

        // Get Ref Addresses
        final String[] refAddresses = textLineBuilder.toString().replace("\r\n", " ").replace("\r", " ").replace("\n", " ").trim().split(" ");
        List<String> refAddressList = new ArrayList<>(Arrays.asList(refAddresses));

        if (refAddressList.get(0).length() == 2) {
            this.unknownAddressInfo.setRefPriority(refAddressList.get(0));
            refAddressList.remove(0);
        }
        this.unknownAddressInfo.setRefAddresses(refAddressList);

        // Get Unknown address
        // textLineBuilder = new StringBuilder();
        textLineBuilder.delete(0, textLineBuilder.length());
        while (index < contentLength) {
            String addressLine = contents.get(index).trim();

            if (addressLine.isEmpty()) {
                break;
            }

            textLineBuilder.append(addressLine).append(" ");
            index++;
        }
        final String[] unknownAddresses = textLineBuilder.toString().replace("UNKNOWN ", "").replace("\r\n", " ").replace("\r", " ").replace("\n", " ").trim().split(" ");
        final List<String> unknownAddressList = new ArrayList<>(Arrays.asList(unknownAddresses));
        this.unknownAddressInfo.setUnknownAddresses(unknownAddressList);
        this.messageType = Type.UNKNOWN;
    }

    private void addError(Errors err) {
        this.getErrs().add(err);
    }

    private void parsingAddress(String address) {
        if (address.isEmpty()) {
            this.addError(Errors.INVALID_FORMAT);
            return;
        }

        address = address.trim();

        final String[] lines = address.split(" ");
        if (lines == null || lines.length == 0) {
            this.addError(Errors.MSG_ADS_ERROR);
            return;
        }

        final String prio = lines[0];
        if (!AftnValidator.validatePriority(prio)) {
            this.addError(Errors.MSG_PRIORITY_ERROR);
            return;
        }

        this.setPriority(lines[0]);
        for (int i = 1; i < lines.length; i++) {
            if (!AftnValidator.validateSingleAddressFormat(lines[i])) {
                continue;
            }
            this.addresses.add(lines[i]);
        }
    }

    private void parsingHeader(String header) {

        if (!AftnValidator.validateHeaderCharacter(header)) {
            this.addError(Errors.MSG_INVALID_HEADER);
            return;
        }

        final String[] hearders = header.split(" ");

        // Get message ID
        if (hearders.length < 2) {
            this.addError(Errors.MSG_INVALID_HEADER);
            return;
        }
        this.setChannelId(hearders[1]);

        // Get Service Indicator
        if (hearders.length < 3) {
            return;
        }
        this.setServiceIndicator(hearders[2]);
    }

    private void parsingOriginator(String message) {

        final String origin = message.replace("\u0007", "").trim();

        if (!AftnValidator.validateOrigin(origin)) {
            this.addError(Errors.OGN_ERROR);
            return;
        }

        // Split string
        final String[] strs = origin.split(" ");
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strs.length; i++) {
            switch (i) {
                case 0:
                    this.setFilingTime(strs[0]);
                    break;
                case 1:
                    this.setOriginator(strs[1]);
                    break;
                default:
                    builder.append(" ").append(strs[i]);
                    break;
            }
        }

        this.setOriginId(String.format("%s %s", getFilingTime(), this.getOriginator()));
        this.setAdditionInfo(builder.toString().trim());
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

    //<editor-fold defaultstate="collapsed" desc="Class Properties">
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the channelId
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * @param channelId the channelId to set
     */
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    /**
     * @return the serviceIndicator
     */
    public String getServiceIndicator() {
        return serviceIndicator;
    }

    /**
     * @param serviceIndicator the serviceIndicator to set
     */
    public void setServiceIndicator(String serviceIndicator) {
        this.serviceIndicator = serviceIndicator;
    }

    /**
     * @return the priority
     */
    public String getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * @return the addresses
     */
    public List<String> getAddresses() {
        return addresses;
    }

    /**
     * @param addresses the addresses to set
     */
    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    /**
     * @return the envelopeMessages
     */
    public List<String> getEnvelopeMessages() {
        return envelopeMessages;
    }

    /**
     * @param envelopeMessages the envelopeMessages to set
     */
    public void setEnvelopeMessages(List<String> envelopeMessages) {
        this.envelopeMessages = envelopeMessages;
    }

    /**
     * @return the filingTime
     */
    public String getFilingTime() {
        return filingTime;
    }

    /**
     * @param filingTime the filingTime to set
     */
    public void setFilingTime(String filingTime) {
        this.filingTime = filingTime;
    }

    /**
     * @return the originator
     */
    public String getOriginator() {
        return originator;
    }

    /**
     * @param originator the originator to set
     */
    public void setOriginator(String originator) {
        this.originator = originator;
    }

    /**
     * @return the originId
     */
    public String getOriginId() {
        return originId;
    }

    /**
     * @param originId the originId to set
     */
    public void setOriginId(String originId) {
        this.originId = originId;
    }

    /**
     * @return the additionInfo
     */
    public String getAdditionInfo() {
        return additionInfo;
    }

    /**
     * @param additionInfo the additionInfo to set
     */
    public void setAdditionInfo(String additionInfo) {
        this.additionInfo = additionInfo;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the contents
     */
    public List<String> getContents() {
        return contents;
    }

    /**
     * @param contents the contents to set
     */
    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    /**
     * @return the messageType
     */
    public Type getMessageType() {
        return messageType;
    }

    /**
     * @param messageType the messageType to set
     */
    public void setMessageType(Type messageType) {
        this.messageType = messageType;
    }

    /**
     * @return the errs
     */
    public List<Errors> getErrs() {
        return errs;
    }

    /**
     * @param errs the errs to set
     */
    public void setErrs(List<Errors> errs) {
        this.errs = errs;
    }

    /**
     * @return the unknownAddressInfo
     */
    public UnknownAddressInfo getUnknownAddressInfo() {
        return unknownAddressInfo;
    }

    /**
     * @return the ackInfo
     */
    public AckInfo getAckInfo() {
        return ackInfo;
    }
    //</editor-fold>

    @Override
    /* Overriding finalize method to check which object 
     is garbage collected */
    protected void finalize() throws Throwable {
        super.finalize();
        // will print name of object 
//        System.out.println(this.getClass() + " successfully garbage collected");
    }

    @Override
    public int hashCode() {
        return this.message.hashCode();
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.message == null && other.getMessage() != null) || (this.message != null && !this.message.equals(other.getMessage()))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(String.format("Channel ID: %s\n", this.getChannelId()));
        builder.append(String.format("Service Indicator: %s\n", this.getServiceIndicator()));
        if (this.getMessageType() == Type.CHECK) {
            builder.append("Content: <CHECK MESSAGE>\n");
            return builder.toString();
        }

        builder.append(String.format("Priority: %s\n", this.getPriority()));
        builder.append("Address:\n");

        for (String address : this.getAddresses()) {
            builder.append(String.format("%20s\n", address));
        }

        builder.append(String.format("Filing time: %s\n", this.getFilingTime()));
        builder.append(String.format("Origin: %s\n", this.getOriginator()));
        builder.append(String.format("OHI: %s\n", this.getAdditionInfo()));
        builder.append("Content: \n");
        builder.append(this.getContent());
        return builder.toString();
    }

}
