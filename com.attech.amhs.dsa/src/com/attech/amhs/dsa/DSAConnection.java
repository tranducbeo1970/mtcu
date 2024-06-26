/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.dsa;

//import com.attech.amhs.mtcu.Resources;
//import com.attech.amhs.mtcu.config.Config;
//import com.attech.amhs.mtcu.utils.AddressUtil;
import com.isode.dsapi.Attribute;
import com.isode.dsapi.AttributeType;
import com.isode.dsapi.BadAddressException;
import com.isode.dsapi.BadAttributeTypeException;
import com.isode.dsapi.BadDNException;
import com.isode.dsapi.BadFilterException;
import com.isode.dsapi.DN;
import com.isode.dsapi.DSAPIException;
import com.isode.dsapi.DSapi;
import com.isode.dsapi.DirectoryOperationFailedException;
import com.isode.dsapi.DirectorySession;
import com.isode.dsapi.DirectorySession.ConnectionState;
import com.isode.dsapi.Entry;
import com.isode.dsapi.Indication;
import com.isode.dsapi.IndicationException;
import com.isode.dsapi.NativeLibraryException;
import com.isode.dsapi.NoSuchAttributeException;
import com.isode.dsapi.NoSuchEntryException;
import com.isode.dsapi.NotBoundException;
import com.isode.dsapi.NotImplementedException;
import com.isode.dsapi.Selection;
import com.isode.dsapi.SignedOpFailedException;
import com.isode.dsapi.atnds.ATNds;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author ANDH
 */
public class DSAConnection implements Runnable {

//    private static final MLogger logger = MLogger.getLogger(DSAConnection.class);
    private static DSAConnection instance;

    private final Map<Key, AddressConvertResult> map;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

    private DirectorySession ds;
    private DN mdLookupDN;
    private DN usrLookupDN;
    private DN aftnLookupDN;
    private String mdLookupDNName = "o=address-lookup,o=conversion-address-lookup-definition";
    private String usrLookupDNName = "o=custom-address-lookup,o=conversion-address-lookup-definition";
    private String aftnLookupName = "o=aftn-address-lookup,o=conversion-address-lookup-definition";
//    private String dsaServer = "URI+0000+URL+itot://192.168.1.202:19999";
    private String dsaServer = "ldap://192.168.1.202:19389";
    private long expiredPeriod;
    private long maintainPeriod;
    private boolean enableCache;
    private boolean isConnected;

    public DSAConnection() {
        isConnected = false;
        enableCache = true;
        maintainPeriod = 20;
        expiredPeriod = 5000;
        map = new HashMap<>();
        scheduler.scheduleAtFixedRate(this, maintainPeriod, maintainPeriod, TimeUnit.SECONDS);
    }

//    public void config(AddressConversion config) {
//        this.dsaServer = config.getDsaPresentationAdd();
//        this.aftnLookupName = config.getAftbLookupTable();
//        this.mdLookupDNName = config.getDnName();
//        this.usrLookupDNName = config.getUserLookupTable();
//        this.expiredPeriod = config.getExpiredPeriod();
//        this.maintainPeriod = config.getMaintainPeriod();
//        this.enableCache = config.isCacheEnable();
//    }
    public synchronized AddressConvertResult convertToAftnAddress(String address, boolean isOriginAddress) throws DSAPIException {

        // final String key = this.getKey(address, isOriginAddress);
        if (enableCache) {

            final Key key = new Key(address, isOriginAddress);
            if (map.containsKey(key)) {
                final AddressConvertResult convertedResult = map.get(key);
                System.out.println("Get from cache " + convertedResult);
                convertedResult.reset();
                return convertedResult;
            }
        }

        this.connect();

        try {

//        String adr = convertAMHSAdressToAFTNAddress(address)
//        String adr = searchUserLookupTableAMHS(address);
//        if (adr == null || adr.isEmpty()) {
//            adr = AddressUtil.getShort(address);
//        }
//
//        if (adr == null || adr.isEmpty()) {
//            return null;
//        }
            final String converted = toAFTN(address);
            if (converted == null) {
                return null;
            }

            final String backwardAddress = toAMHS(converted, isOriginAddress);

            final AddressConvertResult convertResult = new AddressConvertResult(address, converted, backwardAddress);

            if (enableCache) {
                final Key key = new Key(address, isOriginAddress);
                map.put(key, convertResult);
                System.out.println("Put in cache " + key + " (" + map.keySet().size() + ") ");
            }

            return convertResult;
        } catch (Exception ex) {
            this.close();

            if (ex instanceof DSAPIException) {
                throw ex;
            }

            throw new DSAPIException("Convert fail", ex);

        }

    }

    public synchronized AddressConvertResult convertToAmhsAddress(String aftnAddress, boolean isOriginAddress) throws DSAPIException {

        if (!validateAFTNAddress(aftnAddress)) {
            return null;
        }

        // Get from caching
        // final String key = this.getKey(aftnAddress, isOriginAddress);
        AddressConvertResult convertedResult;
        if (enableCache) {
            final Key key = new Key(aftnAddress, isOriginAddress);
            if (map.containsKey(key)) {
                convertedResult = map.get(key);
                System.out.println("Get from cache " + convertedResult);
                convertedResult.reset();
                return convertedResult;
            }
        }

        this.connect();

        try {

            final String converted = toAMHS(aftnAddress, isOriginAddress);
            if (converted == null) {
                return null;
            }

            final String backwardAddress = toAFTN(converted);
            convertedResult = new AddressConvertResult(aftnAddress, converted, backwardAddress);

            if (enableCache) {
                final Key key = new Key(aftnAddress, isOriginAddress);
                map.put(key, convertedResult);
                System.out.println("Put in cache " + key + " (" + map.keySet().size() + ") ");
            }

            return convertedResult;

        } catch (Exception ex) {

            this.close();

            if (ex instanceof DSAPIException) {
                throw ex;
            }

            throw new DSAPIException("Convert fail", ex);
        }
    }

    public static DSAConnection getInstance() {
        if (instance == null) {
            instance = new DSAConnection();
        }

        return instance;
    }

    public void starSelfMaintainService() {
//        if (!enableCache) {
//            return;
//        }
        scheduler.scheduleAtFixedRate(this, maintainPeriod, maintainPeriod, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        try {
//            logger.debug("Begin cleanup address conversion cache process. (Cache amount: %s items)", this.map.size());
            int count = maintain();
//            logger.debug("End cleanup. (Remove: %s remain: %s)", count, this.map.size());
            System.gc();
        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error(ex);
        }
    }

    private int maintain() {

        System.out.println("Maintain cache ---------");

        final Iterator it = map.keySet().iterator();
        int count = 0;
        int index = 0;
        while (it.hasNext()) {

            index++;
            final Key key = (Key) it.next();
            final AddressConvertResult item = this.map.get(key);
            System.out.println(" >> Maintain item " + index + " key: " + key);
            if (!item.isExpireUpdate(expiredPeriod)) {
                continue;
            }

            it.remove();
            count++;
            System.out.println("removed");
//            logger.warn(" ** Cleanup process > remove cache item %s", key);

        }
        return count;
    }

    private void connect() throws DSAPIException {
        try {

            if (ds == null) {
                ds = new DirectorySession(dsaServer);
                ds.bind(null);
                mdLookupDN = new DN(mdLookupDNName);
                usrLookupDN = new DN(usrLookupDNName);
                aftnLookupDN = new DN(aftnLookupName);
                System.out.println("Connected to dsa");
                return;
            }

            ConnectionState connectionState = ds.getConnectionState();

            switch (connectionState) {
                case UNBOUND:
                case NOT_YET_CONNECTED:
                case VERIFICATION_FAILED:
                case CONNECTION_STATE_UNKNOWN:
                case CONNECTION_LOST:
                    ds = new DirectorySession(dsaServer);
                    ds.bind(null);
                    mdLookupDN = new DN(mdLookupDNName);
                    usrLookupDN = new DN(usrLookupDNName);
                    aftnLookupDN = new DN(aftnLookupName);
                    System.out.println("Connected to dsa");
                    break;
            }

            /*
            if (ds != null) {

                System.out.println("Connection is bound: " + ds.isBound());
            }

            if (ds != null && ds.isBound()) {
                return;
            }

            System.out.println("init api");
            ds = new DirectorySession(dsaServer);
            ds.bind(null);

            mdLookupDN = new DN(mdLookupDNName);
            usrLookupDN = new DN(usrLookupDNName);
            aftnLookupDN = new DN(aftnLookupName);
             */
        } catch (Exception ex) {
            throw new DSAPIException("Connect fail", ex);
        }
    }

    private void close() {
        try {

            if (this.ds == null || !this.ds.isBound()) {
                return;
            }
            this.ds.unbind();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            System.out.println("closed");
        }
    }

    // PRIVATE
    private boolean validateAFTNAddress(String address) {
        if (address == null || address.isEmpty()) {
            return false;
        }
        final String pattern = "^[A-Z0-9]{8}$";
        return address.matches(pattern);
    }

    private String toAFTN(String address) throws DSAPIException {
//        this.connect();
//        try {
        // Checking for custom conversion
        final String adr = searchUserLookupTableAMHS(address);
        if (adr != null && !adr.isEmpty()) {
            return adr;
        }

        // Convert
        return AddressUtil.getShort(address);

//        } catch (Exception ex) {
//            this.close();
//            throw new DSAPIException(ex.getMessage(), ex.getCause());
//        }
    }

    private String toAMHS(String address, boolean isOriginAddress) throws DSAPIException {
        // this.connect();
        // try {

        String converted = searchUserLookupTableAFTN(address);
        if (converted != null && !converted.isEmpty()) {
            return converted;
        }

        if (isOriginAddress) {
            converted = toAMHS(ds, aftnLookupDN, address);
            if (converted != null) {
                return converted;
            }
        }

        return toAMHS(ds, mdLookupDN, address);

        // } catch (DSAPIException ex) {
        //    System.out.println("ERROR on convert addres " + address + " origin: " + isOriginAddress);
        //    this.close();
        //    throw ex;
        // }
    }

    private String toAMHS(DirectorySession ds, DN dn, String address) throws DSAPIException {

        try {

            ATNds.ATNdsResult result = ATNds.convertAFTN2AMHS(ds, dn, address);

            if (result == null) {
                return null;
            }

            String amhsAddress = result.getString();

            if (amhsAddress == null || amhsAddress.isEmpty()) {
                return null;
            }
            return amhsAddress.replace("/OU1=", "/OU=");

        } catch (Exception ex) {
            throw new DSAPIException("Searching directory fail", ex);
        }
    }

    private String searchUserLookupTableAMHS(String address) throws DSAPIException {

        try {
            // logger.info("searchUserLookupTableAMHS");
            final String filter = String.format("(atn-global-domain-identifier=%s)", address);
            final Indication indication = ds.searchOneLevel(usrLookupDN, filter, new Selection(Selection.SelectionType.ALL_USER_AND_OPER_ATTRIBUTES));
            if (indication == null || indication.getEntryCount() == 0) {
                return null;
            }

            final Entry entry = indication.getEntry(0);
            final AttributeType type = new AttributeType("cn");
            final Attribute att = entry.getAttribute(type);
            if (att == null || att.getValueCount() == 0) {
                return null;
            }

            return att.getValue(0).getString();

        } catch (Exception ex) {
            throw new DSAPIException("Searching directory fail", ex);
        }
    }

    private String searchUserLookupTableAFTN(String address) throws DSAPIException {
        try {

            final String filter = String.format("(cn=%s)", address);
            final Indication indication = ds.searchOneLevel(usrLookupDN, filter, new Selection(Selection.SelectionType.ALL_USER_AND_OPER_ATTRIBUTES));

            if (indication == null || indication.getEntryCount() == 0) {
                return null;
            }
            final Entry entry = indication.getEntry(0);
            final AttributeType type = new AttributeType("atn-global-domain-identifier");
            final Attribute att = entry.getAttribute(type);
            if (att == null || att.getValueCount() == 0) {
                return null;
            }
            return att.getValue(0).getString();

        } catch (Exception ex) {
            throw new DSAPIException("Searching directory fail", ex);
        }
    }

    private String makeKey(String value, Boolean isOriginAddress) {
        if (isOriginAddress) {
            return String.format("%s:ORG", value);
        }

        return value;

    }

    //<editor-fold defaultstate="collapsed" desc="Class Properties">
    /**
     * @param dsaServer the dsaServer to set
     */
    public void setDsaServer(String dsaServer) {
        this.dsaServer = dsaServer;
    }

    /**
     * @param mdLookupDNName the mdLookupDNName to set
     */
    public void setMdLookupDNName(String mdLookupDNName) {
        this.mdLookupDNName = mdLookupDNName;
    }

    /**
     * @param usrLookupDNName the usrLookupDNName to set
     */
    public void setUsrLookupDNName(String usrLookupDNName) {
        this.usrLookupDNName = usrLookupDNName;
    }

    /**
     * @param aftnLookupName the aftnLookupName to set
     */
    public void setAftnLookupName(String aftnLookupName) {
        this.aftnLookupName = aftnLookupName;
    }
    //</editor-fold>

    public static void main(String[] args) throws BadDNException, Exception {
        DOMConfigurator.configure("log.xml");

        DSapi.initialize();

//        Config.configure(Resources.GATEWAY_CONFIG);
//        DSAConnection.getInstance().config(Config.instance.getAddressConversion());
        List<String> addresses = buildAftnAddressList();

        DSAConnection connection = new DSAConnection();
        
        /*
        while (true) {
            for (String address : addresses) {
                try {
                    System.out.println("Convert " + address);
                    AddressConvertResult result = connection.convertToAmhsAddress(address, true);
                    System.out.println("Result: ");
                    System.out.println(result);
//                    testConvertion2(address);
                    System.out.println("----------------------------------------------------------");
                    Thread.sleep(2000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Thread.sleep(5000);
                }
            }

            // Thread.sleep(3600000);
        }
        */
        
        
        for (String address : addresses) {
            try {
                System.out.println("Convert " + address);
                AddressConvertResult result = connection.convertToAmhsAddress(address, true);
                System.out.println("Result: ");
                System.out.println(result);
//                    testConvertion2(address);
                System.out.println("----------------------------------------------------------");
//                Thread.sleep(2000);
            } catch (Exception ex) {
                ex.printStackTrace();
                Thread.sleep(5000);
            }
        }
        
        while (true) {
            AddressConvertResult result = connection.convertToAmhsAddress("VVTSYMYX", true);
            Thread.sleep(2000);
        }

    }

    private static void testConvertion2(String amhsAdd) throws Exception {

        DSAConnection connection = new DSAConnection();
//        connection.connect();
//        try {
        AddressConvertResult result = connection.convertToAmhsAddress(amhsAdd, true);
        System.out.println(result);

//            result = DSAConnection.instance.convertToAmhsAddress(amhsAdd, false);
//            System.out.println(result);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            connection.close();
//        }
//        AddressConvertResult result = DSAConnection.getInstance().convertToAmhsAddress(amhsAdd, true);
//        System.out.println(result);
//
//        result = DSAConnection.instance.convertToAmhsAddress(amhsAdd, false);
//        System.out.println(result);
//        DSAConnection connection = new DSAConnection();
//
//        System.out.println("---------------------------------------------------------------------------------");
//        Address address = new Address();
//        address.setAftnAddress(amhsAdd);
//        connection.aftn2amhs(address, true);
//        if (!address.isConvertable()) {
//            System.out.println("Convert fail ");
//            return;
//        }
//        System.out.println("Convertable: " + address.getAmhsAddress());
    }

    private static void testConvertion(String amhsAdd) throws Exception {

//        try {
//            System.out.println("---------------------------------------------------------------------------------");
//            DSAConnection connection = new DSAConnection();
//            Address add = connection.amhs2aftn2(amhsAdd);
//            if (add == null) {
//                System.out.println("Convert fail ");
//                return;
//            }
//            System.out.println("Convertable: " + add.isConvertable());
//            System.out.println("Asymmetric: " + add.isAsymmetric());
//            System.out.println("AMHS: " + add.getAmhsAddress());
//            System.out.println("AFTN: " + add.getAftnAddress());
//        } catch (Exception ex) {
//            throw ex;
//        }
    }

    private static void testConversion(String address) throws Exception {

        System.out.println("Convert: " + address);
        AddressConvertResult result = DSAConnection.instance.convertToAftnAddress(address, true);
        System.out.println(result);
        System.out.println("---------------------------------------------------------------------------");

//        result = DSAConnection.instance.convertToAftnAddress(address, false);
//        System.out.println(result);
//        DSAConnection connection = new DSAConnection();
//        Address add = new Address(address);
//        connection.convertToAftnAddress(add);
//        System.out.printf("ORIGINAL ADDRESS: %s%n", add.getAmhsAddress());
//        System.out.printf("CONVERTED ADDRESS: %s%n", add.getAftnAddress());
//        System.out.printf("BACKWARD ADDRESS: %s%n", add.getBackwardAddress());
    }

    private static List<String> buildAftnAddressList() {
        String[] prefix = new String[]{"VVTS", "VVNB", "VVDN", "VVGL", "VVVV", "VVDB", "VVRG", "VTBB"};
        String[] posfix = new String[]{"YMYX", "YOYX", "YDYX", "ZTZX", "ZDZA", "ZMZX", "ABCD"};

        List<String> addresses = new ArrayList<>();
        for (int i = 0; i < prefix.length; i++) {
            for (int j = 0; j < posfix.length; j++) {
                addresses.add(prefix[i] + posfix[j]);
            }
        }

        return addresses;
    }

}
