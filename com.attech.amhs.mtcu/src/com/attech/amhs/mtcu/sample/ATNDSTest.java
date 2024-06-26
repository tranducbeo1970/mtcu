/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.attech.amhs.mtcu.sample;

import com.isode.dsapi.Attribute;
import com.isode.dsapi.AttributeValue;
import com.isode.dsapi.DN;
import com.isode.dsapi.DSAPIException;
import com.isode.dsapi.DSapi;
import com.isode.dsapi.DirectorySession;
import com.isode.dsapi.Entry;
import com.isode.dsapi.Indication;
import com.isode.dsapi.Selection;
import com.isode.dsapi.atnds.ATNds;
import com.isode.dsapi.atnds.ATNds.ATNdsResult;

/**
 *
 * @author andh
 */
public class ATNDSTest {

    public static void main(String[] args) {

        try {
            // Initialize DSAPI
            DSapi.initialize();

            // Bind anonymously to the DSA
            // DirectorySession ds = new DirectorySession("ldap://localhost:19389");
            DirectorySession ds = new DirectorySession("URI+0000+URL+itot://localhost:19999");
            
            
            ds.bind(null);
            System.out.println("Bound to the DSA");

            // Set the place in the DIT where the ATN Directory Data is found
            // The following DN is used by Isode to store test data
            //DN registryDN = new DN("o=Isode-MD-Register");
            // This this is used by EDS (the European Directory Service)
            // DN registryDN = new DN("o=ICAO-MD-Registry, ou=Pre-operational,o=European-Directory");
            DN registryDN = new DN("o=Isode-MD-Register");
            // DN registryDN = new DN("o=address-lookup,o=conversion-address-lookup-definition");
            System.out.println("Searching under " + registryDN.toString());

            /////////////////////////////////////////////////////////////
            // Convert an AFTN address into XF-AMHS
            String xfAFTNAddress = "EGLLAMHS";

            System.out.println("\nConverting " + xfAFTNAddress);

            // Convert the AFTN address to AMHS
            ATNdsResult amhsConversionResult = ATNds.convertAFTN2AMHS(ds, registryDN, xfAFTNAddress);

            // Show the conversion result
            System.out.println("O/R address = " + amhsConversionResult.getString());
            System.out.println("DN = " + amhsConversionResult.getDN());

            /////////////////////////////////////////////////////////////
            // Convert an AFTN address into CAAS-AMHS
            String caasAFTNAddress = "SUMUYFYX";

            System.out.println("\nConverting " + caasAFTNAddress);

            // Convert the AFTN address to AMHS
            amhsConversionResult = ATNds.convertAFTN2AMHS(ds, registryDN, caasAFTNAddress);

            // Show the conversion result
            System.out.println("O/R address = " + amhsConversionResult.getString());
            System.out.println("DN = " + amhsConversionResult.getDN());
            
            
            
            caasAFTNAddress = "VTBBZQZY";

            System.out.println("\nConverting " + caasAFTNAddress);

            // Convert the AFTN address to AMHS
            amhsConversionResult = ATNds.convertAFTN2AMHS(ds, registryDN, caasAFTNAddress);

            // Show the conversion result
            System.out.println("O/R address = " + amhsConversionResult.getString());
            System.out.println("DN = " + amhsConversionResult.getDN());
            
            
            
            caasAFTNAddress = "VVTSAMHY";

            System.out.println("\nConverting " + caasAFTNAddress);

            // Convert the AFTN address to AMHS
            amhsConversionResult = ATNds.convertAFTN2AMHS(ds, registryDN, caasAFTNAddress);

            // Show the conversion result
            System.out.println("O/R address = " + amhsConversionResult.getString());
            System.out.println("DN = " + amhsConversionResult.getDN());


            /////////////////////////////////////////////////////////////
            // Provide an AMHS address in the expected string format
            String amhsAddress = "/CN=VVTSOPTA/OU=VVTS/O=VVTS/PRMD=VIETNAM/ADMD=ICAO/C=XX/";

            System.out.println("\nConverting " + amhsAddress);

            // Convert the AMHS address to AFTN
            ATNdsResult aftnConversionResult = ATNds.convertAMHS2AFTN(ds, registryDN, amhsAddress);

            // Show the conversion result
            System.out.println("AFTN address = " + aftnConversionResult.getString());
            System.out.println("DN = " + aftnConversionResult.getDN());
            
            
            
            amhsAddress = "/OU=AYPYANGM/O=AFTN/PRMD=SITA/ADMD=ICAO/C=XX/";

            System.out.println("\nConverting " + amhsAddress);

            // Convert the AMHS address to AFTN
            aftnConversionResult = ATNds.convertAMHS2AFTN(ds, registryDN, amhsAddress);

            // Show the conversion result
            System.out.println("AFTN address = " + aftnConversionResult.getString());
            System.out.println("DN = " + aftnConversionResult.getDN());
            
            

            /////////////////////////////////////////////////////////////
            // User with an entry in EDS
            DN baseDN = new DN("ou=Pre-operational,o=European-Directory");
            String result = searchSpecificUser(ds, "ou=Pre-operational,o=European-Directory", "LOWWYCYX");

        } catch (DSAPIException e) {
            System.out.println("Conversion failed : " + e);
        }

    }

    /**
     * Search for a specific AFTN user
     *
     * @param ds
     * @param baseDN
     * @param aftnAddress
     * @return
     */
    private static String searchSpecificUser(DirectorySession ds, String baseDN, String aftnAddress) throws DSAPIException {

        String filter = "(&(objectClass=atn-amhs-user)(atn-af-address=" + aftnAddress + "))";

        Indication ind = null;
        ind = ds.searchSubTree(
                new DN(baseDN),
                filter, // match all user entries
                new Selection()); // request all user attributes

        if (ind == null || ind.getEntryCount() == 0) {
            System.out.println("\nAddress " + aftnAddress + " not found");
            return null;
        }

        // No exception was thrown so the search returned something
        System.out.println("\nFound address " + aftnAddress + ", listing");
        String amhsAddress = null;
        for (Entry entry : ind) {
            System.out.println(entry.getDN());
            for (Attribute attribute : entry) {
                System.out.println("  " + attribute.getAttributeName() + ":");
                for (AttributeValue attributeValue : attribute) {
                    System.out.println("    " + attributeValue);
                    // Save the
                    if (attribute.getAttributeName().equals("mhsORAddresses")) {
                        amhsAddress = attributeValue.getString();
                    }
                }
            }
        }

        return amhsAddress;
    }
}
