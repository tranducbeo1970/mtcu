/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import amhs.aftn.Message;
import amhs.database.dao.GatewayOutDao;
import amhs.database.entities.GatewayOut;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andh
 */
public class AFTNUtil {
    public static void clean() throws Exception {
        Out.print("Cleanup message");
        GatewayOutDao dbUtil = new GatewayOutDao();
        while (true) {
            List<GatewayOut> gwouts = dbUtil.get(10);
            if (gwouts == null || gwouts.isEmpty()) break;
            for (GatewayOut out : gwouts) {
                dbUtil.delete(out);
            }
        }
        Out.print(true);
    }
    
    public static amhs.aftn.Message getMessage(int timeout) throws Exception {
        Out.print("Getting AFTN message");
        GatewayOutDao dbUtil = new GatewayOutDao();
        int counting = 0;
        while (counting <= timeout) {
            List<GatewayOut> gwouts = dbUtil.get(1);
            if (gwouts == null || gwouts.isEmpty()) {
                waiting();
                counting++;
                continue;
            }
            
            GatewayOut gwout = gwouts.get(0);
            amhs.aftn.Message message = new Message();
            message.setOriginator(gwout.getOriginator());
            message.setAdditionInfo(gwout.getOptionalHeading());
            message.setPriority(getPriotiryString(gwout.getPriority()));
            message.setText(gwout.getText());
            message.setFilingTime(gwout.getFilingTime());
            
            String [] adds = gwout.getAddress().split(" ");
            for (String add : adds) {
                if (add == null || add.isEmpty()) continue;
                message.addAddress(add);
            }
            
            dbUtil.delete(gwout);
            Out.print(true);
            return message;
        }
        Out.print(false);
        return null;
    }
    
    private static void waiting() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(AFTNUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static short getPriotiryInteger(String priority) {
        switch (priority) {
            case "SS": return 0;
            case "DD": return 1;
            case "FF": return 2;
            case "GG": return 3;
            default: return 4;
        }
    }
    
    private static String getPriotiryString(int priority) {
        switch (priority) {
            case 0: return "SS";
            case 1: return "DD";
            case 2: return "FF";
            case 3: return "GG";
            default: return "KK";
        }
    }
    
    
    
}
