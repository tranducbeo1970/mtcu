/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.config;

import com.attech.amhs.mtcu.isode.RptRecipient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "Code")
@XmlAccessorType(XmlAccessType.NONE)
public class CodeValue {

    @XmlAttribute(name = "Code")
    private int code;

    @XmlAttribute(name = "Description")
    private String value;

    public CodeValue() {
    }

    public CodeValue(int code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return this.code;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RptRecipient)) {
            return false;
        }
        CodeValue other = (CodeValue) object;
        if (this.code != other.getCode()) {
//        if ((this.code == null && other.getCla!= null) || (this.key != null && !this.getKey().equals(other.getKey()))) {
            return false;
        }
        return true;
    }

    @Override
    /* Overriding finalize method to check which object 
     is garbage collected */
    protected void finalize() throws Throwable {
        super.finalize();
//        System.out.println(this.getClass() + " successfully garbage collected");
    }
}
