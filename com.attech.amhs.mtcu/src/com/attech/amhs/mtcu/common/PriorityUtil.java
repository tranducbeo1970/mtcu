/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.amhs.mtcu.common;

import com.isode.x400api.X400_att;

/**
 *
 * @author ANDH
 */
public class PriorityUtil {
    
    /**
     * Convert AFTN priority to AMHS priority
     * @param aftnPrio
     * @return 
     */
    public static int toAMHSPriority(String aftnPrio) {
        switch (aftnPrio) {
            case "SS":
                return X400_att.X400_PRIORITY_URGENT; // MtAttributes.PRIORITY_URGENT;
            case "DD":
                return X400_att.X400_PRIORITY_NORMAL;
            case "FF":
                return X400_att.X400_PRIORITY_NORMAL;
            case "GG":
                return X400_att.X400_PRIORITY_NON_URGENT;
            case "KK":
                return X400_att.X400_PRIORITY_NON_URGENT;
        }
        return X400_att.X400_PRIORITY_ANY;
    }
    
    /**
     * Convert priority from string to integer
     * @param aftnPrio
     * @return 
     */
    public static int toInteger(String aftnPrio) {
        switch (aftnPrio) {
            case "SS":
                return 0;
            case "DD":
                return 1;
            case "FF":
                return 2;
            case "GG":
                return 3;
            case "KK":
                return 4;
        }
        return 4;
    }
    
    /**
     * Convert priority from integer to string
     * @param value
     * @return 
     */
    public static String toString(int value) {
        switch (value) {
            case 0:
                return "SS";
            case 1:
                return "DD";
            case 2:
                return "FF";
            case 3:
                return "GG";
            case 4:
                return "KK";
        }
        return "KK";
    }
    
    public static Integer toPrecedence(String value) {
        switch (value) {
           case "SS":
                return 107;
            case "DD":
                return 71;
            case "FF":
                return 57;
            case "GG":
                return 28;
            case "KK":
                return 14;
        }
        return null;
    }
}
