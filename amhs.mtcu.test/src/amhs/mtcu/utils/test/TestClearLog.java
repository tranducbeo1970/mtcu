/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amhs.mtcu.utils.test;

import amhs.database.CommonUtils;
import amhs.database.utils.gateway.ConversionLogDbUtil;
import amhs.mtcu.config.Config;
import amhs.mtcu.config.NDRCode;
import amhs.mtcu.config.NDRDianogsticCode;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author andh
 */
public class TestClearLog {

    public static void main(String[] args) {
        DOMConfigurator.configure("config/log.xml");
        CommonUtils.configure("config/database.xml");
        Config.configure("config/gateway.xml");
        NDRCode.configure("config/code.xml");
        NDRDianogsticCode.configure("config/dianogstic_code.xml");

        cleaningTrashMessage();
    }

    private static void countingTrashMessage() {
        ConversionLogDbUtil logUtil = new ConversionLogDbUtil();
        for (int i = 0; i < 60; i++) {
            System.out.println("day: " + i + " ------- " + logUtil.getTrashMaxId(i));
        }
    }

    private static void cleaningTrashMessage() {
        ConversionLogDbUtil logUtil = new ConversionLogDbUtil();
        for (int i = 60; i > 0; i--) {
            long maxId = logUtil.getTrashMaxId(i);
            System.out.println("Day: " + i + " MAX ID: " + maxId);
            logUtil.clearLogMessage(maxId);
        }
    }
}
