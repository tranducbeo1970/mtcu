/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.mtcu.utils.test;

import amhs.database.CommonUtils;
import amhs.mt.AddressConvertor;
import amhs.mtcu.config.AddressConversion;
import amhs.mtcu.config.Config;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author andh
 */
public class TestConvertionAddress {
    public static void main(String [] args) {
        DOMConfigurator.configure("config/log.xml");
        CommonUtils.configure("config/database.xml");
        Config.configure("config/gateway.xml");
        
        AddressConversion addressConvertCfg = Config.instance.getAddressConversion();
        AddressConvertor.connect(addressConvertCfg.getDsaPresentationAdd(), addressConvertCfg.getDnName(), addressConvertCfg.getUserLookupTable());
        AddressConvertor.amhs2aftn("/OU=VVNBXXXX/O=AFTN/PRMD=VV/ADMD=ICAO/C=XX/");
        System.out.println("Conversion 's OK");
    }
}
