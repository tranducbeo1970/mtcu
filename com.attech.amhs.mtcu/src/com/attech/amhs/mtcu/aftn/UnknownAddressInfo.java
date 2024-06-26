/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.aftn;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ANDH
 */
public class UnknownAddressInfo {

    private boolean identifiedByOriginGroup;

    private String refIdentifierLine;
    private List<String> refAddressLine;

    private String refChannelID;
    private Integer refChannelSeq;
    private String refFilingTime;
    private String refOriginator;
    private String refPriority;
    private List<String> refAddresses;
    private List<String> unknownAddresses;

    public UnknownAddressInfo() {
        this.refAddresses = new ArrayList<>();
        this.unknownAddresses = new ArrayList<>();
        this.refAddressLine = new ArrayList<>();
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
     * @return the refChannelID
     */
    public String getRefChannelID() {
        return refChannelID;
    }

    /**
     * @param refChannelID the refChannelID to set
     */
    public void setRefChannelID(String refChannelID) {
        this.refChannelID = refChannelID;
    }

    /**
     * @return the refFilingTime
     */
    public String getRefFilingTime() {
        return refFilingTime;
    }

    /**
     * @param refFilingTime the refFilingTime to set
     */
    public void setRefFilingTime(String refFilingTime) {
        this.refFilingTime = refFilingTime;
    }

    /**
     * @return the refOriginator
     */
    public String getRefOriginator() {
        return refOriginator;
    }

    /**
     * @param refOriginator the refOriginator to set
     */
    public void setRefOriginator(String refOriginator) {
        this.refOriginator = refOriginator;
    }

    /**
     * @return the refPriority
     */
    public String getRefPriority() {
        return refPriority;
    }

    /**
     * @param refPriority the refPriority to set
     */
    public void setRefPriority(String refPriority) {
        this.refPriority = refPriority;
    }

    /**
     * @return the refAddresses
     */
    public List<String> getRefAddresses() {
        return refAddresses;
    }

    /**
     * @param refAddresses the refAddresses to set
     */
    public void setRefAddresses(List<String> refAddresses) {
        this.refAddresses = refAddresses;
    }

    /**
     * @return the unknownAddresses
     */
    public List<String> getUnknownAddresses() {
        return unknownAddresses;
    }

    /**
     * @param unknownAddresses the unknownAddresses to set
     */
    public void setUnknownAddresses(List<String> unknownAddresses) {
        this.unknownAddresses = unknownAddresses;
    }

    /**
     * @return the refChannelSeq
     */
    public Integer getRefChannelSeq() {
        return refChannelSeq;
    }

    /**
     * @param refChannelSeq the refChannelSeq to set
     */
    public void setRefChannelSeq(Integer refChannelSeq) {
        this.refChannelSeq = refChannelSeq;
    }

    /**
     * @return the refIdentifierLine
     */
    public String getRefIdentifierLine() {
        return refIdentifierLine;
    }

    /**
     * @param refIdentifierLine the refIdentifierLine to set
     */
    public void setRefIdentifierLine(String refIdentifierLine) {
        this.refIdentifierLine = refIdentifierLine;
    }

    /**
     * @return the refAddressLine
     */
    public List<String> getRefAddressLine() {
        return refAddressLine;
    }

    /**
     * @param refAddressLine the refAddressLine to set
     */
    public void setRefAddressLine(List<String> refAddressLine) {
        this.refAddressLine = refAddressLine;
    }

    public void addRefAddressLine(String refAddressLine) {
        this.refAddressLine.add(refAddressLine);
    }

    //</editor-fold>
    @Override
    public int hashCode() {
        return this.refIdentifierLine.hashCode();
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Message)) {
            return false;
        }
        UnknownAddressInfo other = (UnknownAddressInfo) object;
        if ((this.refIdentifierLine == null && other.getRefIdentifierLine() != null) || (this.refIdentifierLine != null && !this.refIdentifierLine.equals(other.getRefIdentifierLine()))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.refIdentifierLine).append("\r\n");
        for (String addressLine : this.refAddressLine) {
            builder.append(addressLine).append("\r\n");
        }

        final String unknownAddressLine = buildAddress("UNKNOWN".length(), this.unknownAddresses);
        builder.append("UNKNOWN ").append(unknownAddressLine).append("\r\n");
        return builder.toString();
    }

}
