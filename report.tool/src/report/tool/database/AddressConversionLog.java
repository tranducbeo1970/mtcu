/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report.tool.database;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author ANDH
 */
@Entity
@Table(name = "address_conversion_log")
public class AddressConversionLog implements Serializable {

    private static final long serialVersionUID = -4797501000549505604L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private MessageConversionLog messageConversionLog;

    @Column(name = "address", nullable = false, length = 128)
    private String address;

    @Column(name = "notify_request")
    private Integer notifyRequest;

    @Column(name = "mta_report_request")
    private Integer mtaReportRequest;

    @Column(name = "report_request")
    private Integer reportRequest;

    // CONSTRUCTORS
    public AddressConversionLog() {
    }

    public AddressConversionLog(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return address;
    }

    //<editor-fold defaultstate="collapsed" desc="Class Properties">
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the originalAddress
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param originalAddress the originalAddress to set
     */
    public void setAddress(String originalAddress) {
        this.address = originalAddress;
    }

    /**
     * @return the notifyRequest
     */
    public Integer getNotifyRequest() {
        return notifyRequest;
    }

    /**
     * @param notifyRequest the notifyRequest to set
     */
    public void setNotifyRequest(Integer notifyRequest) {
        this.notifyRequest = notifyRequest;
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
     * @return the messageConversionLog
     */
    public MessageConversionLog getMessageConversionLog() {
        return messageConversionLog;
    }

    /**
     * @param messageConversionLog the messageConversionLog to set
     */
    public void setMessageConversionLog(MessageConversionLog messageConversionLog) {
        this.messageConversionLog = messageConversionLog;
    }
    //</editor-fold>

    @Override
    public int hashCode() {
        return Long.hashCode(this.id);
        // return new Long(this.id).hashCode();
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof AddressConversionLog)) {
            return false;
        }
        AddressConversionLog other = (AddressConversionLog) object;
        if (this.id != other.getId()) {
            return false;
        }

        return true;
    }

    @Override
    /* Overriding finalize method to check which object 
     is garbage collected */
    protected void finalize() throws Throwable {
        // super.finalize();
        // will print name of object 
//        System.out.println(this.getClass() + " successfully garbage collected");
    }
}
