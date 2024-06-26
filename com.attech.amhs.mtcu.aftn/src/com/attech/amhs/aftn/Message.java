/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.aftn;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.NONE)
public class Message {

    // public static final int MSG_TYPE_GENERAL = 0;
    // public static final int MSG_TYPE_SERVICE = 1;
    // public static final int MSG_TYPE_CHECK = 2;
    // public static final int MSG_TYPE_ACKNOWLEDGE = 3;
    // public static final int MSG_TYPE_ADS_UNKNOWN = 4;

    private String envelopeAddress; // Store envelope to deliver message
    private String message;  // Store the whole original aftn message
    
    private String headerLine; // Store the header line
    private String addressLine; // Store the address line
    private String originLine; // Store the origin line

    @XmlElement(name = "Content")
    private String text; // Store text content
    // private String end; // Store ending signal
    // private String originMessage;

    // Header
    @XmlElement(name = "MessageID")
    private String messageId; // Store the circleID + sequence number
    // private char transChar;
    // private char receiChar;
    // private char chanChar;
    // private String chanSequence;
    // private Integer chanSequence2;
    // private boolean autoCorrectChanSeq;
    private String serviceIndicator;

    // Address
    @XmlElement(name = "Priority")
    private String priority;

    @XmlElement(name = "Addresses")
    private List<String> addresses;

    // Origin
    @XmlElement(name = "FilingTime")
    private String filingTime;

    @XmlElement(name = "Originator")
    private String originator;

    @XmlElement(name = "OHI")
    private String additionInfo; // need more implementation

    // Text
    private List<String> textLines;

    // End content
    private Type messageType; // 0-N/A; 1-Service Mesage; 2-Check Mesage; 3- Acknowlegement message
    private List<Errors> errs;
    private final Map<Errors, String> errors;

    // private final static String headerTemplate = "Header: [%s][%s][%s][%s][%s]";
    private final DecimalFormat format = new DecimalFormat("000");

    public Message() {
	this.errs = new ArrayList<>();
	this.errors = new HashMap<>();
	this.addresses = new ArrayList<>();
	this.textLines = new ArrayList<>();
	this.messageType = Type.GENERAL;
    }

    public Message(String message) {
	this();
	this.message = message;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append(String.format("CID [%s] PRI [%s] FIL [%s] ORG [%s]\r\n", messageId, priority, filingTime, originator));

	StringBuilder adds = new StringBuilder();
	for (String add : addresses) {
	    adds.append(" ").append(add);
	}
	builder.append(String.format("ADD [%s] \r\n", adds.toString().trim()));
	builder.append("Txt: ").append("\n");
	int index = 1;
	for (String line : textLines) {
	    builder.append("[").append(format.format(index)).append("]").append(line).append("\n");
	    index++;
	}

	return builder.toString();
    }

    public String getErrorMessage() {
	StringBuilder errorMsg = new StringBuilder();
	for (Errors err : errors.keySet()) {
	    errorMsg.append(errors.get(err)).append("\n");
	}
	return errorMsg.toString();
    }

    /**
     * Build up content from
     */
    public void buildContent() {
	if (this.textLines == null) {
	    this.text = "";
	    return;
	}

	StringBuilder builder = new StringBuilder();
	for (int i = 0; i < this.textLines.size(); i++) {
	    if (i == 0) {
		builder.append(this.textLines.get(i)).append("\r\n");
		continue;
	    }

	    builder.append(this.textLines.get(i)).append("\r\n");
	}
	this.text = builder.toString();
    }

    public void addErr(Errors err, String message, Object... param) {
	this.errors.put(err, String.format(message, param));
    }

    public boolean isValid() {
	return this.errors.isEmpty();
    }

    public String buildUnknown(List<String> unknownAddress) {

	String[] recipients = this.envelopeAddress.split(" ");
	List<String> recipientList = Arrays.asList(recipients);
	String address = buildAddress(this.priority.length(), recipientList);
	String unknowAddress = buildAddress("UNKNOWN".length(), unknownAddress);

	StringBuilder builder = new StringBuilder();
	builder.append(String.format("SVC ADS %s %s\r\n", this.getFilingTime(), this.getOriginator()));
	builder.append(this.priority).append(" ").append(address);
	builder.append("UNKNOWN").append(" ").append(unknowAddress);
	builder.append("\r\n").append("\r\n").append("\r\n").append("\r\n");
	return builder.toString();

    }

    private String buildAddress(int offset, List<String> addresses) {
	StringBuilder builder = new StringBuilder();
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

    /**
     * Return this valid
     *
     * @return
     */
    @Deprecated
    public boolean valid() {
	return this.errs == null || this.errs.isEmpty();
    }

    //<editor-fold defaultstate="collapsed" desc="Class Properties">
    /**
     * @return the original AFTN message
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
     * @return the header
     */
    public String getHeaderLine() {
	return headerLine;
    }

    /**
     * @param header the header to set
     */
    public void setHeaderLine(String header) {
	this.headerLine = header;
    }

    /**
     * @return the address
     */
    public String getAddressLine() {
	return addressLine;
    }

    /**
     * @param address the address to set
     */
    public void setAddressLine(String address) {
	this.addressLine = address;
    }

    /**
     * @return the origin
     */
    public String getOriginLine() {
	return originLine;
    }

    /**
     * @param origin the origin to set
     */
    public void setOriginLine(String origin) {
	this.originLine = origin;
    }

    /**
     * @return the text
     */
    public String getText() {
	return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
	this.text = text;
    }

//    /**
//     * @return the end
//     */
//    public String getEnd() {
//	return end;
//    }
//
//    /**
//     * @param end the end to set
//     */
//    public void setEnd(String end) {
//	this.end = end;
//    }

    /**
     * @return the circleId
     */
    public String getMessageId() {
	return messageId;
    }

    /**
     * @param circleId the circleId to set
     */
    public void setMessageId(String circleId) {
	this.messageId = circleId;
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
     * Put address to list
     *
     * @param address
     */
    public void addAddress(String address) {
	address = address.replace("\r", "").replace("\n", "");
	this.addresses.add(address);
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
     * @return the textLines
     */
    public List<String> getTextLines() {
	return textLines;
    }

    /**
     * @param textLines the textLines to set
     */
    public void setTextLines(List<String> textLines) {
	this.textLines = textLines;
    }

    /**
     * add text lines
     *
     * @param textLines
     */
    public void addTextLines(String textLines) {
	this.textLines.add(textLines);
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
    public void setErrs(Errors errs) {
	if (this.errs == null) {
	    this.errs = new ArrayList<>();
	}

	this.errs.add(errs);
    }

    /**
     * @return the envelopeAddress
     */
    public String getEnvelopeAddress() {
	return envelopeAddress;
    }

    /**
     * @param envelopeAddress the envelopeAddress to set
     */
    public void setEnvelopeAddress(String envelopeAddress) {
	while (envelopeAddress.contains("  ")) {
	    envelopeAddress = envelopeAddress.replace("  ", " ");
	}
	this.envelopeAddress = envelopeAddress.trim();
    }
    //</editor-fold>

}
