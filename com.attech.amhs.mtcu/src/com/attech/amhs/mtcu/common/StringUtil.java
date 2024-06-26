/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.common;

import com.attech.amhs.mtcu.config.Config;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author ANDH
 */
public class StringUtil {

    private static final int MESSAGE_SAFE_LIMIT = 1770;

    public static String correctLineLength(String content) {
        final String[] lines = content.split("\r\n");
        // boolean lastEnd = content.endsWith("\r\n");

        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            String tmp = addLinebreaks(lines[i], 69);
            if (i > 0) {
                builder.append("\r\n").append(tmp);
                continue;
            }

            builder.append(tmp);
        }
        return builder.toString();
    }

    public static String addLinebreaks(String input, int maxLineLength) {

        if (input.length() <= maxLineLength) {
            return input;
        }

        final StringTokenizer tok = new StringTokenizer(input, " ");
        final StringBuilder output = new StringBuilder();
        int lineLen = 0;
        while (tok.hasMoreTokens()) {

            String word = tok.nextToken();

            while (word.length() > maxLineLength) {

                output.append(word.substring(0, maxLineLength - lineLen)).append("\r\n");
                word = word.substring(maxLineLength - lineLen);
                lineLen = 0;

            }

            if (lineLen + word.length() - 1 > maxLineLength) {
                output.append("\r\n");
                lineLen = 0;
            }

            if (output.length() > 0) {
                output.append(" ").append(word);
                lineLen += (word.length() + 1);
            } else {
                output.append(word);
                lineLen += word.length();
            }

        }
        return output.toString();
    }
  
    public static String autoCorrectCharacter(String content) {
        // final String alphabetic = Config.instance.getAmhsChannel().getAllowedCharacters();
        // final String symbols = Config.instance.getAmhsChannel().getAllowSymbols();
        boolean isLetterDigit;
        char currentCharacter;
        for (int i = 0; i < content.length(); i++) {
            currentCharacter = content.charAt(i);
            if (currentCharacter == '\n' || currentCharacter == '\r') {
                continue;
            }

            isLetterDigit = Character.isLetterOrDigit(currentCharacter);

            if (isLetterDigit) {
                if (Character.isUpperCase(currentCharacter) && Config.instance.getAmhsChannel().getAllowedCharacters().contains(Character.toString(currentCharacter))) {
                    continue;
                }

                String replace = Character.toString(currentCharacter).toUpperCase();
                replace = Config.instance.getAmhsChannel().getAllowedCharacters().contains(replace) ? replace : "?";
                content = content.replace(Character.toString(currentCharacter), replace);
                continue;
            }

            if (!Config.instance.getAmhsChannel().getAllowSymbols().contains(Character.toString(currentCharacter))) {
                content = content.replace(Character.toString(currentCharacter), "?");
            }
        }
        return content;
    }
    
    public static List<String> splitContent(String content) {
        
        final List<String> retStr = new ArrayList<>();

        if (content.length() <= MESSAGE_SAFE_LIMIT) {
            retStr.add(content);
            return retStr;
        }

        // int safeLimit = 1770;
        /*
        if (content.length() < 1800) {
            List<String> retStr = new ArrayList<>();
            retStr.add(content);
            return retStr;
        }
        */
        
        // String partTemplate = "\r\n// END PART %s/%s //";
        final String[] lines = content.split("\r\n");
        final StringBuilder temp = new StringBuilder();

        // DecimalFormat format = new DecimalFormat("00");
        for (String line : lines) {
            int totalSum = temp.length() + line.length();
            if (totalSum < MESSAGE_SAFE_LIMIT) {
                if (temp.length() > 0) {
                    temp.append("\r\n");
                }
                temp.append(line);
                continue;
            }

            if (temp.length() != 0) {
                retStr.add(temp.toString());
                temp.delete(0, temp.length());
                // temp = new StringBuilder();
            }

            while (line.length() > MESSAGE_SAFE_LIMIT) {
                String splitOut = line.substring(0, MESSAGE_SAFE_LIMIT);
                line = line.substring(MESSAGE_SAFE_LIMIT);
                retStr.add(splitOut);
            }

            if (line.length() > 0) {
                if (temp.length() > 0) {
                    temp.append("\r\n");
                }
                temp.append(line);
            }
        }

        if (temp.length() > 0) {
            retStr.add(temp.toString());
        }

        for (int i = 0; i < retStr.size(); i++) {
            String endOfPart = String.format("\r\n// END PART %s/%s //", (i + 1), retStr.size());
            String str = retStr.get(i);
            str += endOfPart;
            retStr.set(i, str);
        }

        return retStr;
    }

    public static boolean isNumeric(String strNum) {
        return strNum.matches("-?\\d+(\\.\\d+)?");
    }

}
