/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.attech.amhs.mtcu.isode;

/**
 *
 * @author root
 */
public class dsaChannelData {

    /**
     * @return the mhsORAddresses
     */
    public String getMhsORAddresses() {
        return mhsORAddresses;
    }

    /**
     * @param mhsORAddresses the mhsORAddresses to set
     */
    public void setMhsORAddresses(String mhsORAddresses) {
        this.mhsORAddresses = mhsORAddresses;
    }

    /**
     * @return the atn_amhs_direct_access
     */
    public boolean isAtn_amhs_direct_access() {
        return atn_amhs_direct_access;
    }

    /**
     * @param atn_amhs_direct_access the atn_amhs_direct_access to set
     */
    public void setAtn_amhs_direct_access(boolean atn_amhs_direct_access) {
        this.atn_amhs_direct_access = atn_amhs_direct_access;
    }

    /**
     * @return the atn_ipm_heading_extensions
     */
    public boolean isAtn_ipm_heading_extensions() {
        return atn_ipm_heading_extensions;
    }

    /**
     * @param atn_ipm_heading_extensions the atn_ipm_heading_extensions to set
     */
    public void setAtn_ipm_heading_extensions(boolean atn_ipm_heading_extensions) {
        this.atn_ipm_heading_extensions = atn_ipm_heading_extensions;
    }
    
    private String mhsORAddresses;
    private boolean atn_amhs_direct_access;
    private boolean atn_ipm_heading_extensions;
    
}
