package amhs.mtcu.utils.test;
/*  Copyright (c) 2013, Isode Limited, London, England.
 *  All rights reserved.
 *                                                                       
 *  Acquisition and use of this software and related materials for any      
 *  purpose requires a written licence agreement from Isode Limited,
 *  or a written licence from an organisation licenced by Isode Limited
 *  to grant such a licence.
 *
 */

import com.isode.dsapi.DN;
import com.isode.dsapi.DSAPIException;
import com.isode.dsapi.DSapi;
import com.isode.dsapi.DirectorySession;
import com.isode.dsapi.atnds.ATNds;
import com.isode.dsapi.atnds.ATNds.ATNdsResult;

public class ATNDirectoryTest {

    // Configurable
    private static String dsaPresenatationAddress = "URI+0000+URL+itot://192.168.22.171:19999";

    // Private
    private static DirectorySession ds;
    private static DN registryDN;

    public static void main(String[] args) {

        // Initialize the Directory Session
        try {
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        
         showConversion("VDCAMBOD");
         showConversion("EGKKAFTN");
         showConversion("SUMUYNYX");
         showConversion("KLAXTEST");
         showConversion("LZBRATIS");
         showConversion("VVIETNAM");
         showConversion("VTVTYNYX");
         showConversion("WSSSINGA");
         showConversion("VTBDAMHS");
         
        showConversion("VVNBYFYX");
    }

    /**
     * Initialize DSAPI, bind to the configured Directory Service and initialize
     * the registry DN If any of these steps fails, throw an Exception
     *
     * @throws Exception
     */
    private static void initialize() throws Exception {
        // This needs to be called before anything else is used by the DSA API
        DSapi.initialize();
        ds = new DirectorySession(dsaPresenatationAddress);
        ds.bind(null); // simple bind; no common args
        registryDN = new DN("o=Isode-MD-Register");
    }

    /**
     * Convert the AFTN address provided and return the AMHS O/R address
     * equivalent If the conversion fails, return null
     *
     * @param aftnAddress
     * @return
     */
    private static String convert(String aftnAddress) throws Exception {
        ATNdsResult conversionResult = null;
        conversionResult = ATNds.convertAFTN2AMHS(ds, registryDN, aftnAddress);
        if (conversionResult.getString() != null) {
            return conversionResult.getString();
        }
        return null;
    }

    /**
     * Convert the AFTN address provided and print information about it to
     * stdout
     *
     * @param aftnAddress
     */
    private static void showConversion(String aftnAddress) {

        ATNdsResult conversionResult = null;
        try {
            System.out.println("\nConverting AFTN address : " + aftnAddress);
            conversionResult = ATNds.convertAFTN2AMHS(ds, registryDN, aftnAddress);
            if (conversionResult.getString() != null) {
                System.out.println("AMHS O/R Address = " + conversionResult.getString());
            }
            if (conversionResult.getDN() != null) {
                System.out.println("User DN = " + conversionResult.getDN());
            }
        } catch (DSAPIException e) {
            System.out.println("Conversion failed : " + e);
            return;
        }

        // If an O/R address was returned, try reversing the conversion
        String ora = conversionResult.getString();
        if (ora != null) {
            try {
                System.out.println("Reverse conversion: AMHS O/R Address : " + ora);
                conversionResult = ATNds.convertAMHS2AFTN(ds, registryDN, ora);

                if (conversionResult.getString() != null) {
                    System.out.println("AFTN Address = " + conversionResult.getString());
                }
                if (conversionResult.getDN() != null) {
                    System.out.println("User DN = " + conversionResult.getDN());
                }
            } catch (DSAPIException e) {
                System.out.println("Reverse conversion failed : " + e);
            }
        }

    }

}
