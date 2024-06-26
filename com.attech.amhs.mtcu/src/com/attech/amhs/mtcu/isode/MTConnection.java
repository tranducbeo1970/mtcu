
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
public class MTConnection {

    private final String E_WAITING_FOR_MESSAGE_FAIL = "Waiting for message fail [code %s : %s] ";
    private final String E_GETTING_MESSAGE_FAIL = "Getting message from M-Switch fail [code %s : %s]";
    private final String E_FINISH_GETTING_MESSAGE_FAIL = "Finish getting message fail [code %s : %s]";

    private Logger logger = LoggerFactory.getLogger(MTConnection.class);
    // private MLogger logger = MLogger.getLogger(MTConnection.class);
// TODO: refactor logging
    private Session session;
    private boolean open = false;
    private String channelName;
    private int waitingTimeout = 3600;
    private String logFile;

    /**
     * Constructor
     *
     * @param channelName
     * @param logFile
     */
    public MTConnection(String channelName, String logFile) {
        // channel = Config.instance.getAmhsChannel();
        this.channelName = channelName;
        this.logFile = logFile;
    }

    /**
     * Constructor with timeout
     *
     * @param channelName
     * @param logFile
     * @param waitingTimeout
     */
    public MTConnection(String channelName, String logFile, int waitingTimeout) {
        this(channelName, logFile);
        this.waitingTimeout = waitingTimeout;
    }

    public void open() throws X400APIException {
        if (isOpen()) {
            logger.info(String.format("Connection 's already opened (%s)", this.channelName));
            return;
        }

        logger.info("Connecting to M-Switch server");
        session = new Session();
        session.SetSummarizeOnBind(false);

        final int result = X400mt.x400_mt_open(channelName, session);
        if (result != X400_att.X400_E_NOERROR) {
            throw new X400APIException(String.format("Connect to M-Switch %s fail (code: %s)", channelName, result));
        }

        setOpen(true);
        logger.info(String.format("Connected to M-Switch server (%s)", this.channelName));
    }

    public void close() {
        try {
            if (session == null) {
                return;
            }
            final int result = X400mt.x400_mt_close(session);
            if (result == X400_att.X400_E_NOERROR) {
                logger.info("M-Switch connection closed");
            } else {
                logger.error("M-Switch connection closed fail (code: %s)", result);
            }

            session = null;

        } finally {
            setOpen(false);
        }
    }

    public void waiting() throws X400APIException {

        this.open();
        final int status = com.isode.x400mtapi.X400mt.x400_mt_wait(session, waitingTimeout);
        if (status != X400_att.X400_E_NOERROR && status != X400_att.X400_E_TIMED_OUT) {
            String error = String.format(E_WAITING_FOR_MESSAGE_FAIL, status, getError(status));
            this.close();
            throw new X400APIException(error);
        }

        com.isode.x400mtapi.X400mt.x400_mt_setstrdefault(session, X400_att.X400_S_LOG_CONFIGURATION_FILE, this.logFile, -1);
    }

    public void finishGettingMessage(MTMessage mtmessage, int errorCode, int nonDeliveryReason, int diagnosticCode, String suplementInfo) throws X400APIException {
        final int status = X400mt.x400_mt_msggetfinish(mtmessage, errorCode, nonDeliveryReason, diagnosticCode, suplementInfo);
        if (status != X400_att.X400_E_NOERROR) {
            final String error = String.format(E_FINISH_GETTING_MESSAGE_FAIL, status, getError(status));
            throw new X400APIException(error);
        }
    }

    public void send(MTMessage mtmessage) throws X400APIException {
        this.open();
        final int status = X400mt.x400_mt_msgsend(mtmessage);
        if (status != X400_att.X400_E_NOERROR) {
            final String log = String.format("Delivery message failed with code %s (%s)",
                    status,
                    X400mt.x400_mt_get_string_error(session, status));
            this.close();
            throw new X400APIException(log);
        }
    }

    public MTMessage tryGettingMessage() throws X400APIException {
        this.open();
        final MTMessage mtmessage = new MTMessage();
        final int status = X400mt.x400_mt_msggetstart(session, mtmessage);

        if (status == X400_att.X400_E_NO_MESSAGE) {
            return null;
        }

        if (status != X400_att.X400_E_NOERROR) {
            this.close();
            String error = String.format(E_GETTING_MESSAGE_FAIL, status, getError(status));
            throw new X400APIException(error);
        }
        return mtmessage;
    }

    public MTMessage gettingMessage() throws X400APIException {
        this.open();
        final MTMessage mtmessage = new MTMessage();
        final int status = X400mt.x400_mt_msgget(session, mtmessage);

        if (status == X400_att.X400_E_NO_MESSAGE) {
            return null;
        }

        if (status != X400_att.X400_E_NOERROR) {
            this.close();
            final String error = String.format(E_GETTING_MESSAGE_FAIL, status, getError(status));
            throw new X400APIException(error);
        }
        return mtmessage;
    }

    public MTMessage createNewMessage(int messageType) throws X400APIException {
        this.open();
        final MTMessage message = new MTMessage();
        final int status = X400mt.x400_mt_msgnew(session, messageType, message);

        if (status != X400_att.X400_E_NOERROR) {
            this.close();
            final String log = String.format("Create new amhs message fail with code %s (%s)",
                    status, X400mt.x400_mt_get_string_error(session, status));
            throw new X400APIException(log);
        }
        return message;
    }

    public String getError(int error) {
        if (session == null) {
            return null;
        }
        return X400mt.x400_mt_get_string_error(session, error);
    }

//    public void setLogingClass(Class clzz) {
//        logger = MLogger.getLogger(clzz);
//    }
    public synchronized boolean isOpen() {
        return this.open;
    }

    public synchronized void setOpen(boolean value) {
        this.open = value;
    }

}
