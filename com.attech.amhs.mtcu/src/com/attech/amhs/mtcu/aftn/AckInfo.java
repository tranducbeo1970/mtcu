/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.aftn;

/**
 *
 * @author ANDH
 */
public class AckInfo {

    private String refTime;
    private String refOriginator;

    public AckInfo() {
    }

    public AckInfo(String time, String origin) {
        this.refTime = time;
        this.refOriginator = origin;
    }

    //<editor-fold defaultstate="collapsed" desc="Class Properties">
    /**
     * @return the refTime
     */
    public String getRefTime() {
        return refTime;
    }

    /**
     * @param refTime the refTime to set
     */
    public void setRefTime(String refTime) {
        this.refTime = refTime;
    }

    /**
     * @return the refOriginator
     */
    public String getRefOriginator() {
        return refOriginator;
    }

    /**
     * @param refOriginator the refOriginator to set
     */
    public void setRefOriginator(String refOriginator) {
        this.refOriginator = refOriginator;
    }
    //</editor-fold>
    
    
}
