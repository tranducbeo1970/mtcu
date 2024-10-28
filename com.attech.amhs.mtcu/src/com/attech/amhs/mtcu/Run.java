/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.



  */

/*------------------------------

MAIN
        amhsChannel = new AMHSChannel(Config.instance.getAmhsChannel(),AftnChannelList);
        aftnChannel = new AFTNChannel(Config.instance.getAftnChannel());
        amhsChannel.start();
        aftnChannel.start();

*/



/*


Xu ly dien van tu mtcu
package com.attech.amhs.mtcu; -> class AMHSProcessor  -> processMessage(mtmessage, session);


Xu ly dien van tu gwin aftn
package com.attech.amhs.mtcu; -> public class AFTNProcessor 
*/



/*
    TAO DIEN VAN AMHS VA PHAT DI

package com.attech.amhs.mtcu.isode; -> public class DeliverMessage extends DeliverMessageBase {


*/

/*
    Diaary

Ngay 28/10/2024 copy code vao /gatewaynew_28102024


*/

package com.attech.amhs.mtcu;

import com.attech.amhs.mtcu.threads.AFTNChannel;
import com.attech.amhs.mtcu.threads.AMHSChannel;
import com.attech.amhs.mtcu.config.Config;
import com.attech.amhs.mtcu.common.ShutdownHook;
import com.attech.amhs.mtcu.database.Connection;
import com.attech.amhs.mtcu.database.dao.AFTNMessageDao;
import com.attech.amhs.mtcu.database.entity.AftnConfig;
import com.attech.amhs.mtcu.isode.DSAConnection;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * -XX:SurvivorRatio=8 -XX:MinHeapFreeRatio=40 -XX:MaxHeapFreeRatio=70
 *
 * @author andh
 */
public class Run implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Run.class);

    private boolean running;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
    private AMHSChannel amhsChannel;
    private AFTNChannel aftnChannel;

    public Run() throws Exception {
        running = false;

    }

    public void start() throws SQLException {
        logger.info("SOFTWARE START #27/10/2024#");

        final AFTNMessageDao util = new AFTNMessageDao();
        String s = util.getAFTNAddressIndicator();
        //System.out.println("+++++++++++++++++++++");
        //System.out.println("AFTN SYSTEM ORIGIN =" + s);
        Config.instance.setSysAFTNAddress(util.getAFTNAddressIndicator());
        
        
        List<AftnConfig> AftnChannelList = util.getAFTNConfig();
        System.out.println("*** AFTN channel config routing address ****");
        for (AftnConfig config : AftnChannelList) {
            int id = config.getId();
            String content = config.getCCT();
            String add = config.getAddress_pattern();
            
            System.out.println(Integer.toString(id) + "-" + content + "-" + add);

        }
        System.out.println("*******");
        
        
        

        DSAConnection.getInstance().config(Config.instance.getAddressConversion());
        amhsChannel = new AMHSChannel(Config.instance.getAmhsChannel(),AftnChannelList);
        aftnChannel = new AFTNChannel(Config.instance.getAftnChannel());
        amhsChannel.start();
        aftnChannel.start();

        scheduler.scheduleAtFixedRate(this, 10, 10, TimeUnit.SECONDS);
        running = true;
    }

    public void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        try {
//            if (!Config.instance.isDebugMode()) return;
//            printMemory();
            // logger.warn("CALL SYSTEM GC --------------------------------------------");
            // System.gc();
            // printMemory();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

//    private static Run run;
    public static void main(String[] args) throws Exception {

        // DOMConfigurator.configure(Resources.CONFIG_LOG);
        Connection.configure(Resources.DATABASE_CONFIG);
        logger.info("Load {}", Resources.DATABASE_CONFIG);

        Config.configure(Resources.GATEWAY_CONFIG);
        logger.info("Load {}", Resources.GATEWAY_CONFIG);

        Runtime.getRuntime().addShutdownHook(new ShutdownHook(logger));

        printOutVersion();

        if (args != null && args.length > 0) {
            final String command = args[0];
            switch (command) {
                case "/ver":
                    printOutVersion();
                    break;

                case "/convert2AMHS":

                    Config.configure(Resources.GATEWAY_CONFIG);
                    DSAConnection.getInstance().setDsaServer(Config.instance.getAddressConversion().getDsaPresentationAdd());
                    DSAConnection.getInstance().setAftnLookupName(Config.instance.getAddressConversion().getAftbLookupTable());
                    DSAConnection.getInstance().setMdLookupDNName(Config.instance.getAddressConversion().getDnName());
                    DSAConnection.getInstance().setUsrLookupDNName(Config.instance.getAddressConversion().getUserLookupTable());

                    String amhsAddress = args[1];
                    System.out.println(DSAConnection.getInstance().convertToAmhsAddress(amhsAddress, true));
                    System.out.println(DSAConnection.getInstance().convertToAmhsAddress(amhsAddress, false));

                    break;

                case "/convert2AFTN":

                    Config.configure(Resources.GATEWAY_CONFIG);
                    DSAConnection.getInstance().setDsaServer(Config.instance.getAddressConversion().getDsaPresentationAdd());
                    DSAConnection.getInstance().setAftnLookupName(Config.instance.getAddressConversion().getAftbLookupTable());
                    DSAConnection.getInstance().setMdLookupDNName(Config.instance.getAddressConversion().getDnName());
                    DSAConnection.getInstance().setUsrLookupDNName(Config.instance.getAddressConversion().getUserLookupTable());

                    String aftnAddress = args[1];
                    System.out.println(DSAConnection.getInstance().convertToAftnAddress(aftnAddress, true));
                    System.out.println(DSAConnection.getInstance().convertToAftnAddress(aftnAddress, false));

                    break;

                default:
                    printUsage();
                    break;
            }

            return;
        }

        Run run = new Run();
        run.start();

        while (run.isRunning()) {
            try {
                Thread.sleep(120000);
                logger.debug("Program is still running");
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }

    private static void printOutVersion() throws IOException {
        java.io.File file = new java.io.File("com.attech.amhs.mtcu.jar");
        if (!file.exists()) {
            logger.warn("Jar file is not found");
            return;
        }

        try (java.util.jar.JarFile jar = new java.util.jar.JarFile(file)) {
            java.util.jar.Manifest manifest = jar.getManifest();

            System.out.println("\r\nMain Attributes:\r\n--------------------------");
            printAttributes(manifest.getMainAttributes());

            System.out.println("\r\nOther Attributes:\r\n--------------------------");
            java.util.Map<String, java.util.jar.Attributes> entries = manifest.getEntries();
            if (entries.isEmpty()) {
                System.out.println("Nothing");
            } else {
                for (String key : entries.keySet()) {
                    printAttributes(entries.get(key));
                    logger.info("{}:{}", key, entries.get(key));
                }
            }
            
        }
         System.out.println("\r\n-- now begin --");
    }

    private static void printAttributes(java.util.jar.Attributes attributes) {
        java.util.Iterator it = attributes.keySet().iterator();
        while (it.hasNext()) {
            java.util.jar.Attributes.Name key = (java.util.jar.Attributes.Name) it.next();
            Object value = attributes.get(key);
            System.out.println(key + ":  " + value);
        }
    }

    private static void printUsage() {
        System.out.println("java -jar amhs.mtcu.jar [/ver] ");
        System.out.println("java -jar amhs.mtcu.jar [/convert2AMHS | /convert2AFTN] [address]");
    }
}
