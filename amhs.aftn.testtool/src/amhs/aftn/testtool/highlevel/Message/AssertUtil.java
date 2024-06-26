/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import amhs.aftn.Message;
import java.util.List;

/**
 *
 * @author andh
 * 
 */
public class AssertUtil {
    
    public static boolean equal(NormalMessage expected, NormalMessage actual) {
        if (expected == null && actual == null) return true;
        if (expected == null || actual == null) return false;
        if (!evaluate(expected.getDlExpasionProhibited(), actual.getDlExpasionProhibited(), "Checking originator")) return false;
        if (!evaluate(expected.getContentType(), actual.getContentType(), "Checking Content Type")) return false;
        if (!evaluate(expected.getConversionProhibited(), actual.getConversionProhibited(), "Checking Conversion Prohibited")) return false;
        if (!evaluate(expected.getConversionWithLossProhibited(), actual.getConversionWithLossProhibited(), "Checking Conversion with loss")) return false;
        if (!evaluate(expected.getOriginEIT(), actual.getOriginEIT(), "Checking OEIT")) return false;
        if (!evaluate(expected.getPriority(), actual.getPriority(), "Checking priority")) return false;
        if (!equal(expected.getAts(), actual.getAts())) return false;
        return equalRecipient(expected.getRecipients(), actual.getRecipients());
    }
    
    private static boolean equal(ATS expected, ATS actual) {
        if (expected == null && actual == null) return true;
        if (expected == null || actual == null) return false;
        if (!evaluate(expected.getEohMode(), expected.getEohMode(), "Checking ATS EohMode")) return false;
        if (!evaluate(expected.getExtended(), expected.getExtended(), "Checking ATS Extended")) return false;
        if (!evaluate(expected.getFilingTime(), expected.getFilingTime(), "Checking ATS Filing Time")) return false;
        if (!evaluate(expected.getOptionalHeading(), expected.getOptionalHeading(), "Checking ATS Optional Heading info")) return false;
        if (!evaluate(expected.getPriority(), expected.getPriority(), "Checking ATS Priority")) return false;
        if (!evaluate(expected.getText(), expected.getText(), "Checking ATS Text")) return false;
        return true;
    }
    
    public static boolean equal(Report expected, Report actual) {
        if (expected == null && actual == null) return true;
        if (expected == null || actual == null) return false;
        
        if (!evaluate(expected.getOriginator(), actual.getOriginator(), "Checking originator")) return false;
        if (!evaluate(expected.getOriginEIT(), actual.getOriginEIT(), "Checking original encoded-information-type")) return false;
        if (!evaluate(expected.getContentID(), actual.getContentID(), "Checking Content ID")) return false;
        if (!evaluate(expected.getContentType(), actual.getContentType(), "Checking Content Type")) return false;
        return equalReportRecipient(expected.getRecipients(), actual.getRecipients());
    }
    
    public static boolean equalReportRecipient(List<ReportRecipient> expected, List<ReportRecipient> actual) {
        if (expected == null && actual == null) return true;
        if (expected == null || actual == null) return false;
        if (expected.size() !=  actual.size()) return false;
        int number = expected.size();
        for (int i = 0; i < number; i ++){
            boolean result = equalReportRecipient(expected.get(i), actual.get(i));
            Out.print(String.format("Checking Recipient [%s]", i), result);
            if (!result) return false;
        }
        
        return true;
    }
    
    public static boolean equalReportRecipient(ReportRecipient expected, ReportRecipient actual) {
        if (!evaluate(expected.getOr(), actual.getOr(), "Checking originator address")) return false;
        if (!evaluate(expected.getArrivalTime(), actual.getArrivalTime(), "Checking Arrival Time")) return false;
        if (!evaluate(expected.getSupplementInfo(), actual.getSupplementInfo(), "Checking Supplement Info")) return false;
        if (!evaluate(expected.getUserType(), actual.getUserType(), "Checking User type")) return false;
        if (!evaluate(expected.getNonDeliveryDiagnosticCode(), actual.getNonDeliveryDiagnosticCode(), "Checking Non Delivery Diagnostic Code")) return false;
        if (!evaluate(expected.getNonDeliveryReasonCode(), actual.getNonDeliveryReasonCode(), "Checking Non Delivery Code")) return false;
        if (!evaluate(expected.getDeliverTime(), actual.getDeliverTime(), "Checking Delivered Time")) return false;
        return true;
    }
    
    private static boolean equalReportRecipient(Recipient expected, Recipient actual) {
        if (expected == null && actual == null) return true;
        if (expected == null || actual == null) return false;
        return evaluate(expected.getOr(), actual.getOr(), "Checking recipient address");
    }
    
    private static boolean equalRecipient(List<Recipient> expected, List<Recipient> actual) {
        if (expected == null && actual == null) return true;
        if (expected == null || actual == null) return false;
        if (!evaluate(expected.size(), actual.size(), "Checking recipient count")) return false;
        for (int i = 0; i< expected.size(); i++) {
            System.out.println("> Checking recipient #" + i + "/" + expected.size());
            Recipient expectedRcip = expected.get(i);
            Recipient actualRecip = actual.get(i);
            if (!equalReportRecipient(expectedRcip, actualRecip)) return false;
        }
        return true;
    }
    
    public static boolean equalAFTN(Message expected, Message actual) {
        if (expected == null && actual == null) return true;
        if (expected == null || actual == null) return false;
        
        if (!evaluate(expected.getPriority(), actual.getPriority(), "Checking Priority")) return false;
        if (!evaluate(expected.getFilingTime(), actual.getFilingTime(), "Checking FilingTime")) return false;
        if (!evaluate(expected.getOriginator(), actual.getOriginator(), "Checking Originator")) return false;
        if (!evaluate(expected.getAdditionInfo(), actual.getAdditionInfo(), "Checking OHI")) return false;
        if (!evaluate(expected.getText(), actual.getText(), "Checking Content")) return false;
        
//        print("Checking Priority");
//        if (!equal(expected.getPriority(), actual.getPriority())) {
//            print(false);
//            System.out.println(String.format("Expected [%s] <> Result [%s]", expected.getPriority(), actual.getPriority()));
//            return false;
//        }
//        print(true);
//        
//        print("Checking FilingTime  ");
//        if (!equal(expected.getFilingTime(), actual.getFilingTime())) {
//            print(false);
//            System.out.println(String.format("Expected [%s] <> Result [%s]", expected.getFilingTime(), actual.getFilingTime()));
//            return false;
//        }
//        print(true);
//
//        print("Checking Originator  ");
//        if (!equal(expected.getOriginator(), actual.getOriginator())) {
//            print(false);
//            System.out.println(String.format("Expected [%s] <> Result [%s]", expected.getOriginator(), actual.getOriginator()));
//            return false;
//        }
//        print(true);
//        
//        
//        print("Checking OHI  ");
//        if (!equal(expected.getAdditionInfo(), actual.getAdditionInfo())) {
//            print(false);
//            System.out.println(String.format("Expected [%s] <> Result [%s]", expected.getAdditionInfo(), actual.getAdditionInfo()));
//            return false;
//        }
//        print(true);
//
//        print("Checking Text");
//        
//        if (!equal(expected.getText(), actual.getText())) {
//            print(false);
//            System.out.println(String.format("Expected [%s] <> Result [%s]", expected.getText(), actual.getText()));
//            return false;
//        }
//        print(true);
        return true;
    }
    
    private static boolean equal(String expected, String actual) {
        if (expected == null && actual == null) return true;
        if (expected == null || actual == null) return false;
        expected = decode(expected);
        actual = decode(actual);
        return expected.equals(actual);
    }
    
    private static boolean equal(Integer expected, Integer actual) {
        if (expected == null && actual == null) return true;
        if (expected == null || actual == null) return false;
        return expected.equals(actual);
    }
    
    private static boolean evaluate(String expected, String actual, String message) {
        Out.print(message);
        if (expected != null && expected.equals("[NN]") && actual != null && !actual.isEmpty()) {
            Out.print(true);
            return true;
        }
        // expected = decode(expected);
        expected = encode(expected);
        actual = encode(actual);
        // actual = decode(actual);
        boolean result = equal(expected, actual);
        Out.print(result);
        if (!result) System.out.println(String.format("Expect [%s] Actual [%s]", expected, actual));
        return result;
    }
    
    private static boolean evaluate(Integer expected, Integer actual, String message) {
        Out.print(message);
        boolean result = equal(expected, actual);
        Out.print(result);
        if (!result) System.out.println(String.format("Expect [%s] Actual [%s]", expected, actual));
        return result;
    }
    
    private static boolean equal(List<String> str1, List<String> str2) {
        if (str1 == null && str2 == null) return true;
        if (str1.size() != str2.size()) return false;
        for (String str : str1) {
            if (str2.contains(str))continue;
            return false;
        }
        
         for (String str : str2) {
            if (str1.contains(str))continue;
            return false;
        }
        return true;
    }
    
    private static String decode(String str) {
        if (str == null) return str;
        return str.replace("[r]", "\r").replace("[n]", "\n");
    }
    
    private static String encode(String str) {
        if (str == null) return str;
        return str.replace("\r", "[r]").replace("\n", "[n]").replace(" ", "[s]");
    }
}
