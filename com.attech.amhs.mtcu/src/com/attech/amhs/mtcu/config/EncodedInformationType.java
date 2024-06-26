/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.amhs.mtcu.config;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */

@XmlRootElement(name = "EncodedInformationType")
@XmlAccessorType(XmlAccessType.NONE)
public class EncodedInformationType {
    
    @XmlAttribute(name = "AllowEmpty")
    private boolean allowEmpty;
    
    @XmlElement(name = "Encode")
    private List<String> encodedType;
    
    public EncodedInformationType() {
        allowEmpty = false;
        encodedType = new ArrayList<>();
    }

    /**
     * @return the allowEmpty
     */
    public boolean isAllowEmpty() {
        return allowEmpty;
    }

    /**
     * @param allowEmpty the allowEmpty to set
     */
    public void setAllowEmpty(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }
    
    public void setEncodedType(String set) {
        this.getEncodedType().add(set);
    }

    /**
     * @return the encodedType
     */
    public List<String> getEncodedType() {
        return encodedType;
    }

    /**
     * @param encodedType the encodedType to set
     */
    public void setEncodedType(List<String> encodedType) {
        this.encodedType = encodedType;
    }
}
