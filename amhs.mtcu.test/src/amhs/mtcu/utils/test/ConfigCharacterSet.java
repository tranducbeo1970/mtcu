/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.mtcu.utils.test;

import amhs.mtcu.config.EncodedInformationType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "Config")
@XmlAccessorType(XmlAccessType.NONE)
public class ConfigCharacterSet {
    
    @XmlElement(name = "AllowedCharacterSet")
    private EncodedInformationType characterSet;

    /**
     * @return the characterSet
     */
    public EncodedInformationType getCharacterSet() {
        return characterSet;
    }

    /**
     * @param characterSet the characterSet to set
     */
    public void setCharacterSet(EncodedInformationType characterSet) {
        this.characterSet = characterSet;
    }
}
