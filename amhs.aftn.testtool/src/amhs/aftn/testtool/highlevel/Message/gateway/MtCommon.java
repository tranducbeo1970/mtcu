/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amhs.aftn.testtool.highlevel.Message.gateway;

import com.isode.x400api.BodyPart;
import com.isode.x400api.DLExpHist;
import com.isode.x400api.InternalTraceinfo;
import com.isode.x400api.Message;
import com.isode.x400api.Recip;
import com.isode.x400api.Traceinfo;
import com.isode.x400api.X400_att;
import com.isode.x400mtapi.MTMessage;
import com.isode.x400mtapi.X400mt;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.apache.log4j.Logger;

/**
 *
 * @author andh
 */
public class MtCommon {

    public static Logger logger = Logger.getLogger(MtCommon.class);

    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd.HHmmss");
    private final static SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyMMddHHmmss");
    private final static SimpleDateFormat authorizedFormat = new SimpleDateFormat("yyMMddHHmmss");
    private final static SimpleDateFormat filingTime = new SimpleDateFormat("ddHHmm");
    private final static SimpleDateFormat ipmDateFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
    
    private final static String tracingStr = "Set attribute %s value %s fail with code %s";
    
    public static void set(MTMessage mt, int attribute, Integer value) {
        set(mt, attribute, value, 0);
    }

    public static void set(MTMessage mt, int attribute, Integer value, Integer mode) {
        if (value == null) return;
        
        int status = X400mt.x400_mt_msgaddintparam(mt, attribute, value);
        
        if (mode == 0 && status == X400_att.X400_E_NOERROR) return;
        logger.info(String.format(tracingStr, AttributeDic.getInstance().get(attribute), value, status));
    }

    public static void set(MTMessage mt, int attribute, String value) {
        set(mt, attribute, value, 0);
    }
    
    public static void set(MTMessage mt, int attribute, String value, Integer mode) {
        if (value == null || value.isEmpty()) return;

        int status = X400mt.x400_mt_msgaddstrparam(mt, attribute, value, value.length());

        if (mode == 0 && status == X400_att.X400_E_NOERROR) return;
        logger.info(String.format(tracingStr, AttributeDic.getInstance().get(attribute), value, status));
    }
    
    public static void set(MTMessage mt, int attribute, String value, int length) {
        set(mt, attribute, value, length, 0);
    }
    
    public static void set(MTMessage mt, int attribute, String value, int length, Integer mode) {
        if (value == null || value.isEmpty()) return;

        int status = X400mt.x400_mt_msgaddstrparam(mt, attribute, value, length);
        
        if (mode == 0 && status == X400_att.X400_E_NOERROR) return;
        logger.info(String.format(tracingStr, AttributeDic.getInstance().get(attribute), value, status));
    }

    
    
    
    
    public static void set(Message mt, int attribute, String value) {
        set(mt, attribute, value, 0);
    }
    
    public static void set(Message mt, int attribute, Integer value, Integer mode) {
        if (value == null) return;
        int status = com.isode.x400api.X400.x400_msgaddintparam(mt, attribute, value);
        
        if (mode == 0 && status == X400_att.X400_E_NOERROR) return;
        logger.info(String.format(tracingStr, AttributeDic.getInstance().get(attribute), value, status));
    }
    
    public static void set(Message mt, int attribute, Integer value) {
        set(mt, attribute, value, 0);
    }
    
    public static void set(Message mt, int attribute, String value, Integer mode) {
        if (value == null || value.isEmpty()) return;

        int status = com.isode.x400api.X400.x400_msgaddstrparam(mt, attribute, value, value.length());

        if (mode == 0 && status == X400_att.X400_E_NOERROR) return;
        logger.info(String.format(tracingStr, AttributeDic.getInstance().get(attribute), value, status));
    }
    

    
    
    
    public static void set(Traceinfo traceObject, int attribute, String value, int length, Integer mode) {
        if (value == null || value.isEmpty())  return;

        int status = com.isode.x400api.X400.x400_traceinfoaddstrparam(traceObject, attribute, value, length);
        
        if (mode == 0 && status == X400_att.X400_E_NOERROR) return;
        logger.info(String.format(tracingStr, AttributeDic.getInstance().get(attribute), value, status));
    }
    
    public static void set(Traceinfo traceObject, int attribute, String value, int length) {
        set(traceObject, attribute, value, length, 0);
    }

    public static void set(Traceinfo traceObject, int attribute, Integer value, Integer mode) {
        if (value == null) return;

        int status = com.isode.x400api.X400.x400_traceinfoaddintparam(traceObject, attribute, value);
        
        if (mode == 0 && status == X400_att.X400_E_NOERROR) return;
        logger.info(String.format(tracingStr, AttributeDic.getInstance().get(attribute), value, status));
    }
    
    public static void set(Traceinfo traceObject, int attribute, Integer value) {
        set(traceObject, attribute, value, 0);
    }
    

    
    
    public static void set(InternalTraceinfo traceObject, int attribute, String value, int length, Integer mode) {
        if (value == null || value.isEmpty()) return;

        int status = com.isode.x400api.X400.x400_internaltraceinfoaddstrparam(traceObject, attribute, value, length);

        if (mode == 0 && status == X400_att.X400_E_NOERROR) return;
        logger.info(String.format(tracingStr, AttributeDic.getInstance().get(attribute), value, status));
    }
    
    public static void set(InternalTraceinfo traceObject, int attribute, String value, int length) { 
        set(traceObject, attribute, value, length, 0);
    }

    public static void set(InternalTraceinfo traceObject, int attribute, Integer value, Integer mode) {
        if (value == null) return;

        int status = com.isode.x400api.X400.x400_internaltraceinfoaddintparam(traceObject, attribute, value);

        if (mode == 0 && status == X400_att.X400_E_NOERROR) return;
        logger.info(String.format(tracingStr, AttributeDic.getInstance().get(attribute), value, status));
    }

    public static void set(InternalTraceinfo traceObject, int attribute, Integer value) {
        set(traceObject, attribute, value, 0);
    }
    
    
    
    
    
    
    
    

    public static void set(Recip recipObj, int attribute, String value, int length, Integer mode) {
        if (value == null || value.isEmpty()) return;
        int status = com.isode.x400mtapi.X400mt.x400_mt_recipaddstrparam(recipObj, attribute, value, length);
        
        if (mode == 0 && status == X400_att.X400_E_NOERROR) return;
        logger.info(String.format(tracingStr, AttributeDic.getInstance().get(attribute), value, status));
    }
    
    public static void set(Recip recipObj, int attribute, String value, int length) {
        set(recipObj, attribute, value, length, 0);
    }

    public static void set(Recip recipObj, int attribute, String value) {
        set(recipObj, attribute, value, -1, 0);
    }

    public static void set(Recip recipObj, int attribute, Integer value, Integer mode) {
        if (value == null) return;

        int status = com.isode.x400mtapi.X400mt.x400_mt_recipaddintparam(recipObj, attribute, value);
        
        if (mode == 0 && status == X400_att.X400_E_NOERROR) return;
        logger.info(String.format(tracingStr, AttributeDic.getInstance().get(attribute), value, status));
    }
    
    public static void set(Recip recipObj, int attribute, Integer value) {
        set(recipObj, attribute, value, 0);
    }
    
    
    
    
    
    
    
    
    
    
    

    public static String getStr(MTMessage message, int att, Integer mode) {
        StringBuffer retValue = new StringBuffer();
        int status = com.isode.x400mtapi.X400mt.x400_mt_msggetstrparam(message, att, retValue);

        if (status != X400_att.X400_E_NOERROR) {
            if (mode != 0) logger.info(AttributeDic.getInstance().get(att) + " --> NO VALUE [" + status + "]");
            return null;
        }

        if (mode != 0) logger.info(AttributeDic.getInstance().get(att) + " --> " + retValue.toString());
        return retValue.toString();
    }
    
    public static String getStr(MTMessage message, int att) {
        return getStr(message, att, 0);
    }
    
    public static String getStr(Recip recipObj, int att, Integer mode) {

        StringBuffer value = new StringBuffer();
        int status = com.isode.x400mtapi.X400mt.x400_mt_recipgetstrparam(recipObj, att, value);

        if (status != X400_att.X400_E_NOERROR) {
            if (mode != 0) logger.info(AttributeDic.getInstance().get(att) + "--> NO VALUE [" + status + "]");
            return null;
        }

        if (mode != 0) logger.info(AttributeDic.getInstance().get(att) + "-->" + value);
        return value.toString();
    }
    
    public static String getStr(Recip recipObj, int att) {
        return getStr(recipObj, att, 0);
    }
    
    public static String getStr(BodyPart bodypart, int att) {
        return getStr(bodypart, att, 0);
    }

    public static String getStr(BodyPart bodypart, int att, int mode) {

        StringBuffer value = new StringBuffer();
        byte[] bytes = new byte[32000];

        int status = com.isode.x400api.X400.x400_bodypartgetstrparam(bodypart, att, value, bytes);

        if (status != X400_att.X400_E_NOERROR) {
            if (mode != 0) logger.info(AttributeDic.getInstance().get(att) + "--> NO VALUE [" + status + "]");
            return null;
        }

        if (mode != 0) logger.info(AttributeDic.getInstance().get(att) + "-->" + value);
        return value.toString();
    }

    public static String getStr(DLExpHist dlHist, int att) {
        return getStr(dlHist, att, 0);
    }
    
    public static String getStr(DLExpHist dlHist, int att, int mode) {
        
         StringBuffer buffer = new StringBuffer();
        int status = com.isode.x400api.X400.x400_DLgetstrparam(dlHist, att, buffer);
        
        if (status != X400_att.X400_E_NOERROR) {
            if (mode != 0) logger.info(AttributeDic.getInstance().get(att) + "--> NO VALUE [" + status + "]");
            return null;
        }
        
        if (mode != 0) logger.info(AttributeDic.getInstance().get(att) + "-->" + buffer);
        return buffer.toString();
    }
    
    
    
    
    public static Integer getInt(MTMessage message, int att, Integer mode) {
        
        int status = com.isode.x400mtapi.X400mt.x400_mt_msggetintparam(message, att);
        
        if (status == X400_att.X400_E_NO_VALUE) {
            if (mode != 0) logger.info(AttributeDic.getInstance().get(att) + " --> NO VALUE");
            return null;
        } 
        
        if (status != X400_att.X400_E_NOERROR) {
            if (mode != 0)  logger.info(AttributeDic.getInstance().get(att) + " --> ERROR " + status);
            return null;
        } 
            
        int value = message.GetIntValue();
        if (mode != 0) logger.info(AttributeDic.getInstance().get(att) + " --> " + value);
        return value;
    }
    
    public static Integer getInt(MTMessage message, int att) {
        return getInt(message, att, 0);
    }
    
    public static Integer getInt(Recip recipObj, int att, Integer mode) {
        int status = com.isode.x400mtapi.X400mt.x400_mt_recipgetintparam(recipObj, att);

        if (status != X400_att.X400_E_NOERROR) {
            if (mode != 0) logger.info(AttributeDic.getInstance().get(att) + "--> NO VALUE [" + status + "]");
            return null;
        }

        int int_value = recipObj.GetIntValue();

        if (mode != 0) logger.info(AttributeDic.getInstance().get(att) + "-->" + int_value);
        return int_value;
    }

    public static Integer getInt(Recip recipObj, int att) {
        return getInt(recipObj, att, 0);
    }

    public static Integer getInt(BodyPart bodypart, int att) {
        return getInt(bodypart, att, 0);
    }

    public static Integer getInt(BodyPart bodypart, int att, int mode) {
        int status = com.isode.x400api.X400.x400_bodypartgetintparam(bodypart, att);

        if (status != X400_att.X400_E_NOERROR) {
            if (mode != 0) logger.info(AttributeDic.getInstance().get(att) + "--> NO VALUE [" + status + "]");
            return null;
        }

        int value = bodypart.GetIntValue();
        if (mode != 0) logger.info(AttributeDic.getInstance().get(att) + "-->" + value);
        return value;
    }
    
    
    
    
    
    

    public static List<String> getReportAddressFilterByDianogsticCode(MTMessage message, Integer diagnostic) {

        int status;
        int num = 1;

        List<String> adds = new ArrayList<>();
        Recip recip_obj = new Recip();
        
        for (num = 1;; num++) {

            status = com.isode.x400mtapi.X400mt.x400_mt_recipget(message, X400_att.X400_RECIP_REPORT, num, recip_obj);
            
            if (status == X400_att.X400_E_NO_RECIP || status != X400_att.X400_E_NOERROR) break;

            Integer diagnosticCode = getInt(recip_obj, X400_att.X400_N_NON_DELIVERY_DIAGNOSTIC);
            if (!Objects.equals(diagnostic, diagnosticCode)) continue;

            adds.add(getStr(recip_obj, X400_att.X400_S_OR_ADDRESS));
        }
        
        return adds;
    }        

    public static int priorityMapping(String priority) {
        if (priority == null) {
            return 4;
        }
        switch (priority) {
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
            default:
                return 4;
        }
    }

    public static String generateMessageId() {
        String hostname = Config.instance.getHostname();
        String domain = Config.instance.getDomainId();
        Date date = new Date();
        int n = (int) (date.getTime() % 1000);
        // System.out.println("n=" + n);
        // String localpart = hostname + "." + n + "-" + dateFormat.format(date);
        return String.format("[%s;%s.%s-%s]", domain, hostname, n, dateFormat.format(date));
        // return new StringBuilder().append(domain).append(";").append(localpart).toString();
    }
    
    public static String generateIpmId(String originator) {
        String time = ipmDateFormat.format(new Date());
        // String ipmFormat = "%s;%sZ*";
        String ipmFormat = "%sZ*%s";
        return String.format(ipmFormat, time, originator);
    }

    public static String getTimeInString(Date date) {
        return datetimeFormat.format(date) + "Z";
    }

    public static String getTimeInString() {
        return datetimeFormat.format(new Date()) + "Z";
    }

    public static String getReceiptTimeFromFilingTime(String filingTime) {
        final String patern = "yyMM" + filingTime + "00";
        SimpleDateFormat frmt = new SimpleDateFormat(patern);
        return frmt.format(new Date()) + "Z";
    }
    
    public static String getAuthorizedTimeFromFilingTime(String filingTime) {
        final String patern = "yyyyMM" + filingTime + "00";
        SimpleDateFormat frmt = new SimpleDateFormat(patern);
        return frmt.format(new Date()) + "Z";
    }
    
    public static String generateFilingTime(Date date) {
        return filingTime.format(date);
    }
    
    public static String generateFilingTime() {
        return generateFilingTime(new Date());
    }
    
    private static String buildAddressIndicator(List<String> addresses) {
        StringBuilder builder = new StringBuilder();
        for (String addresse : addresses) {
            // if (i == 6 || i == 13) {
            //     builder.append(addresses.get(i)).append("\n");
            //     continue;
            // }
            builder.append(addresse).append(" ");
        }
        return builder.toString().trim();
    }
}
