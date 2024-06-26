/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.common;

/**
 *
 * @author ANDH
 */
public class AddressUtil {

    /**
     * Get short name of an address
     * @param address
     * @return short form of address (8 characters)
     */
    public static String getShort(String address) {
        if (address == null || address.isEmpty()) {
            return null;
        }
        
        int index = address.indexOf("CN=");
        if (index >= 0) {
            index += 3;
            int end = address.indexOf("/", index);
            if (end < index) {
                return address.substring(index);
            }

            return address.substring(index, end);
        }

        index = address.indexOf("OU=");
        if (index >= 0) {
            index += 3;
            int end = address.indexOf("/", index);
            if (end < index) {
                return address.substring(index);
            }

            return address.substring(index, end);
        }

        return null;
    }
}
