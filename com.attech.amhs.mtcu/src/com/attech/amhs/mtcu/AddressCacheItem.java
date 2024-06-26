/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.amhs.mtcu;

import com.attech.amhs.mtcu.isode.RptRecipient;

/**
 *
 * @author ANDH
 */
public class AddressCacheItem {
    private String key;
    private String value;
    private String backwardAddress;
    private boolean asymetric;
    private long lastUpdateTime;
    private long lastUsedTime;
    
    
    public AddressCacheItem() {
    }
    
    public AddressCacheItem(String key, String value, boolean asymetric) {
        this.key = key;
        this.value = value;
        this.asymetric = asymetric;
        this.lastUpdateTime = System.currentTimeMillis();
        this.lastUsedTime = System.currentTimeMillis();
    }
    
    public AddressCacheItem(String key, String value, String backwardAddress, boolean asymetric) {
        this.key = key;
        this.value = value;
        this.asymetric = asymetric;
        this.backwardAddress = backwardAddress;
        this.lastUpdateTime = System.currentTimeMillis();
        this.lastUsedTime = System.currentTimeMillis();
    }
    
    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        
        this.key = key;
    }

    /**
     * @return the value
     */
    public String getValue() {
        // this.lastUsedTime = System.currentTimeMillis();
        return value;
    }
    
    public void resetTime() {
        this.lastUpdateTime = System.currentTimeMillis();
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        
        this.value = value;
    }

    public boolean isExpireUpdate(long period) {
        long current = System.currentTimeMillis();
        return lastUpdateTime + period < current;
    }
    
    public boolean isExpireUsing(long period) {
        long current = System.currentTimeMillis();
        return lastUsedTime + period < current;
    }

    /**
     * @return the asymetric
     */
    public boolean getAsymetric() {
        return asymetric;
    }

    /**
     * @param asymetric the asymetric to set
     */
    public void setAsymetric(boolean asymetric) {
        this.asymetric = asymetric;
    }
    
    @Override
    public String toString() {
        return String.format("key: % value: %s backward: %s asym: %s", key, value, backwardAddress, asymetric);
    }
    
    public String buildKey(String value, boolean isCheckXF) {
	return String.format("@%s;%s$", value, isCheckXF);
    }
    
    /**
     * @return the backwardAddress
     */
    public String getBackwardAddress() {
        return backwardAddress;
    }

    /**
     * @param backwardAddress the backwardAddress to set
     */
    public void setBackwardAddress(String backwardAddress) {
        this.backwardAddress = backwardAddress;
    }
   
    @Override
    public int hashCode() {
        return this.key.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RptRecipient)) {
            return false;
        }
        AddressCacheItem other = (AddressCacheItem) object;
        if ((this.key == null && other.getKey() != null) || (this.key != null && !this.key.equals(other.getKey()))) {
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
