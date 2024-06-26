/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.dsa;

import java.text.DecimalFormat;
import java.util.Date;

/**
 *
 * @author ANDH
 */
public class AddressConvertResult {

    private String backwardAddress;
    private String convertedAddress;
    private String address;
    private boolean asym;
    private long date;
    private final DecimalFormat format = new DecimalFormat("###,###,###,###,###");

    public AddressConvertResult() {
//        this.date = new Date();
        date = System.currentTimeMillis();
    }

    public AddressConvertResult(String address, String convertedAddress, String backwardAddress) {
        this();
        this.address = address;
        this.backwardAddress = backwardAddress;
        this.convertedAddress = convertedAddress;

    }

    public AddressConvertResult(String address, boolean asym) {
        this();
        this.address = address;
        this.asym = asym;
    }

    public Boolean verifyAsyncAddress() {
        return !this.address.equalsIgnoreCase(this.backwardAddress);
    }

    public boolean isExpireUpdate(long periodInMiliSecond) {
//        System.out.println(" > item: " + this.address + " time: " + format.format(new Date(this.date)) + " period: " + format.format(periodInMiliSecond) + " current: " + format.format(System.currentTimeMillis()));
        return this.date + periodInMiliSecond < System.currentTimeMillis();
    }

    public void reset() {
//        System.out.println("Item: " + this.address + " reset time to "  + );
        this.date = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Address: ").append(address).append("\n");
        builder.append("Result: ").append(convertedAddress).append("\n");
        builder.append("Backward: ").append(backwardAddress).append("\n");
        builder.append("Asyn: ").append(this.verifyAsyncAddress()).append("\n");
        builder.append("Time: ").append(this.date);
        return builder.toString();
    }

    //<editor-fold defaultstate="collapsed" desc="Class Properties">
    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

//    /**
//     * @return the asym
//     */
//    public boolean verifyAsyncAddress() {
//        return asym;
//    }
    /**
     * @param asym the asym to set
     */
    public void setAsym(boolean asym) {
        this.asym = asym;
    }

    /**
     * @return the convertedAddress
     */
    public String getConvertedAddress() {
        return convertedAddress;
    }

    /**
     * @param convertedAddress the convertedAddress to set
     */
    public void setConvertedAddress(String convertedAddress) {
        this.convertedAddress = convertedAddress;
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
    //</editor-fold>
    
    @Override
    public int hashCode() {
        return this.address.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AddressConvertResult)) {
            return false;
        }
        AddressConvertResult other = (AddressConvertResult) object;
        if ((this.address == null && other.getAddress()!= null) || (this.address != null && !this.getAddress().equals(other.getAddress()))) {
            return false;
        }
        return true;
    }

    @Override
    /* Overriding finalize method to check which object 
     is garbage collected */
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println(this.getClass() + " successfully garbage collected");
    }
}
