/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report.tool.database;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import report.tool.database.enums.ConvertResult;
import report.tool.database.enums.MessageCategory;
import report.tool.database.enums.MessageType;
import report.tool.database.enums.Priority;

/**
 *
 * @author ANDH
 */
@Entity
@Table(name = "message_conversion_log")
public class MessageConversionLog implements Serializable {

    private static final long serialVersionUID = -9054121990548798244L;

    @Transient
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

//    public static final String MSG_TYPE_PROBE = "P";
//    public static final String MSG_TYPE_IPM = "M";
//    public static final String MSG_TYPE_IPN = "N";
//    public static final String MSG_TYPE_REPORT = "R";
//    public static final String MSG_TYPE_UNKNOWN = "U";
//    public static final String MSG_TYPE_SVC = "S";
//    public static final String MSG_TYPE_ACK = "A";
//    public static final String MSG_TYPE_GENERAL = "G";
//
//    public static final String TYPE_AMHS = "A";
//    public static final String TYPE_AFTN = "F";
//
//    public static final short CONVERT_FAIL = -1;
//    public static final short CONVERT_SUCCESS = 0;
//    public static final short CONVERT_WITH_ERROR = 1;
//    public static final short CONVERTED = 2;
//    public static final short DELIVERY_SUCCESS = 3;
//    public static final short DELIVERY_FAIL = 4;
//    public static final short CONVERT_DISCARD = 5;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "date", length = 8)
    private String date;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 4)
    private MessageType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", length = 12)
    private MessageCategory category;

    @Column(name = "message_id", length = 256)
    private String messageId;

    @Column(name = "ipm_id", length = 256)
    private String ipmId;

    @Column(name = "subject_id", length = 256)
    private String subjectId;

    @Column(name = "subject_ipm", length = 256)
    private String subjectIpm;

    @Column(name = "content_id", length = 256)
    private String contentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", length = 12)
    private Priority priority;

    @Column(name = "ohi", length = 64)
    private String ohi;

    @Column(name = "origin", length = 128)
    private String origin;

    @Column(name = "filing_time", length = 20)
    private String filingTime;

    @Column(name = "subject", length = 512)
    private String subject;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "converted_time")
    private Date convertedTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 8)
    private ConvertResult status;

    @Column(name = "remark", length = 256)
    private String remark;

    @ManyToOne
    @JoinColumn(name = "reference_id")
    private MessageConversionLog parents;

    @OneToMany(mappedBy = "parents", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<MessageConversionLog> childs;

    @OneToMany(mappedBy = "messageConversionLog", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<AddressConversionLog> addressLogs;

    // CONSTRUCTORS
    public MessageConversionLog() {
        addressLogs = new HashSet<>();
        childs = new HashSet<>();
    }

//    public MessageConversionLog(GatewayIn gatewayin) {
//        this();
//        this.type = MessageType.AFTN;
//        this.category = MessageCategory.GENERAL;
//        this.content = gatewayin.getText();
//        this.priority = parseAftnPriority(gatewayin.getPriority());
//        final String addressLine = gatewayin.getAddress();
//
//        if (addressLine == null || addressLine.isEmpty()) {
//            return;
//        }
//
//        final String[] addresses = addressLine.split(" ");
//        for (String address : addresses) {
//            this.addAddressLog(new AddressConversionLog(address));
//        }
//    }

//    public GatewayIn rebuildGatwayInMessage() {
//        final GatewayIn gatewayin = new GatewayIn();
//        gatewayin.setCpa("A");
//        gatewayin.setPriority(this.priority.getValue());
//        gatewayin.setSource("RESUBMIT");
//
//        final StringBuilder builder = new StringBuilder();
//        builder.append("ZCZC TMP123\r\n");
//        builder.append(String.format("%s VVTSAMHS\r\n", this.priority));
//        builder.append(String.format("%s %s\r\n", this.filingTime, this.origin));
//        builder.append(this.content);
//        builder.append("\r\nNNNN");
//
//        gatewayin.setText(builder.toString());
//        gatewayin.setTime(new Date());
//
//        // builder = new StringBuilder();
//        builder.delete(0, builder.length());
//        for (AddressConversionLog add : this.addressLogs) {
//            builder.append(add.getAddress()).append(" ");
//        }
//        gatewayin.setAddress(builder.toString().trim());
//        return gatewayin;
//    }

    public void setConvertedDate(Date date) {
        this.convertedTime = date;
        this.date = dateFormat.format(date);
    }

    public String generateAckMessage() {
        return String.format("R %s %s\r\n", filingTime, origin);
    }

    public String generateUnknowAddressMessage(List<String> unknownAddresses) {
        final String addressLine = buildRefAddress(this.addressLogs);
        final String unknownAddressesLine = joinStr(unknownAddresses);
        final StringBuilder builder = new StringBuilder();
        builder.append(String.format("SVC ADS %s %s\r\n", this.filingTime, this.origin));
        builder.append(String.format("%s %s\r\n", priority, addressLine));
        builder.append(String.format("UNKNOWN %s\r\n", unknownAddressesLine));

        return builder.toString();
    }

    private String buildRefAddress(Set<AddressConversionLog> addressConversionLogs) {
        if (addressConversionLogs == null || addressConversionLogs.isEmpty()) {
            return "";
        }

        final StringBuilder builder = new StringBuilder();
        int index = 1;
        for (AddressConversionLog str : addressConversionLogs) {

            if (index == 1 && !builder.toString().isEmpty()) {
                builder.append(str.getAddress()).append("\r\n");
            }

            if (index < 7) {
                index++;
                builder.append(str.getAddress()).append(" ");
                continue;
            }

            index = 1;
        }

        return builder.toString().trim();
    }

    private String joinStr(List<String> strs) {
        if (strs == null || strs.isEmpty()) {
            return "";
        }

        final StringBuilder builder = new StringBuilder();
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

    private Priority parseAftnPriority(Integer value) {
        if (value == null || value < 0 || value > 4) {
            return null;
        }

        switch (value) {
            case 0:
                return Priority.SS;
            case 1:
                return Priority.DD;
            case 2:
                return Priority.FF;
            case 3:
                return Priority.GG;
            case 4:
                return Priority.KK;
        }

        return null;
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
     * @return the messageId
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * @return the ipmId
     */
    public String getIpmId() {
        return ipmId;
    }

    /**
     * @param ipmId the ipmId to set
     */
    public void setIpmId(String ipmId) {
        this.ipmId = ipmId;
    }

    /**
     * @return the origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
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
     * @return the convertedTime
     */
    public Date getConvertedTime() {
        return convertedTime;
    }

    /**
     * @param convertedTime the convertedTime to set
     */
    public void setConvertedTime(Date convertedTime) {
        this.convertedTime = convertedTime;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
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
     * @return the addressLogs
     */
    public Set<AddressConversionLog> getAddressLogs() {
        return addressLogs;
    }

    /**
     * @param addressLogs the addressLogs to set
     */
    public void setAddressLogs(Set<AddressConversionLog> addressLogs) {
        this.addressLogs = addressLogs;
    }

    public void addAddressLog(AddressConversionLog log) {
        log.setMessageConversionLog(this);
        this.addressLogs.add(log);
    }

    /**
     * @return the parents
     */
    public MessageConversionLog getParents() {
        return parents;
    }

    /**
     * @param parents the parents to set
     */
    public void setParents(MessageConversionLog parents) {
        this.parents = parents;
    }

    /**
     * @return the childs
     */
    public Set<MessageConversionLog> getChilds() {
        return childs;
    }

    /**
     * @param childs the childs to set
     */
    public void setChilds(Set<MessageConversionLog> childs) {
        this.childs = childs;
    }

    public void addChild(MessageConversionLog log) {
        log.setConvertedDate(this.convertedTime);
        log.setParents(this);
        this.childs.add(log);
    }

    /**
     * @return the type
     */
    public MessageType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(MessageType type) {
        this.type = type;
    }

    /**
     * @return the category
     */
    public MessageCategory getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(MessageCategory category) {
        this.category = category;
    }

    /**
     * @return the priority
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * @return the ohi
     */
    public String getOhi() {
        return ohi;
    }

    /**
     * @param ohi the ohi to set
     */
    public void setOhi(String ohi) {
        this.ohi = ohi;
    }

    /**
     * @return the status
     */
    public ConvertResult getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(ConvertResult status) {
        this.status = status;
    }

    /**
     * @return the subjectIpm
     */
    public String getSubjectIpm() {
        return subjectIpm;
    }

    /**
     * @param subjectIpm the subjectIpm to set
     */
    public void setSubjectIpm(String subjectIpm) {
        this.subjectIpm = subjectIpm;
    }
    //</editor-fold>

    @Override
    public int hashCode() {
        return Long.hashCode(this.id);
        // return new Long(id).hashCode();
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MessageConversionLog)) {
            return false;
        }
        MessageConversionLog other = (MessageConversionLog) object;
        if (this.id != other.getId()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.attech.hibernation.entities.MessageConversionLog[ id=" + id + " ]";
    }

    @SuppressWarnings("deprecation")
    @Override
    /* Overriding finalize method to check which object 
     is garbage collected */
    protected void finalize() throws Throwable {
        super.finalize();
        // will print name of object 
//        System.out.println(this.getClass() + " successfully garbage collected");
    }
}
