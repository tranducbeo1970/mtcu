/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.amhs.mtcu.common;

/**
 *
 * @author ANDH
 */
public class AftnValidator {

    // private final String headerRegx = "^ZCZC\\s[\\w|&*%]{3}\\d{3,4}(\\s\\d{1,10})?\\s{5}(\\n)*(\\r)*$";
    // private final String addressRegex = "^(SS|DD|GG|HH|KK)(\\s\\w{8}){1,7}(\\n\\w{8}(\\s\\w{8}){0,6}){0,2}$";
    // private final String originatorRegex = "^\\d{6}\\s\\w{8}(\\s\\w*){0,65}$";
    // private static String ORIGIN_PATTERN = "^\\d{6}\\s\\w{8}(.){0,60}$";
    
    
    public static boolean validateOrigin(String origin) {
        // String format = "^\\d{6}\\s\\w{8}(\\s|\\w){0,60}$";
        // final String format = "^\\d{6}\\s\\w{8}(.){0,60}$";
        // return validateFormat(format, origin);
        // return validateFormat(ORIGIN_PATTERN, origin);
        return origin.matches("^\\d{6}\\s\\w{8}(.){0,60}$");
    }

    public static boolean validateOriginFormat(String address) {
        // final String format = "^(SS|DD|FF|GG|KK)(\\s\\w{8}){1,7}(\\n\\w{8}(\\s\\w{8}){0,6}){0,2}$";
        // return validateFormat(format, address);
        return address.matches("^(SS|DD|FF|GG|KK)(\\s\\w{8}){1,7}(\\n\\w{8}(\\s\\w{8}){0,6}){0,2}$");
    }

    public static boolean validateSingleAddressFormat(String address) {
        // final String format = "^[A-Z]{8}$";
        // return validateFormat(format, address);
        return address.matches("^[A-Z0-9]{8}$");
    }

    public static boolean validatePriority(String address) {
        // final String format = "^(SS|DD|FF|GG|KK)$";
        // return validateFormat(format, address);
        return address.matches("^(SS|DD|FF|GG|KK)$");
    }

    public static boolean validateHeaderFormat(String header) {
        // final String format = "^ZCZC\\s[\\w|&*%]{3}\\d{3,4}(\\s\\d{1,10})?\\s{5}(\\n)*(\\r)*$";
        // return validateFormat(format, header);
        return header.matches("^ZCZC\\s[\\w|&*%]{3}\\d{3,4}(\\s\\d{1,10})?\\s{5}(\\n)*(\\r)*$");
    }

    public static boolean validateEnding(String ending) {
        // final String format = "^\\n{0,4}NNNN(\\n)*$";
        // return validateFormat(format, ending);
        return ending.matches("^\\n{0,4}NNNN(\\n)*$");
    }

//    public static boolean validateCharacter(String message) {
//        // final String format = "^ZCZC(\\s)*([A-Z0-9])*(\\n)*(\\r)*$";
//        // return validateFormat(format, message);
//        return message.matches("^ZCZC(\\s)*([A-Z0-9])*(\\n)*(\\r)*$");
//    }

    public static boolean validateHeaderCharacter(String message) {
        return message.matches("^ZCZC\\s([A-Z0-9]|\\&){5,}(\\s[A-Z0-9]{0,})*\\s*$");
    }

    public static boolean validateCidCsnFormat(String message) {
        // final String format = "^([A-Z0-9]|\\&){5,}$";
        // return validateFormat(format, message);
        return message.matches("^([A-Z0-9]|\\&){5,}$");
    }

    public static boolean validateAckMessage(String message) {
        // final String format = "^R\\s(0[1-9]|[1-2][0-9]|3[0-1])([0-1][0-9]|2[0-3])([0-5][0-9])\\s[A-Z]{8}$";
        // return validateFormat(format, message);
        return message.matches("^R\\s(0[1-9]|[1-2][0-9]|3[0-1])([0-1][0-9]|2[0-3])([0-5][0-9])\\s[A-Z]{8}$");
    }

    public static boolean validateCsnCsnFormat(String message) {
        // final String format = "^[0-9]{3,4}$";
        // return validateFormat(format, message);
        return message.matches("^[0-9]{3,4}$");
    }

    public static boolean isChar(String ch) {
        // final String format = "^[A-Z]$";
        //return validateFormat(format, ch);
        return ch.matches("^[A-Z]$");
    }

    public static boolean isNumeric(String ch) {
        // final String format = "^[0-9]$";
        // return validateFormat(format, ch);
        return ch.matches("^[0-9]$");
    }

    public static boolean isCheckMessage(String ch) {
        // final String format = "^CH$";
        // return validateFormat(format, ch);
        return ch.matches("^CH$");
    }

    public static boolean validateDatetime(String value) {
        // final String format = "^(0[1-9]|[1-2][0-9]|3[0-1])([0-1][0-9]|2[0-3])([0-5][0-9])$";
        // return validateFormat(format, value);
        return value.matches("^(0[1-9]|[1-2][0-9]|3[0-1])([0-1][0-9]|2[0-3])([0-5][0-9])$");
    }

    public static boolean validateContent(String value) {
        // final String format = "^([A-Z0-9]|-|\\?|:|\\(|\\)|\\s|\\+|\\.|\\,|\\;|\\=|/)*$";
        // return validateFormat(format, value);
        return value.matches("^([A-Z0-9]|-|\\?|:|\\(|\\)|\\s|\\+|\\.|\\,|\\;|\\=|/)*$");
    }

    public static boolean validateFormat(String format, String str) {
        return str.matches(format);
    }
}
