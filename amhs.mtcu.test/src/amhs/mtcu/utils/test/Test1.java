/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.mtcu.utils.test;

import amhs.database.CommonUtils;
import amhs.mt.Validator;
import amhs.mtcu.config.Config;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author andh
 */
public class Test1 {
    
    public static void main(String [] args ){
        
        DOMConfigurator.configure("config/log.xml");
        CommonUtils.configure("config/database.xml");
        Config.configure("config/gateway.xml");
        
        // String format = "^ZCZC\\s([A-Z0-9]|\\&){5,}(\\s[A-Z0-9]{1,})*(\\s{5})*$";
        // System.out.println(">> " + Validator.validateFormat(format, "ZCZC &HA0001 230000     "));
        // System.out.println(">> " + Validator.validateOrigin("230001 VVNBYFYX"));
                
        /*
        System.out.println(System.nanoTime());
        DecimalFormat format = new DecimalFormat("#.00000*");
        long time = (long) System.nanoTime();
        long prefix = time/100000;
        long post = time % 100000;
        System.out.println(prefix + "." + post+"*");
        */
        
        /*
        System.out.println("Time");
        SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmssz");
        System.out.println("Time now: " + format.format(new Date()));
        
        GatewayInDbUtils gatewayDbUtils = new GatewayInDbUtils();
        List<GatewayIn> gateways = gatewayDbUtils.getGatewayInMessages(17);
        System.out.println("Get gatewayin from table: " + gateways.size());
        */
        StringBuilder builder  = new StringBuilder();

        builder.append("zczc H&;A0002 230002     ").append("\r\n");
        builder.append("GG VVNBZaAZX â").append("\r\n");
       

        // String result = Validator.autoCorrectCharacter(builder.toString());
        // System.out.println(result);
        
        System.out.println(Config.instance.getAmhsChannel().getAllowSymbols().contains(" "));
        
        int i = Validator.validateCharacter(builder.toString());
        System.out.println(i);
        
        builder  = new StringBuilder();
        builder.append("zCZC HA0002 230002     ").append("\r\n");
        builder.append("GG VVNBZAZX").append("\r\n");
        i = Validator.validateCharacter(builder.toString());
        System.out.println(i);
        
         builder  = new StringBuilder();
        builder.append("ZCZC HA0002 230002     ").append("\r\n");
        builder.append("GG VVNBZAZX;").append("\r\n");
        i = Validator.validateCharacter(builder.toString());
        System.out.println(i);
        
        builder  = new StringBuilder();
        builder.append("ZCZC HA0002 230002     ").append("\r\n");
        builder.append("GG VVNBZAZX").append("\r\n");
        i = Validator.validateCharacter(builder.toString());
        System.out.println(i);
       
        
    }
    
}
