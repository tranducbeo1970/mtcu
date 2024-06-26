/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "Account")
@XmlAccessorType(XmlAccessType.NONE)
public class Account {
    
    @XmlElement(name = "Name")
    private String name;
    
    @XmlElement(name = "OR")
    private String or;
    
    @XmlElement(name = "PresentationAddress")
    private String presentationAddress;
    
    @XmlElement(name = "MailBox")
    private String mailbox;
    
    @XmlElement(name = "Password")
    private String password;

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
     * @return the presentationAddress
     */
    public String getPresentationAddress() {
        return presentationAddress;
    }

    /**
     * @param presentationAddress the presentationAddress to set
     */
    public void setPresentationAddress(String presentationAddress) {
        this.presentationAddress = presentationAddress;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the mailbox
     */
    public String getMailbox() {
        return mailbox;
    }

    /**
     * @param mailbox the mailbox to set
     */
    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }
    
}
