/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package report.tool;

import report.tool.database.DeliveryReport;
import java.util.List;
import report.tool.database.DeliveryReportDao;

/**
 *
 * @author andh
 */
public class DeliveryThread extends Thread {

    private DeliveryReportDao deliveryReportDao = new DeliveryReportDao();

    private static final MLogger logger = MLogger.getLogger(DeliveryThread.class);

    @Override
    public void run() {
        try {

            List<DeliveryReport> gatewayIn;

            MtConnection connection = new MtConnection(Config.instance.getChannel(), "x400api.xml");
//            connection.connect();
//            connection.sendDump();
//            connection.close();
            
            

            while (!Thread.interrupted()) {
//                logger.info("Lấy điện văn cần phát");
                gatewayIn = deliveryReportDao.get();

                while (gatewayIn != null && !gatewayIn.isEmpty()) {

                    for (DeliveryReport gw : gatewayIn) {

                        try {
                            connection.connect();
                            connection.send(gw);

                        } catch (Exception ex) {
                            connection.close();
                            ex.printStackTrace();
                        } finally {
                            deliveryReportDao.delete(gw);
                        }
                    }
                    gatewayIn = deliveryReportDao.get();
                }

//                logger.info("Đợi điện văn mới");
                this.sleep(Config.instance.getUpdateInterval());
                continue;

            }

        } catch (Exception ex) {
            logger.error("Lỗi! %s", ex);
        }
    }
    
//    public static DeliveryReport createDump() {
//        DeliveryReport deliveryReport = new DeliveryReport();
//        deliveryReport.setOriginator("/CN=VVTSOPTA/OU=VVTS/O=VVTS/PRMD=VIETNAM/ADMD=ICAO/C=XX/");
//    }

}
