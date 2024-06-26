/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.config;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "AddressConversion")
@XmlAccessorType(XmlAccessType.NONE)
public class AddressConversion implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3790241623584706280L;

    @XmlElement(name = "DsaPresentationAddress")
    private String dsaPresentationAdd;

    @XmlElement(name = "DirectoryName")
    private String dnName;

    @XmlElement(name = "UserLookupTable")
    private String userLookupTable;

    @XmlElement(name = "CacheEnable")
    private boolean cacheEnable;

    @XmlElement(name = "CacheExpire")
    private Long expiredPeriod;

    @XmlElement(name = "CacheUpdate")
    private Long updatedPeriod;

    @XmlElement(name = "CacheMaintain")
    private Long maintainPeriod;

    @XmlElement(name = "RetryConnectPeriod")
    private long retryConnectPeriod;

    @XmlElement(name = "AftnLookupTable")
    private String aftbLookupTable;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Dsa Presentation Address: ").append(this.dsaPresentationAdd);
        builder.append("Directory Name: ").append(this.dnName);
        return builder.toString();
    }

    //<editor-fold defaultstate="collapsed" desc="Class Properties">
    /**
     * @return the dsaPresentationAdd
     */
    public String getDsaPresentationAdd() {
        return dsaPresentationAdd;
    }

    /**
     * @param dsaPresentationAdd the dsaPresentationAdd to set
     */
    public void setDsaPresentationAdd(String dsaPresentationAdd) {
        this.dsaPresentationAdd = dsaPresentationAdd;
    }

    /**
     * @return the dnName
     */
    public String getDnName() {
        return dnName;
    }

    /**
     * @param dnName the dnName to set
     */
    public void setDnName(String dnName) {
        this.dnName = dnName;
    }

    /**
     * @return the userLookupTable
     */
    public String getUserLookupTable() {
        return userLookupTable;
    }

    /**
     * @param userLookupTable the userLookupTable to set
     */
    public void setUserLookupTable(String userLookupTable) {
        this.userLookupTable = userLookupTable;
    }

    /**
     * @return the expiredPeriod
     */
    public Long getExpiredPeriod() {
        return expiredPeriod;
    }

    /**
     * @param expiredPeriod the expiredPeriod to set
     */
    public void setExpiredPeriod(Long expiredPeriod) {
        this.expiredPeriod = expiredPeriod;
    }

    /**
     * @return the updatedPeriod
     */
    public Long getUpdatedPeriod() {
        return updatedPeriod;
    }

    /**
     * @param updatedPeriod the updatedPeriod to set
     */
    public void setUpdatedPeriod(Long updatedPeriod) {
        this.updatedPeriod = updatedPeriod;
    }

    /**
     * @return the maintainPeriod
     */
    public Long getMaintainPeriod() {
        return maintainPeriod;
    }

    /**
     * @param maintainPeriod the maintainPeriod to set
     */
    public void setMaintainPeriod(Long maintainPeriod) {
        this.maintainPeriod = maintainPeriod;
    }

    /**
     * @return the retryConnectPeriod
     */
    public long getRetryConnectPeriod() {
        return retryConnectPeriod;
    }

    /**
     * @param retryConnectPeriod the retryConnectPeriod to set
     */
    public void setRetryConnectPeriod(long retryConnectPeriod) {
        this.retryConnectPeriod = retryConnectPeriod;
    }

    /**
     * @return the aftbLookupTable
     */
    public String getAftbLookupTable() {
        return aftbLookupTable;
    }

    /**
     * @param aftbLookupTable the aftbLookupTable to set
     */
    public void setAftbLookupTable(String aftbLookupTable) {
        this.aftbLookupTable = aftbLookupTable;
    }

    /**
     * @return the cacheEnable
     */
    public boolean isCacheEnable() {
        return cacheEnable;
    }

    /**
     * @param cacheEnable the cacheEnable to set
     */
    public void setCacheEnable(boolean cacheEnable) {
        this.cacheEnable = cacheEnable;
    }
    //</editor-fold>
}
