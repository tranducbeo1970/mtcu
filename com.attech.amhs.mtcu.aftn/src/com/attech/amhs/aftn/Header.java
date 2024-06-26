/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.aftn;

/**
 *
 * @author andh
 */
public class Header {
    
    private final String START_SIGNAL = "ZCZC";
    private String transmitID;
    private String additionServiceIndication;
    
    public Header() {
    }
    
    public Header(String transmitID, String additionalServiceIndication) {
        this.transmitID = transmitID;
        this.additionServiceIndication = additionalServiceIndication;
    }
    
    @Override
    public String toString() {
        return String.format("%s %s %s", START_SIGNAL, get(transmitID), get(additionServiceIndication)).replace("  ", " ");
    }
    
    public String get(String str) {
        return str == null ? "" : str;
    }

    /**
     * @return the transmitID
     */
    public String getTransmitID() {
        return transmitID;
    }

    /**
     * @param transmitID the transmitID to set
     */
    public void setTransmitID(String transmitID) {
        this.transmitID = transmitID;
    }

    /**
     * @return the additionServiceIndication
     */
    public String getAdditionServiceIndication() {
        return additionServiceIndication;
    }

    /**
     * @param additionServiceIndication the additionServiceIndication to set
     */
    public void setAdditionServiceIndication(String additionServiceIndication) {
        this.additionServiceIndication = additionServiceIndication;
    }
}
