/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import com.isode.x400.highlevel.X400Msg;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "Recipient")
@XmlAccessorType(XmlAccessType.NONE)
public class Recipient {
    @XmlElement(name = "OR")
    private String or;
    
    @XmlElement(name = "ReceiptNotify")
    private Integer receiptNotification;
    
    @XmlElement(name = "Report")
    private X400Msg.DR_Request reportRequest;
    
    @XmlElement(name = "OriginReportRequest")
    private Integer originReportRequest;
    
    @XmlElement(name = "MtaReportRequest")
    private Integer mtaReportRequest;
    
    @XmlElement(name = "No")
    private Integer no;

    /**
     * @return the or
     */
    public String getOr() {
        return or;
    }

    /**
     * @param or the or to set
     */
    public void setOr(String or) {
        this.or = or;
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

    /**
     * @return the reportRequest
     */
    public X400Msg.DR_Request getReportRequest() {
        return reportRequest;
    }

    /**
     * @param reportRequest the reportRequest to set
     */
    public void setReportRequest(X400Msg.DR_Request reportRequest) {
        this.reportRequest = reportRequest;
    }

    /**
     * @return the originReportRequest
     */
    public Integer getOriginReportRequest() {
        return originReportRequest;
    }

    /**
     * @param originReportRequest the originReportRequest to set
     */
    public void setOriginReportRequest(Integer originReportRequest) {
        this.originReportRequest = originReportRequest;
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
     * @return the no
     */
    public Integer getNo() {
        return no;
    }

    /**
     * @param no the no to set
     */
    public void setNo(Integer no) {
        this.no = no;
    }

}
