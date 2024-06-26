/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.resubmit;

import com.attech.amhs.mtcu.common.MLogger;
import com.attech.amhs.mtcu.database.Connection;
import com.attech.amhs.mtcu.database.dao.AFTNMessageDao;
import com.attech.amhs.mtcu.database.dao.GatewayInDao;
import com.attech.amhs.mtcu.database.dao.MessageDao;
import com.attech.amhs.mtcu.database.entity.AftnMessage;
import com.attech.amhs.mtcu.database.entity.GatewayIn;
import com.attech.amhs.mtcu.database.entity.MessageConversionLog;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author hong
 */
public class Run {

    public static final String CONFIG_LOG = "config/log.xml";
    public static final String DATABASE_CONFIG = "config/database.xml";

    private static final MLogger logger = MLogger.getLogger("Program");

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException, InterruptedException {
        DOMConfigurator.configure(CONFIG_LOG);
        Connection.configure(DATABASE_CONFIG);
        Config config = XmlSerializer.load("config/config.xml", Config.class);
        
        logger.info("Program started");
        logger.info("Table name: %s", config.getMsgTableName());
        int index = 1;
        StringBuilder addressBuilder = new StringBuilder();
        for (String address : config.getAddresses()) {
            logger.info("Address %s: %s", index, address);
            addressBuilder.append(address).append(" ");
            index++;
        }

        AFTNMessageDao dao = new AFTNMessageDao();
        GatewayInDao gatewayinDao = new GatewayInDao();
        List<AftnMessage> messages = dao.getAftnMessage(config.getMsgTableName(), config.getAddresses());
        logger.info("Found: %s (messages)", messages.size());

        do {
            int sendingIndex = 1;
            for (AftnMessage aftnmessage : messages) {
                GatewayIn gatewayin = new GatewayIn();
                gatewayin.setAddress(addressBuilder.toString().trim());
                gatewayin.setCpa(aftnmessage.getCpa());
                gatewayin.setMsgid(aftnmessage.getMsgId());
                gatewayin.setPriority(aftnmessage.getPriority());
                gatewayin.setSource("DUMMY");
                gatewayin.setText(aftnmessage.getText());
                gatewayin.setTime(new Date());
                gatewayinDao.save(gatewayin);
                logger.info("Sent %s / %s", sendingIndex, messages.size());
                sendingIndex++;
                Thread.sleep(config.getInterval());
            }
        } while (config.isRepeat());


    }

}
