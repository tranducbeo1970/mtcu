/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
public class HighLevelSendingConfig  implements Serializable {
    
    // P3 account
    @XmlElement(name = "P3PresentationAddress")
    private String p3PresentationAddress;
    
    @XmlElement(name = "P3Password")
    private String p3Password;
    
    // P7 Account
    @XmlElement(name = "P7PresentationAddress")
    private String p7PresentationAddress;
    
    @XmlElement(name = "P7Password")
    private String p7Password;
    
    // Address
    @XmlElement(name = "Address")
    private List<String> addresses;
    
    /**
     * Contructor
     */
    public HighLevelSendingConfig() {
        addresses = new ArrayList<>();
    }

    /**
     * @return the p3PresentationAddress
     */
    public String getP3PresentationAddress() {
        return p3PresentationAddress;
    }

    /**
     * @param p3PresentationAddress the p3PresentationAddress to set
     */
    public void setP3PresentationAddress(String p3PresentationAddress) {
        this.p3PresentationAddress = p3PresentationAddress;
    }

    /**
     * @return the p3Password
     */
    public String getP3Password() {
        return p3Password;
    }

    /**
     * @param p3Password the p3Password to set
     */
    public void setP3Password(String p3Password) {
        this.p3Password = p3Password;
    }

    /**
     * @return the p7PresentationAddress
     */
    public String getP7PresentationAddress() {
        return p7PresentationAddress;
    }

    /**
     * @param p7PresentationAddress the p7PresentationAddress to set
     */
    public void setP7PresentationAddress(String p7PresentationAddress) {
        this.p7PresentationAddress = p7PresentationAddress;
    }

    /**
     * @return the p7Password
     */
    public String getP7Password() {
        return p7Password;
    }

    /**
     * @param p7Password the p7Password to set
     */
    public void setP7Password(String p7Password) {
        this.p7Password = p7Password;
    }

    /**
     * @return the addresses
     */
    public List<String> getAddresses() {
        return addresses;
    }

    /**
     * @param addresses the addresses to set
     */
    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }
    
    /**
     * Add address to list
     * @param address 
     */
    public void addAddress(String address) {
        this.addresses.add(address);
    }
    
    /**
     * Convert address
     * @return 
     */
    public String convertAddress() {
        StringBuilder buidler = new StringBuilder();
        for (String add : this.addresses) {
            buidler.append(add).append("\n");
        }
        return buidler.toString();
    }
    
    /**
     * Parsing address from String
     * @param str 
     */
    public void parseAddress(String str) {
        String [] adds = str.split("\n");
        this.addresses.clear();
        this.addresses.addAll(Arrays.asList(adds));
    }
    
    
}
