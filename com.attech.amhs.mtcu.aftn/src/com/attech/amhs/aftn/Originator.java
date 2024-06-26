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
public class Originator {
    private String filingTime;
    private String originator;
    private boolean priorityAlarm;
    private String optionalHeadingInformation;

    /**
     * @return the originator
     */
    public String getOriginator() {
        return originator;
    }

    /**
     * @param originator the originator to set
     */
    public void setOriginator(String originator) {
        this.originator = originator;
    }

    /**
     * @return the priorityAlarm
     */
    public boolean isPriorityAlarm() {
        return priorityAlarm;
    }

    /**
     * @param priorityAlarm the priorityAlarm to set
     */
    public void setPriorityAlarm(boolean priorityAlarm) {
        this.priorityAlarm = priorityAlarm;
    }

    /**
     * @return the optionalHeadingInformation
     */
    public String getOptionalHeadingInformation() {
        return optionalHeadingInformation;
    }

    /**
     * @param optionalHeadingInformation the optionalHeadingInformation to set
     */
    public void setOptionalHeadingInformation(String optionalHeadingInformation) {
        this.optionalHeadingInformation = optionalHeadingInformation;
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
    
}
