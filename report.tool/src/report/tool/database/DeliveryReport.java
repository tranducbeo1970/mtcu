/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package report.tool.database;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author HONG
 */
@Entity
@Table(name = "delivery_report")
@NamedQueries({
    @NamedQuery(name = "DeliveryReport.SELECT_ALL", query = "From DeliveryReport")
})
public class DeliveryReport implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Class Properties">
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
     * @return the recipient
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * @param recipient the recipient to set
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * @return the arrivalTime
     */
    public String getArrivalTime() {
        return arrivalTime;
    }

    /**
     * @param arrivalTime the arrivalTime to set
     */
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * @return the subjectId
     */
    public String getSubjectId() {
        return subjectId;
    }

    /**
     * @param subjectId the subjectId to set
     */
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * @return the contentId
     */
    public String getContentId() {
        return contentId;
    }

    /**
     * @param contentId the contentId to set
     */
    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    /**
     * @return the encodeInfomationType
     */
    public String getEncodeInfomationType() {
        return encodeInfomationType;
    }

    /**
     * @param encodeInfomationType the encodeInfomationType to set
     */
    public void setEncodeInfomationType(String encodeInfomationType) {
        this.encodeInfomationType = encodeInfomationType;
    }

    /**
     * @return the contentCorrelatorIA5String
     */
    public String getContentCorrelatorIA5String() {
        return contentCorrelatorIA5String;
    }

    /**
     * @param contentCorrelatorIA5String the contentCorrelatorIA5String to set
     */
    public void setContentCorrelatorIA5String(String contentCorrelatorIA5String) {
        this.contentCorrelatorIA5String = contentCorrelatorIA5String;
    }

    /**
     * @return the priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * @return the contentType
     */
    public Integer getContentType() {
        return contentType;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    /**
     * @return the nonDeliveryReasonCode
     */
    public int getNonDeliveryReasonCode() {
        return nonDeliveryReasonCode;
    }

    /**
     * @param nonDeliveryReasonCode the nonDeliveryReasonCode to set
     */
    public void setNonDeliveryReasonCode(int nonDeliveryReasonCode) {
        this.nonDeliveryReasonCode = nonDeliveryReasonCode;
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
    //</editor-fold>

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "originator", nullable = false, length = 128)
    private String originator;

    @Column(name = "recipient", nullable = false, length = 128)
    private String recipient;

    @Column(name = "arrival_time", nullable = true, length = 32)
    private String arrivalTime;

    @Column(name = "non_delivery_reason")
    private int nonDeliveryReasonCode;

    @Column(name = "non_delivery_diagnostic_code")
    private int nonDeliveryDiagnosticCode;

    @Column(name = "suplement_info")
    private String suplementInfo;

    @Column(name = "subject_id", nullable = false, length = 128)
    private String subjectId;

    @Column(name = "content_id", nullable = true, length = 128)
    private String contentId;

    @Column(name = "encode_information_type", nullable = true, length = 128)
    private String encodeInfomationType;

    @Column(name = "content_correlator_string", nullable = true, length = 128)
    private String contentCorrelatorIA5String;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "content_type")
    private Integer contentType;

}
