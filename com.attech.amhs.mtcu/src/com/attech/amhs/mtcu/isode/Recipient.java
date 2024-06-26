/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.isode;

import com.attech.amhs.mtcu.MtAttributes;
import com.attech.amhs.mtcu.config.RecipientConfig;
import com.attech.amhs.mtcu.database.entity.AddressConversionLog;

/**
 *
 * @author ANDH
 */
public class Recipient {

    

    protected String address;

    protected Integer reportRequest;
    protected Integer mtaReportRequest;
    protected Integer receiptNotification;
    private boolean phankenhduocaftn;                   // DUC THEM

    public Recipient() {
    }

    public Recipient(String address, RecipientConfig config) {
        this.address = address;
        this.reportRequest = config.getReportRequest();
        this.mtaReportRequest = config.getMtaReportRequest();
        this.receiptNotification = config.getNotificationRequest();
        this.phankenhduocaftn  = false;
    }

    public Recipient(String address, Integer reportRequest, Integer mtaReportRequest, Integer notificationRequest) {
        this.address = address;
        this.reportRequest = reportRequest;
        this.mtaReportRequest = mtaReportRequest;
        this.receiptNotification = notificationRequest;
        this.phankenhduocaftn  = false;
    }

    public AddressConversionLog getAddressConvertionLog() {
        AddressConversionLog addressConversionLog = new AddressConversionLog(this.address);
        addressConversionLog.setMtaReportRequest(this.mtaReportRequest);
        addressConversionLog.setNotifyRequest(this.receiptNotification);
        addressConversionLog.setReportRequest(this.reportRequest);
        return addressConversionLog;
    }

    public boolean allowDeliveriedReport() {
        return MtAttributes.MTA_NON_DELIVERY_REPORT != this.getMtaReportRequest()
                || MtAttributes.ORIGIN_REPORT == this.getReportRequest();
    }

    @Override
    public String toString() {
        return String.format("%s rpt: %s mta-rpt: %s notify: %s", this.address, this.reportRequest, this.mtaReportRequest, this.receiptNotification);
    }

    //<editor-fold defaultstate="collapsed" desc="Class Properties">
    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the reportRequest
     */
    public Integer getReportRequest() {
        return reportRequest;
    }

    /**
     * @param reportRequest the reportRequest to set
     */
    public void setReportRequest(Integer reportRequest) {
        this.reportRequest = reportRequest;
    }

    /**
     * @return the mtaReportRequest
     */
    public Integer getMtaReportRequest() {
        return mtaReportRequest;
    }

    /**
     * @param mtaReportRequest the mtaReportRequest to set
     */
    public void setMtaReportRequest(Integer mtaReportRequest) {
        this.mtaReportRequest = mtaReportRequest;
    }

    /**
     * @return the receiptNotification
     */
    public Integer getReceiptNotification() {
        return receiptNotification;
    }

    /**
     * @param receiptNotification the receiptNotification to set
     */
    public void setReceiptNotification(Integer receiptNotification) {
        this.receiptNotification = receiptNotification;
    }
    //</editor-fold>

    @Override
    public int hashCode() {
        return this.address.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RptRecipient)) {
            return false;
        }
        Recipient other = (Recipient) object;
        if ((this.address == null && other.getAddress() != null) || (this.address != null && !this.address.equals(other.getAddress()))) {
            return false;
        }
        return true;
    }

    @Override
    /* Overriding finalize method to check which object 
     is garbage collected */
    protected void finalize() throws Throwable {
        super.finalize();
//        System.out.println(this.getClass() + " successfully garbage collected");
    }

    /**
     * @return the phankenhduocaftn
     */
    public boolean isPhankenhduocaftn() {
        return phankenhduocaftn;
    }

    /**
     * @param phankenhduocaftn the phankenhduocaftn to set
     */
    public void setPhankenhduocaftn(boolean phankenhduocaftn) {
        this.phankenhduocaftn = phankenhduocaftn;
    }
}
