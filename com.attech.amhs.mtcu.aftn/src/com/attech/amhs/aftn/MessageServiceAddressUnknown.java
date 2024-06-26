/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.aftn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author ANDH
 */
public class MessageServiceAddressUnknown extends Message {

    private boolean identifiedByOriginGroup;
    private String svcChannelIdentifier;
    private Integer svcSequenceNumber;
    private String svcFilingTime;
    private String svcOriginator;
    private String svcPriority;
    private List<String> svcRefAddresses;
    private List<String> svcUnknownAddresses;
    private String refSVCLine;
    private String refSVCAddLine;
    private String refUKNAddLine;

    public MessageServiceAddressUnknown(Message message) {
        super.setMessage(message.getMessage());
        super.setHeaderLine(message.getHeaderLine());
        super.setAddressLine(message.getAddressLine());
        super.setOriginLine(message.getOriginLine());
        super.setText(message.getText());
        // super.setEnd(message.getEnd());

        super.setMessageId(message.getMessageId());
        // super.setTransChar(message.getTransChar());
        // super.setChanChar(message.getChanChar());
        // super.setReceiChar(message.getReceiChar());
        // super.setChanSequence(message.getChanSequence());
        // super.setAutoCorrectChanSeq(message.isAutoCorrectChanSeq());
        super.setServiceIndicator(message.getServiceIndicator());

        // Address
        super.setPriority(message.getPriority());
        super.setAddresses(message.getAddresses());

        // Origin
        super.setFilingTime(message.getFilingTime());
        super.setOriginator(message.getOriginator());
        super.setAdditionInfo(message.getAdditionInfo());

        // Text
        super.setTextLines(message.getTextLines());

        // End content
        super.setMessageType(Type.UNKNOWN);
        parse(message.getTextLines());
    }

    private void parse(List<String> contents) {

        int index = 0;
        String line = contents.get(index);

        this.setRefSVCLine(line);

        // Get reference message id
        String[] strs = line.split(" ");

        if (strs.length == 3) {

            this.setIdentifiedByOriginGroup(false);
            String som = strs[2];
            char ch = som.charAt(2);

            // logger.debug("Identified by circuit group");
            if (!Character.isDigit(ch)) {
                this.setSvcChannelIdentifier(som.substring(0, 3));
                this.setSvcSequenceNumber(Integer.parseInt(som.substring(3)));
            } else {
                this.setSvcChannelIdentifier(som.substring(0, 2));
                this.setSvcSequenceNumber(Integer.parseInt(som.substring(2)));
            }
        } else if (strs.length == 4) {
            // logger.debug("Identified by origin group");
            this.setIdentifiedByOriginGroup(true);
            this.setSvcFilingTime(strs[2]);
            this.setSvcOriginator(strs[3]);
        }

        StringBuilder originLine = new StringBuilder();
        String seperator = "";
        index++;
        while (index < contents.size()) {
            if (contents.get(index).startsWith("UNKNOWN")) {
                break;
            }
            originLine.append(seperator).append(contents.get(index));
            seperator = "\r\n";
            index++;
        }

        this.setRefSVCAddLine(originLine.toString());
        String[] originalAddress = originLine.toString().replace("\r\n", " ").replace("\r", " ").replace("\n", " ").split(" ");
        List<String> originAddrs = new ArrayList<>(Arrays.asList(originalAddress));

        if (originAddrs.get(0).length() == 2) {
            this.setSvcPriority(originAddrs.get(0));
            originAddrs.remove(0);
        }
        this.setSvcRefAddresses(originAddrs);

        // Get Unknown address
        StringBuilder unknownAddressLine = new StringBuilder();
        seperator = "";
        while (index < contents.size()) {
            if (contents.get(index).isEmpty()) {
                index++;
                continue;
            }

            unknownAddressLine.append(seperator).append(contents.get(index));
            seperator = "\r\n";
            index++;
        }
        this.setRefUKNAddLine(unknownAddressLine.toString());
        String[] unknownAddresses = unknownAddressLine.toString().replace("UNKNOWN ", "").replace("\r\n", " ").replace("\r", " ").replace("\n", " ").split(" ");
        List<String> addrss = new ArrayList<>(Arrays.asList(unknownAddresses));
        this.setSvcUnknownAddresses(addrss);
    }

    /**
     * @return the identifiedByOriginGroup
     */
    public boolean isIdentifiedByOriginGroup() {
        return identifiedByOriginGroup;
    }

    /**
     * @param identifiedByOriginGroup the identifiedByOriginGroup to set
     */
    public void setIdentifiedByOriginGroup(boolean identifiedByOriginGroup) {
        this.identifiedByOriginGroup = identifiedByOriginGroup;
    }

    /**
     * @return the svcChannelIdentifier
     */
    public String getSvcChannelIdentifier() {
        return svcChannelIdentifier;
    }

    /**
     * @param svcChannelIdentifier the svcChannelIdentifier to set
     */
    public void setSvcChannelIdentifier(String svcChannelIdentifier) {
        this.svcChannelIdentifier = svcChannelIdentifier;
    }

    /**
     * @return the svcSequenceNumber
     */
    public Integer getSvcSequenceNumber() {
        return svcSequenceNumber;
    }

    /**
     * @param svcSequenceNumber the svcSequenceNumber to set
     */
    public void setSvcSequenceNumber(Integer svcSequenceNumber) {
        this.svcSequenceNumber = svcSequenceNumber;
    }

    /**
     * @return the svcFilingTime
     */
    public String getSvcFilingTime() {
        return svcFilingTime;
    }

    /**
     * @param svcFilingTime the svcFilingTime to set
     */
    public void setSvcFilingTime(String svcFilingTime) {
        this.svcFilingTime = svcFilingTime;
    }

    /**
     * @return the svcOriginator
     */
    public String getSvcOriginator() {
        return svcOriginator;
    }

    /**
     * @param svcOriginator the svcOriginator to set
     */
    public void setSvcOriginator(String svcOriginator) {
        this.svcOriginator = svcOriginator;
    }

    /**
     * @return the svcPriority
     */
    public String getSvcPriority() {
        return svcPriority;
    }

    /**
     * @param svcPriority the svcPriority to set
     */
    public void setSvcPriority(String svcPriority) {
        this.svcPriority = svcPriority;
    }

    /**
     * @return the svcRefAddresses
     */
    public List<String> getSvcRefAddresses() {
        return svcRefAddresses;
    }

    /**
     * @param svcRefAddresses the svcRefAddresses to set
     */
    public void setSvcRefAddresses(List<String> svcRefAddresses) {
        this.svcRefAddresses = svcRefAddresses;
    }

    /**
     * @return the svcUnknownAddresses
     */
    public List<String> getSvcUnknownAddresses() {
        return svcUnknownAddresses;
    }

    /**
     * @param svcUnknownAddresses the svcUnknownAddresses to set
     */
    public void setSvcUnknownAddresses(List<String> svcUnknownAddresses) {
        this.svcUnknownAddresses = svcUnknownAddresses;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Identified by origin group: ").append(this.identifiedByOriginGroup).append("\n");

        if (this.identifiedByOriginGroup) {
            builder.append("Filing time: ").append(svcFilingTime).append("   ").append(" Originator: ").append(svcOriginator).append("\n");
        } else {
            builder.append(String.format("Circuit ID: %s Sequence Number: %s\n", svcChannelIdentifier, svcSequenceNumber));
        }
        for (String str : this.svcUnknownAddresses) {
            builder.append("    ").append(str).append("\n");
        }

        return builder.toString();

    }

    public String rebuildMessage() {
        StringBuilder builder = new StringBuilder();
        // builder.append(this.getHeader()).append("\r\n");
        // builder.append(this.getAddress()).append("\r\n");
        // builder.append(this.getOrigin()).append("\r\n");
        builder.append(this.getRefSVCLine()).append("\r\n");
        builder.append(this.getRefSVCAddLine()).append("\r\n");
        String unknownAddressLine = joinStr(this.getSvcUnknownAddresses());
        builder.append(unknownAddressLine).append("\r\n");
        // builder.append("\r\n\r\n\r\n\r\n\r\nNNNN");
        return builder.toString();
    }

    private String joinStr(List<String> strs) {
        if (strs == null || strs.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        int index = 1;
        for (String str : strs) {
            if (index < 7) {
                index++;
                builder.append(str).append(" ");
                continue;
            }

            index = 1;
            builder.append(str).append("\n");
        }

        return builder.toString().trim();
    }

    /**
     * @return the refSVCLine
     */
    public String getRefSVCLine() {
        return refSVCLine;
    }

    /**
     * @param refSVCLine the refSVCLine to set
     */
    public void setRefSVCLine(String refSVCLine) {
        this.refSVCLine = refSVCLine;
    }

    /**
     * @return the refSVCAddLine
     */
    public String getRefSVCAddLine() {
        return refSVCAddLine;
    }

    /**
     * @param refSVCAddLine the refSVCAddLine to set
     */
    public void setRefSVCAddLine(String refSVCAddLine) {
        this.refSVCAddLine = refSVCAddLine;
    }

    /**
     * @return the refUKNAddLine
     */
    public String getRefUKNAddLine() {
        return refUKNAddLine;
    }

    /**
     * @param refUKNAddLine the refUKNAddLine to set
     */
    public void setRefUKNAddLine(String refUKNAddLine) {
        this.refUKNAddLine = refUKNAddLine;
    }

}
