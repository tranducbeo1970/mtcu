/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "SendingDetail")
@XmlAccessorType(XmlAccessType.NONE)
public class SendingDetailItem {
    
    @XmlAttribute(name = "No")
    private String messageName;
    
    @XmlAttribute(name = "Path")
    private String path;
    
    @XmlAttribute(name = "Type")
    private String sendingMode;
    
    @XmlAttribute(name = "Account")
    private String accountName;
    
    @XmlAttribute(name = "Remark")
    private String remark;
    
    /**
     * @return the messageName
     */
    public String getMessageName() {
        return messageName;
    }

    /**
     * @param messageName the messageName to set
     */
    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the sendingMode
     */
    public String getSendingMode() {
        return sendingMode;
    }

    /**
     * @param sendingMode the sendingMode to set
     */
    public void setSendingMode(String sendingMode) {
        this.sendingMode = sendingMode;
    }

    /**
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param accountName the accountName to set
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
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
}
