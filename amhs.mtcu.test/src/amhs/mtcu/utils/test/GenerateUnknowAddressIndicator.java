/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.mtcu.utils.test;

import amhs.aftn.Message;
import amhs.database.CommonUtils;
import amhs.database.entities.GatewayIn;
import amhs.database.entities.GatewayOut;
import amhs.database.utils.gateway.GatewayInDbUtils;
import amhs.database.utils.gateway.GatewayOutDbUtils;
import amhs.mtcu.config.Config;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author andh
 */
public class GenerateUnknowAddressIndicator {
    
    private static final SimpleDateFormat format = new SimpleDateFormat("ddHHmm");
    
    public static void main(String[] args) {
        
        DOMConfigurator.configure("config/log.xml");
        CommonUtils.configure("config/database.xml");
        Config.configure("config/gateway.xml");

//        GatewayOutDbUtils dbUtil = new GatewayOutDbUtils();
//        GatewayInDbUtils gatewayInDbUtil = new GatewayInDbUtils();
//
//        while (true) {
//            List<GatewayOut> list = dbUtil.getByPriority((short) 0);
//            for (GatewayOut gatewayOut : list) {
//
//                String[] addresses = gatewayOut.getAddress().split(" ");
//                for (String add : addresses) {
//                    GatewayIn gatewayIn = new GatewayIn();
//                    gatewayIn.setAddress(add);
//                    gatewayIn.setCpa("A");
//                    gatewayIn.setPriority((short) 0);
//                    gatewayIn.setSource("AFTN");
//                    gatewayIn.setTime(new Date());
//                    gatewayIn.setText(generate(gatewayOut.getOriginator(), add, gatewayOut.getFilingTime()));
//                    gatewayInDbUtil.insert(gatewayIn);
//                    System.out.println("Generate message id: " + gatewayIn.getMsgid() + " address: " + gatewayIn.getAddress());
//                    System.out.println(gatewayIn.getText() + "\n");
//                }
//                dbUtil.delete(gatewayOut);
//                System.out.println("Delete gateway-out message id: " + gatewayOut.getMsgid());
//            }
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(GenerateAckMessage.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }

    /**
     * Generate acknowledge message
     * @param origin
     * @param address
     * @param filingTime
     * @return 
     */
    private static String generate(Message message) {
        StringBuilder builder = new StringBuilder();
//        builder.append("ZCZC ABC0001     \r\n");
//        builder.append("FF ").append(origin).append("\r\n");
//        // builder.append("070251 ").append("XXGHJNJN").append("\r\n");
//        builder.append(filingTime).append(" ").append(address).append("\r\n");
//        builder.append("SVC ADS ").append(circuitId).append("\r\n");
//        builder.append("\r\n");
//        builder.append("\r\n");
//        builder.append("\r\n");
//        builder.append("\r\n");
//        builder.append("\r\n");
//        builder.append("NNNN");
        return builder.toString();
    }
}
