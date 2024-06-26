/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.threads;

import com.attech.amhs.mtcu.AMHSProcessor;
import com.attech.amhs.mtcu.config.Config;
import com.attech.amhs.mtcu.config.AMHSChannelConfig;
import com.attech.amhs.mtcu.database.entity.AftnConfig;
import com.isode.x400.highlevel.X400APIException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author andh
 */
public class AMHSChannel extends Channel {
    private static final Logger logger = LoggerFactory.getLogger(AMHSChannel.class);
    private final AMHSProcessor ipmProcessor;
    private final AMHSChannelConfig channel = Config.instance.getAmhsChannel();
    private List<AftnConfig> configAFTN;

    public AMHSChannel(AMHSChannelConfig config,List<AftnConfig> aftn_channel_config) {
        this.configAFTN = aftn_channel_config;

        this.ipmProcessor = new AMHSProcessor(config,aftn_channel_config);
    }   

    @Override
    public void run() {
        logger.info("Started OK");
        this.setRunning(true);
        try {

            while (!isRequestStoped()) {
                process();
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
        } finally {
            logger.info("Stoped");
            this.setRunning(false);
        }
    }

    private void process() {
        final long retryInterval = channel.getRetryInterval();
        try {
            this.ipmProcessor.process();
        } catch (X400APIException ex) {
            if (ex.getNativeErrorCode() == 4) {
                logger.error(ex.getMessage());
                keepWaiting(retryInterval);
            } else {
                logger.error("ERROR FOUND!", ex);
            }
        } catch (Exception ex) {
            logger.error("ERROR FOUND!", ex);
        }
    }

    private void keepWaiting(long time) {
        try {
            logger.debug("Retry processing in {} seconds", time);
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            logger.error(ex.getMessage());
        }
    }

    @Override
    public String toString() {
        return "AMHS Channel";
    }

    @Override
    protected Logger getLog() {
        return logger;
    }
}
