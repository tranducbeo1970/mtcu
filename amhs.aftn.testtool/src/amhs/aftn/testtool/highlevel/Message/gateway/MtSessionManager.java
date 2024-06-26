/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message.gateway;

import amhs.aftn.testtool.highlevel.Message.NormalMessage;
import amhs.aftn.testtool.highlevel.Message.Out;
import amhs.aftn.testtool.highlevel.Message.Report;
import amhs.aftn.testtool.highlevel.XmlSerializer;
import com.isode.x400api.Session;
import com.isode.x400api.X400_att;
import com.isode.x400mtapi.MTMessage;
import com.isode.x400mtapi.X400mt;
import org.apache.log4j.Logger;

/**
 *
 * @author andh
 */
public class MtSessionManager {
    
    protected static Session session;
    protected static boolean openning;
    
    private static final Logger logger = Logger.getLogger(MtSessionManager.class);
    
    public static int open() {
        
        if (openning) {
            logger.debug("Session is already opened");
            return X400_att.X400_E_NOERROR;
        }

        // Get config
        session = new Session();

        int status = X400mt.x400_mt_open(Config.instance.getChannelName(), session);
        if (status != X400_att.X400_E_NOERROR) {
            logger.error(
                    String.format("Opening session to channel %s fail with code %s (%s)", 
                    Config.instance.getChannelName(), 
                    status,
                    com.isode.x400mtapi.X400mt.x400_mt_get_string_error(session, status)));
            return status;
        }

        X400mt.x400_mt_setstrdefault(session, X400_att.X400_S_LOG_CONFIGURATION_FILE, Config.instance.getLogFile(), -1);
        openning = true;
        return status;
    }
    
    public static int close() {
        int status = com.isode.x400mtapi.X400mt.x400_mt_close(session);
        if (status != X400_att.X400_E_NOERROR) {
            logger.error(
                    String.format("Close session fail with code %s (%s)", 
                    status, 
                    com.isode.x400mtapi.X400mt.x400_mt_get_string_error(session, status)));
        }

        openning = false;
        return status;
    }
    
    public static int deliver(MTMessage message) {
        return deliver(message, session);
    }

    public static int deliver(MTMessage message, Session sessionObj) {
        int status = com.isode.x400mtapi.X400mt.x400_mt_msgsend(message);
        if (status != X400_att.X400_E_NOERROR) {
            logger.debug(
                    String.format("Delivery message failed with code %s (%s)", 
                    status, 
                    com.isode.x400mtapi.X400mt.x400_mt_get_string_error(sessionObj, status)));
            return status;
        }
        
        logger.debug("Delivery message successfully");
        return status;
    }
    
    public static MTMessage newMessage(int type) {
        return newMessage(type, session);
    }
    
    public static MTMessage newMessage(int type, Session sessionObj) {

        MTMessage message = new MTMessage();
        int status = com.isode.x400mtapi.X400mt.x400_mt_msgnew(sessionObj, type, message);
        if (status != X400_att.X400_E_NOERROR) {
            logger.error(
                    String.format("Create new amhs message fail with code %s (%s)", 
                    status, 
                    com.isode.x400mtapi.X400mt.x400_mt_get_string_error(sessionObj, status)));
            return null;
        }

        return message;
    }
    
    public static void send(NormalMessage message) {
        // System.out.println("-----------------------------------------------------------------------");
        Out.print("Deliver message " +  message.getContentId() + "  ");
        open();
        MTMessage mtMessage = newMessage(X400_att.X400_MSG_MESSAGE);
        IPMBuilder.build(mtMessage, message);
        int result = deliver(mtMessage);
        Out.print(result == X400_att.X400_E_NOERROR);
        close();
    }
    
    public static void send(String message) {
        NormalMessage msg = (NormalMessage) XmlSerializer.deserialize(message, NormalMessage.class);
        send(msg);
    }
    
    public static void sendReport(Report report) {
        System.out.println("-----------------------------------------------------------------------");
        System.out.print("Deliver report " + report.getContentID() + "  ");
        open();
        MTMessage mtMessage = newMessage(X400_att.X400_MSG_REPORT);
        ReportBuilder.buildReport(mtMessage, report);
        int result = deliver(mtMessage);
        String rs = result == X400_att.X400_E_NOERROR ? "[O]" : "[X]";
        System.out.println(rs);
        close();
    }
    
    public static void sendReport(String message) {
        Report msg = (Report) XmlSerializer.deserialize(message, Report.class);
        sendReport(msg);
    }
}
