/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package report.tool;

import org.apache.log4j.xml.DOMConfigurator;
import report.tool.database.Connection;

/**
 *
 * @author HONG
 */
public class Run {

    private static final MLogger logger = MLogger.getLogger(Run.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            DOMConfigurator.configure("log.xml");
            Connection.configure("database.xml");
            Config.configure("config.xml");
        } catch (Exception ex) {
            logger.error("Errrrrrrror !", ex);
            System.exit(1);
        }

        DeliveryThread thread = new DeliveryThread();
        thread.start();

    }

}
