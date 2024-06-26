package com.attech.amhs.mtcu.threads;

import com.attech.amhs.mtcu.AFTNProcessor;
import com.attech.amhs.mtcu.config.AFTNChannelConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author andh
 */
public class AFTNChannel extends Channel {
    // private static final MLogger logger = MLogger.getLogger(AFTNChannel.class);
    private static final Logger logger = LoggerFactory.getLogger(AFTNChannel.class);
    private final AFTNProcessor processor;
    private long retryInterval;

    public AFTNChannel(AFTNChannelConfig config) {
        processor = new AFTNProcessor(config);
    }

    @Override
    public void run() {

        logger.info("Started OK");
        this.setRunning(true);

        try {

            // while (!this.isRequestStoped()) {
            while (true) {
                // logger.info("Engine starts");
                processor.process();
                
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
        } finally {
            this.setRunning(false);
            logger.info("Stopped");
        }
    }

    /*
    private void process() {

	try {

	    final AFTNChannelConfig config = Config.instance.getAftnChannel();
	    interval = config.getIntervalTime();
	    retryInterval = config.getRetryInterval();
	    maxRecordFetching = config.getMaxRecordFetching();

	    List<GatewayIn> gatewayIn;

            logger.info("Checking for incoming message");
	    while ((gatewayIn = gatewayInDao.getGatewayInMessages(maxRecordFetching)) != null) {

		if (gatewayIn.isEmpty()) {
		    break;
		}

		for (GatewayIn gw : gatewayIn) {
		    processor.process(gw);
		    gatewayInDao.remove(gw);
		}
                
                gatewayIn.clear();
	    }
	    keepWaiting(interval);

        } catch (SQLException ex) {
            Connection.close();
            logger.error(ex);
            keepWaiting(retryInterval);

        } catch (DSAPIException ex) {

            logger.error(ex);
            keepWaiting(retryInterval);

        } catch (X400APIException ex) {

            logger.error(ex);
            keepWaiting(retryInterval);

        } catch (Exception ex) {

            logger.error(ex);
            keepWaiting(retryInterval);
        }
    }
     */
    private void keepWaiting(long time) {
        try {
            logger.debug("Retry processing in {} seconds.", retryInterval);
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            logger.error("ERROR FOUND:", ex);
//            ExceptionHandler.handle(ex);
        }
    }

    @Override
    public String toString() {
        return "AFTN Channel";
    }

    @Override
    protected Logger getLog() {
        return logger;
    }

}
