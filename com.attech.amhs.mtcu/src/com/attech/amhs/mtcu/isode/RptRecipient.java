/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.isode;

import com.isode.x400api.Recip;
import com.isode.x400api.X400_att;
import com.isode.x400mtapi.MTMessage;
import com.isode.x400mtapi.X400mt;

/**
 *
 * @author ANDH
 */
public class RptRecipient extends Recipient {

    protected String suplementInfo;
    protected String deliveryTime;
    protected int nonDeliveryReason;
    protected int nonDeliveryDiagnosticCode;
    protected int userType;

    public RptRecipient() {
    }

    public RptRecipient(String address, String deliveryTime) {
        this.address = address;
        this.deliveryTime = deliveryTime;
    }

    public RptRecipient(String address, Integer nonDeliveryReason, Integer nonDeliveryDiagnosticCode) {
        this.address = address;
        this.nonDeliveryDiagnosticCode = nonDeliveryDiagnosticCode;
        this.nonDeliveryReason = nonDeliveryReason;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        if (this.deliveryTime == null || this.deliveryTime.isEmpty()) {
            builder.append("It could *NOT* be sent to: ").append(this.address).append("\r\n");
            
            builder.append("Non Delivery Reason: ").append(this.nonDeliveryReason).append("\r\n");
            if (this.nonDeliveryDiagnosticCode != -1) {
                builder.append("Non Delivery Diagnostic: ").append(this.nonDeliveryDiagnosticCode).append("\r\n");
            }
            if(this.suplementInfo!=null) {
                builder.append("Supplementary Info: ").append(this.suplementInfo).append("\r\n");
            }
        } else {
            builder.append("It was successfully sent to: ").append(this.address).append("\r\n");
            builder.append("Message Delivery Time: ").append(this.deliveryTime).append("\r\n");
        }
        return builder.toString();
    }

    public void setRecipValues(Recip recip, int index, String arrrivalTime) {
        set(recip, X400_att.X400_S_OR_ADDRESS, this.address, -1);
        set(recip, X400_att.X400_N_ORIGINAL_RECIPIENT_NUMBER, index);

        // set(recip, X400_att.X400_N_RESPONSIBILITY, this.re);
        // Integer orgReportRequest = recip.getOriginReportRequest() == null ? config.getReportRequest() : recip.getOriginReportRequest();
        // Integer mtaReportRequest = recip.getMtaReportRequest() == null ? config.getMtaReportRequest() : recip.getMtaReportRequest();
        set(recip, X400_att.X400_N_REPORT_REQUEST, this.reportRequest);
        set(recip, X400_att.X400_N_MTA_REPORT_REQUEST, this.mtaReportRequest);
        // set(recip_obj, X400_att.X400_N_NOTIFICATION_REQUEST, recip.getReceptNotification() == null ? config.getNotificationRequest() : recip.getReceptNotification());
        set(recip, X400_att.X400_N_RESPONSIBILITY, 1);

        set(recip, X400_att.X400_S_ARRIVAL_TIME, this.deliveryTime, -1);
        set(recip, X400_att.X400_S_CONVERTED_ENCODED_INFORMATION_TYPES, "ia5-text", -1);
        set(recip, X400_att.X400_S_SUPPLEMENTARY_INFO, this.suplementInfo, -1);

        if (this.deliveryTime != null && !this.deliveryTime.isEmpty()) {
            set(recip, X400_att.X400_S_MESSAGE_DELIVERY_TIME, this.deliveryTime, -1);
            set(recip, X400_att.X400_N_TYPE_OF_USER, this.userType);
            return;
        }

        //set(recip, X400_att.X400_N_NON_DELIVERY_REASON, this.nonDeliveryReason);
        set(recip, X400_att.X400_N_NON_DELIVERY_REASON, this.nonDeliveryReason);
        if (this.nonDeliveryDiagnosticCode != -1) {
            set(recip, X400_att.X400_N_NON_DELIVERY_DIAGNOSTIC, this.nonDeliveryDiagnosticCode);
        }
    }

    public void getRecip(MTMessage mtMessage, int index, String arrrivalTime) {

        final Recip recip = new Recip();
        int status = X400mt.x400_mt_recipnew(mtMessage, X400_att.X400_RECIP_REPORT, recip);
        if (status != X400_att.X400_E_NOERROR) {
            return;
        }

        set(recip, X400_att.X400_S_OR_ADDRESS, this.address, -1);
        set(recip, X400_att.X400_N_ORIGINAL_RECIPIENT_NUMBER, index);
        set(recip, X400_att.X400_N_REPORT_REQUEST, this.reportRequest);
        set(recip, X400_att.X400_N_MTA_REPORT_REQUEST, this.mtaReportRequest);
        set(recip, X400_att.X400_N_RESPONSIBILITY, 1);
        set(recip, X400_att.X400_S_ARRIVAL_TIME, arrrivalTime, -1);
        set(recip, X400_att.X400_S_CONVERTED_ENCODED_INFORMATION_TYPES, "ia5-text", -1);
        set(recip, X400_att.X400_S_SUPPLEMENTARY_INFO, this.suplementInfo, -1);

        if (this.deliveryTime != null && !this.deliveryTime.isEmpty()) {
            set(recip, X400_att.X400_S_MESSAGE_DELIVERY_TIME, this.deliveryTime, -1);
            set(recip, X400_att.X400_N_TYPE_OF_USER, this.userType);
            return;
        }

        set(recip, X400_att.X400_N_NON_DELIVERY_REASON, this.nonDeliveryReason);
        if (this.nonDeliveryDiagnosticCode != -1) {
            set(recip, X400_att.X400_N_NON_DELIVERY_DIAGNOSTIC, this.nonDeliveryDiagnosticCode);
        }

    }

    private void set(Recip recip, int attribute, String value, int length) {
        if (value == null || value.isEmpty()) {
            return;
        }
        X400mt.x400_mt_recipaddstrparam(recip, attribute, value, length);
    }

    protected void set(Recip recip, int attribute, String value) {
        set(recip, attribute, value, value.length());
    }

    protected void set(Recip recipObj, int attribute, Integer value, Integer mode) {
        if (value == null) {
            return;
        }
        X400mt.x400_mt_recipaddintparam(recipObj, attribute, value);
        
    }

    protected void set(Recip recip, int attribute, Integer value) {
        set(recip, attribute, value, 0);
    }

    //<editor-fold defaultstate="collapsed" desc="Class Properties">
    /**
     * @return the suplementInfo
     */
    public String getSuplementInfo() {
        return suplementInfo;
    }

    /**
     * @param suplementInfo the suplementInfo to set
     */
    public void setSuplementInfo(String suplementInfo) {
        this.suplementInfo = suplementInfo;
    }

    /**
     * @return the deliveryTime
     */
    public String getDeliveryTime() {
        return deliveryTime;
    }

    /**
     * @param deliveryTime the deliveryTime to set
     */
    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    /**
     * @return the nonDeliveryReason
     */
    public int getNonDeliveryReason() {
        return nonDeliveryReason;
    }

    /**
     * @param nonDeliveryReason the nonDeliveryReason to set
     */
    public void setNonDeliveryReason(int nonDeliveryReason) {
        this.nonDeliveryReason = nonDeliveryReason;
    }

    /**
     * @return the nonDeliveryDiagnosticCode
     */
    public int getNonDeliveryDiagnosticCode() {
        return nonDeliveryDiagnosticCode;
    }

    /**
     * @param nonDeliveryDiagnosticCode the nonDeliveryDiagnosticCode to set
     */
    public void setNonDeliveryDiagnosticCode(int nonDeliveryDiagnosticCode) {
        this.nonDeliveryDiagnosticCode = nonDeliveryDiagnosticCode;
    }

    /**
     * @return the userType
     */
    public int getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(int userType) {
        this.userType = userType;
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
        RptRecipient other = (RptRecipient) object;
        if ((this.address == null && other.getAddress()!= null) || (this.address != null && !this.address.equals(other.getAddress()))) {
            return false;
        }
        return true;
    }
    
    @Override
    /* Overriding finalize method to check which object 
     is garbage collected */
    protected void finalize() throws Throwable  
    { 
        super.finalize();
//        System.out.println(this.getClass() + " successfully garbage collected"); 
    }
}
