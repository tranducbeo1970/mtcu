/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.isode;

import com.attech.amhs.mtcu.Resources;
import com.attech.amhs.mtcu.config.AddressConversion;
import com.attech.amhs.mtcu.config.Config;
import com.attech.amhs.mtcu.common.AddressUtil;
import com.isode.dsapi.Attribute;
import com.isode.dsapi.AttributeType;
import com.isode.dsapi.BadDNException;
import com.isode.dsapi.BadValueException;
import com.isode.dsapi.DN;
import com.isode.dsapi.DSAPIException;
import com.isode.dsapi.DSapi;
import com.isode.dsapi.DirectorySession;
import com.isode.dsapi.Entry;
import com.isode.dsapi.Indication;
import com.isode.dsapi.NativeLibraryException;
import com.isode.dsapi.Selection;
import com.isode.dsapi.atnds.ATNds;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ANDH
 */
public class DSAConnection extends WeakHashMap<Key, AddressConvertResult> implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(DSAConnection.class);

    private static DSAConnection instance = new DSAConnection();

    private final WeakHashMap<Key, AddressConvertResult> map;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private DirectorySession ds;
    private DN mdLookupDN;
    private DN usrLookupDN;
    private DN aftnLookupDN;
    private String mdLookupDNName;
    private String usrLookupDNName;
    private String aftnLookupName;
    private String dsaServer;
    private long expiredPeriod;
    private long maintainPeriod;
    private boolean cachingEnabled;
    private boolean checkAsym;

    private DSAConnection() {
        cachingEnabled = false;
        map = new WeakHashMap<>();
        DSapi.initialize();
    }

    public void config(AddressConversion config) {
        this.dsaServer = config.getDsaPresentationAdd();
        this.aftnLookupName = config.getAftbLookupTable();
        this.mdLookupDNName = config.getDnName();
        this.usrLookupDNName = config.getUserLookupTable();
        this.expiredPeriod = config.getExpiredPeriod();
        this.maintainPeriod = config.getMaintainPeriod();
        this.cachingEnabled = config.isCacheEnable();
        if (cachingEnabled) {
            scheduler.scheduleAtFixedRate(this, maintainPeriod, maintainPeriod, TimeUnit.MILLISECONDS);
        }
    }

    public synchronized AddressConvertResult convertToAftnAddress(String address, boolean isOriginAddress) throws DSAPIException {

        logger.debug("Converting {} {} --> AFTN format", address, (isOriginAddress ? "[OR]" : ""));

        if (cachingEnabled) {
            Key key = new Key(address, isOriginAddress);
            if (map.containsKey(key)) {
                logger.debug("<<< Cache: {}", key);
                final AddressConvertResult convertedResult = map.get(key);
                convertedResult.reset();
                return convertedResult;
            }
        }

        this.connect();

        try {

            final String converted = toAFTN(address, isOriginAddress);
            if (converted == null) {
                return null;
            }

            String backwardAddress = toAMHS(converted, isOriginAddress);
            AddressConvertResult convertResult = new AddressConvertResult(address, converted, backwardAddress);

            if (cachingEnabled) {
                final Key key = new Key(address, isOriginAddress);
                map.put(key, convertResult);
                logger.debug(">>> Cache: {} [{} items]", key, map.size());
            }

            return convertResult;
        } catch (Exception ex) {
            this.close();

            String error = String.format("Convert address %s fail. %s", address, ex.getMessage());

            if (ex instanceof DSAPIException) {
                throw new DSAPIException(error, ex);
            }

            throw new DSAPIException("Convert fail", ex);

        }

    }

    public synchronized AddressConvertResult convertToAmhsAddress(String aftnAddress, boolean isOriginAddress) throws DSAPIException {

        logger.debug("Converting {} {} --> AMHS format", aftnAddress, (isOriginAddress ? "[OR]" : ""));

        if (!validateAFTNAddress(aftnAddress)) {
            logger.warn("Address {} is invalid.", aftnAddress);
            return null;
        }

        // Get from caching
        AddressConvertResult convertedResult;
        if (cachingEnabled) {
            final Key key = new Key(aftnAddress, isOriginAddress);
            if (map.containsKey(key)) {
                logger.debug("<<< Cache: {}", key);
                convertedResult = map.get(key);
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

            final String backwardAddress = toAFTN(converted, isOriginAddress);
            convertedResult = new AddressConvertResult(aftnAddress, converted, backwardAddress);

            if (cachingEnabled) {
                final Key key = new Key(aftnAddress, isOriginAddress);
                map.put(key, convertedResult);
                logger.debug(">>> Cache: {} [{} items]", key, map.size());
            }

            /* DUC VIET DA LAY TU DIR */
            
            
            
            readDSA readdsa = new readDSA();
            dsaChannelData dsadsata = readdsa.ReadVal(aftnAddress);

	    if (dsadsata == null) {	//  
		convertedResult.setDirect(false);
                convertedResult.setIhe(false);
  	    } else {				//
                convertedResult.setDirect(dsadsata.isAtn_amhs_direct_access());
                convertedResult.setIhe(dsadsata.isAtn_ipm_heading_extensions());
            }
            
            return convertedResult;
            

        } catch (Exception ex) {

            this.close();

            String error = String.format("Convert address " + aftnAddress + " fail. " + ex.getMessage());

            if (ex instanceof DSAPIException) {
                throw new DSAPIException(error, ex);
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
    
    private Boolean isVVAddress(String address) {
        return address.startsWith("VV") || address.contains("/CN=VV") || address.contains("/OU=VV") || address.contains("/OU1=VV");
    }

    @Override
    public void run() {
        try {
            logger.debug("Begin cleanint up Cache. (Amount: {} items)", this.map.size());

            final Iterator it = map.keySet().iterator();
            int count = 0;
            int index = 0;
            Key key;
            AddressConvertResult item;
            while (it.hasNext()) {
                index++;
                key = (Key) it.next();
                item = this.map.get(key);
                if (!item.isExpireUpdate(expiredPeriod)) {
                    continue;
                }

                it.remove();
                count++;
                logger.warn(" ** Remove cache item {}", key);

            }
            item = null;
            logger.debug("End cleanup. (Remove: {} remain: {})", count, this.map.size());
        } catch (Exception ex) {
            logger.error("ERROR FOUND!", ex);
        }
    }

    /*
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
     */
    
    
    
    private void connect() throws DSAPIException {
        try {

            if (ds == null) {
                logger.debug("Connecting to {}", dsaServer);
                ds = new DirectorySession(dsaServer);
                ds.bind(null);
                mdLookupDN = new DN(mdLookupDNName);
                usrLookupDN = new DN(usrLookupDNName);
                aftnLookupDN = new DN(aftnLookupName);

                return;
            }

            DirectorySession.ConnectionState connectionState = ds.getConnectionState();

            switch (connectionState) {
                case UNBOUND:
                case NOT_YET_CONNECTED:
                case VERIFICATION_FAILED:
                case CONNECTION_STATE_UNKNOWN:
                case CONNECTION_LOST:
                    logger.debug("Connecting to {}", dsaServer);
                    ds = new DirectorySession(dsaServer);
                    ds.bind(null);
                    mdLookupDN = new DN(mdLookupDNName);
                    usrLookupDN = new DN(usrLookupDNName);
                    aftnLookupDN = new DN(aftnLookupName);
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
//            throw new DSAPIException("Connect fail", ex);
            throw ex;
        }
    }

    private void close() {
        try {

            if (this.ds == null || !this.ds.isBound()) {
                return;
            }
            this.ds.unbind();
        } catch (Exception ex) {
            logger.error("ERROR FOUND!", ex);
        } finally {
            logger.info("Closed");
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

    private String toAFTN(String address, boolean isOriginAddress) throws DSAPIException {

        String result;
        if (isOriginAddress && isVVAddress(address)) {
            result = toAFTN(aftnLookupDN, address);
            if (result != null) {
                return result;
            }
        }

        result = toAFTN(mdLookupDN, address);
        return (result != null) ? result : AddressUtil.getShort(address);
    }

    private String toAMHS(String address, boolean isOriginAddress) throws DSAPIException {

        String result;
        if (isOriginAddress && isVVAddress(address)) {
            result = toAMHS(aftnLookupDN, address);
            if (result != null) {
                return result;
            }
        }

        String add = toAMHS(mdLookupDN, address);
        return add;

    }

    private String toAMHS(DN dn, String address) throws DSAPIException {

        try {

            ATNds.ATNdsResult result = ATNds.convertAFTN2AMHS(ds, dn, address);
            return result.getString();

        } catch (BadValueException ex) {
            logger.warn("Invalid address {}", address);
            return null;
        }
    }

    
    
    
    private String toAFTN(DN dn, String address) throws DSAPIException {
        try {

            ATNds.ATNdsResult result = ATNds.convertAMHS2AFTN(ds, dn, address); //ATNds.convertAMHS2AFTN(
            String tmp = result.getString();
            return tmp;

        } catch (BadValueException ex) {
            logger.warn("Invalid address {}", address);
            return null;
        } catch (DSAPIException ex) {

            switch (ex.getNativeErrorCode()) {
                case DSAPIException.DS_E_NOTFOUND:
                    logger.warn("DSAConnection.410 Search address {} is not found under {}", address, dn);
                    return null;

                case DSAPIException.DS_E_BADPARAM:
                    logger.warn("DSAConnection.414 Search address {} has bad parameter under {}", address, dn);
                    return null;

                default:
                    throw ex;
            }
        } catch (NativeLibraryException ex) {
            logger.warn("Search address {} fail due to NativeLibrary. {}", address, ex.getMessage());
            return null;
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

    private String makeKey(String value, boolean isOriginAddress) {
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
        // DOMConfigurator.configure("config/log.xml");
        Config.configure(Resources.GATEWAY_CONFIG);

//        try {
//            throw  new Exception("HELLO EXCEPTION");
//        } catch (Exception ex) {
//            logger.error("ERROR FOUND:", ex);
//        }
        DSAConnection.getInstance().config(Config.instance.getAddressConversion());
        List<String> addresses = buildAftnAddressList();

        for (String address : addresses) {
            testConvertion2(address);
        }

        DSAConnection.instance.connect();
        String result = DSAConnection.instance.toAMHS("VTBBYMYX", true);
        System.out.println("Result: " + result);

        result = DSAConnection.instance.toAFTN("/CN=VTBBYMYX/OU=VTBB/O=VTBB/PRMD=THAILAND/ADMD=ICAO/C=XX/", false);
        System.out.println("Result: " + result);

        result = DSAConnection.instance.toAFTN("/CN=VTBBYMYX/OU=VTBB/O=VTBB/PRMD=THAILAND/ADMD=ICAO/C=XX/", true);
        System.out.println("Result: " + result);

        result = DSAConnection.instance.toAFTN("/CN=RROUYZYZ/OU=RROU/O=RROU/PRMD=THAILANDSSSSSSS/ADMD=ICAO/C=XX/", true);
        System.out.println("Result: " + result);

//        // AddressConvertResult result = DSAConnection.instance.convertToAmhsAddress("VTBBZYZX", false);
//         String result = DSAConnection.instance.toAMHS("VTBBZYZX", false);
//        System.out.println("Result: " + result);
//
//        result = DSAConnection.instance.toAMHS("RRZFSDFG", false);
//        System.out.println("Result: " + result);
//        
//        String result = DSAConnection.instance.toAMHS("VTBBZYZX", false);
//        System.out.println("Result: " + result);
//        
//        AddressConvertResult result2 = DSAConnection.instance.convertToAmhsAddress("RRZFSDFGSS", false);
//          System.out.println("Result: " + result);
    }

    private static void testConvertion2(String amhsAdd) throws Exception {

        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("Test value: " + amhsAdd);

        AddressConvertResult result = DSAConnection.instance.convertToAmhsAddress(amhsAdd, true);
        System.out.println(result);

        result = DSAConnection.instance.convertToAmhsAddress(amhsAdd, false);
        System.out.println(result);

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
