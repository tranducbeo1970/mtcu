/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "Sender")
@XmlAccessorType(XmlAccessType.NONE)
public class SendingItem {
    @XmlElement(name = "Case")
    private String caseNo;
    
    @XmlElement(name = "Description")
    private String description;
    
    @XmlElement(name = "SendingItem")
    private List<SendingDetailItem> sendingDetails;
    
    public SendingItem() {
        sendingDetails = new ArrayList<>();
    }

    /**
     * @return the caseNo
     */
    public String getCaseNo() {
        return caseNo;
    }

    /**
     * @param caseNo the caseNo to set
     */
    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the sendingDetails
     */
    public List<SendingDetailItem> getSendingDetails() {
        return sendingDetails;
    }

    /**
     * @param sendingDetails the sendingDetails to set
     */
    public void setSendingDetails(List<SendingDetailItem> sendingDetails) {
        this.sendingDetails = sendingDetails;
    }
    
    
    
    
}
