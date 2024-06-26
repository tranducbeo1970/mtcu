package com.attech.amhs.mtcu.common;

import com.attech.amhs.mtcu.MtAttributes;
import com.attech.amhs.mtcu.config.Config;

/**
 *
 * @author ANDH
 */
public class AmhsValidator {

    public static int validateCharacter(String content) {

        final String alphabetic = Config.instance.getAmhsChannel().getAllowedCharacters();
        final String symbols = Config.instance.getAmhsChannel().getAllowSymbols();
        boolean validCharacter = true;
        boolean validSymbol = true;

        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c == '\n' || c == '\r') {
                continue;
            }
            boolean isLetterDigit = Character.isLetterOrDigit(c);

            if (isLetterDigit) {
                if (!alphabetic.contains(Character.toString(c))) {
                    validCharacter = false;
                }
                continue;
            }

            if (!symbols.contains(Character.toString(c))) {
                validSymbol = false;
            }
        }

        if (!validCharacter && !validSymbol) {
            return MtAttributes.E_CONTENT_CONTAIN_INVALID_INFO;
        }

        if (!validCharacter) {
            return MtAttributes.E_CONTENT_CONTAIN_INVALID_CHARACTER;
        }

        if (!validSymbol) {
            return MtAttributes.E_CONTENT_CONTAIN_INVALID_SYMBOLS;
        }

        return MtAttributes.E_NO_ERROR;
    }

    public static boolean validateContentLine(String content) {
        final String[] lines = content.split("\r\n");
        for (String line : lines) {
            if (line.length() > 69) {
                return false;
            }
        }

        return true;
    }

    public static boolean validatePriority(String address) {
        if (address == null || address.isEmpty()) {
            return false;
        }
        final String format = "^(SS|DD|FF|GG|KK)$";
        return validateFormat(format, address);
    }

    public static boolean validateOHI(String ohi, String priority) {
        if (ohi == null || ohi.isEmpty()) {
            return true;
        }

        if ((priority.equals("SS") && ohi.length() > 48) || ohi.length() > 53) {
            return false;
        }

        return true;
    }

    public static boolean validateFilingTime(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        final String format = "^(0[1-9]|[1-2][0-9]|3[0-1])([0-1][0-9]|2[0-3])([0-5][0-9])$";
        return validateFormat(format, value);
    }

    public static boolean validateFormat(String format, String str) {
        return str.matches(format);
    }

}
