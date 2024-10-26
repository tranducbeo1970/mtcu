
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.isode;

import com.isode.x400.highlevel.X400APIException;

import com.isode.x400api.Session;
import com.isode.x400api.X400_att;
import com.isode.x400mtapi.MTMessage;
import com.isode.x400mtapi.X400mt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ANDH
 */
public class MTConnection1 implements AutoCloseable {

    private boolean connected = false;
    private String channelName;
    private String logFile;
    private Session session;
    private int result;

    private static final Logger logger = LoggerFactory.getLogger(MTConnection1.class);
    
    /**
     * Constructor
     *
     * @param channelName
     * @param logFile
     */
    public MTConnection1(String channelName, String logFile) {
        this.channelName = channelName;
        this.logFile = logFile;
    }

    public void connect() throws X400APIException {
        session = new Session();
        session.SetSummarizeOnBind(false);
        result = X400mt.x400_mt_open(channelName, session);

        if (result != X400_att.X400_E_NOERROR) {
            // X400mt.x400_mt_close(session);
            // this.close();

            String errString = X400mt.x400_mt_get_string_error(session, result);
            logger.error("Open connection fail (code: {} - {})", result, errString);
            throw new X400APIException(String.format("Connect fail (%s)", errString), result);
        }

        this.connected = true;
        result = X400mt.x400_mt_setstrdefault(session, X400_att.X400_S_LOG_CONFIGURATION_FILE, this.logFile, -1);
    }

    @Override
    public void close() {
        if (session != null) {
            result = X400mt.x400_mt_close(session);
            logger.error("Connection closed (code: {})", result);
        }

        session = null;
        this.connected = false;
    }

    /*-----------------------------------------------------------
    
    
    
    ------------------------------------------------------------*/
    public void send(DeliverMessage message) throws X400APIException {

        final MTMessage mtMessage = new MTMessage();
        result = X400mt.x400_mt_msgnew(session, X400_att.X400_MSG_MESSAGE, mtMessage);
        if (result != X400_att.X400_E_NOERROR) {
            String errString = X400mt.x400_mt_get_string_error(session, result);
            logger.error("Creating IPM fail (code: {} - {})", result, errString);
            throw new X400APIException(String.format("Create message fail (%s)", errString), result);
        }

        try {

            message.build(mtMessage);
            result = X400mt.x400_mt_msgsend(mtMessage);
            if (result != X400_att.X400_E_NOERROR) {
                // this.close();
                String errString = X400mt.x400_mt_get_string_error(session, result);
                logger.error("Sending IPM fail (code: {} - {})", result, errString);
                throw new X400APIException(String.format("Delivery message fail (%s)", errString), result);
            }

        } finally {
            delele(mtMessage);
        }
    }
    /*-----------------------------------------------------------
    
    
    
    ------------------------------------------------------------*/
    public void send(DeliverIpnMessage message) throws X400APIException {

        final MTMessage mtMessage = new MTMessage();

        result = X400mt.x400_mt_msgnew(session, X400_att.X400_MSG_MESSAGE, mtMessage);
        if (result != X400_att.X400_E_NOERROR) {
            // X400mt.x400_mt_close(session);
            String errString = X400mt.x400_mt_get_string_error(session, result);
            logger.error("Create IPN fail (code: {} - {})", result, errString);
            throw new X400APIException(String.format("Create MTMessage fail (%s)", errString), result);
        }

        try {
            message.build(mtMessage);
            result = X400mt.x400_mt_msgsend(mtMessage);

            if (result != X400_att.X400_E_NOERROR) {
                // X400mt.x400_mt_close(session);
                String errString = X400mt.x400_mt_get_string_error(session, result);
                logger.error("Create IPN fail (code: {} - {})", result, errString);
                throw new X400APIException(String.format("Delivery message fail (%s)", errString), result);
            }

        } finally {
            delele(mtMessage);
        }

    }

    public void send(DeliverReport message) throws X400APIException {

        final MTMessage mtMessage = new MTMessage();
        result = X400mt.x400_mt_msgnew(session, X400_att.X400_MSG_REPORT, mtMessage);
        if (result != X400_att.X400_E_NOERROR) {
            // X400mt.x400_mt_close(session);
            String errString = X400mt.x400_mt_get_string_error(session, result);
            logger.error("Sending report fail (code: %s - %s)", result, errString);
            // this.close();
            throw new X400APIException(String.format("Create MTMessage fail (%s)", errString), result);
        }

        try {
            message.build(mtMessage);
            result = X400mt.x400_mt_msgsend(mtMessage);
            if (result != X400_att.X400_E_NOERROR) {
                String errString = X400mt.x400_mt_get_string_error(session, result);
                throw new X400APIException(String.format("Delivery message fail (%s)", errString), result);
            }

        } finally {
            delele(mtMessage);
        }

    }

    private void delele(MTMessage mtMessage) {
        int result = X400mt.x400_mt_msgdel(mtMessage);
        if (result != X400_att.X400_E_NOERROR) {
            String errString = X400mt.x400_mt_get_string_error(session, result);
            logger.error("Message deleted ({})", errString);
        }
    }

    /**
     * @return the connected
     */
    public boolean isConnected() {
        return connected;
    }
}
