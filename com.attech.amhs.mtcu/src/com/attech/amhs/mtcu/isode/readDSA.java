/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.attech.amhs.mtcu.isode;

import com.isode.dsapi.Attribute;
import com.isode.dsapi.AttributeValue;
import com.isode.dsapi.DN;
import com.isode.dsapi.DSAPIException;
import com.isode.dsapi.DSapi;
import com.isode.dsapi.DirectorySession;
import com.isode.dsapi.Entry;
import com.isode.dsapi.Indication;
import com.isode.dsapi.Selection;

/**
 *
 * @author root
 */
public class readDSA {
    
    
    
    public dsaChannelData ReadVal(String address)
            
    {
        
        dsaChannelData data = new dsaChannelData();
        String dns = "cn=" + address + ",cn=White Pages,o=messaging";
        DSapi.initialize();
        DirectorySession ds = null;
        Indication ind = null;
        
        try {
            //ds = new DirectorySession("ldap://10.10.1.1:19389");
            ds = new DirectorySession("URI+0000+URL+itot://localhost:19999");
            //ds = new DirectorySession("URI+0000+URL+itot://45.124.94.20:19999");
            ds.bind(null); // anonymous bind

            ind = ds.searchSubTree(
                    new DN(dns),
                    DirectorySession.MATCHALL, // match all entries
                    new Selection());          // request all user attributes
            // No exception was thrown so the search returned something
            int count  = 0;
            for (Entry entry : ind) {
                //System.out.println(entry.getDN());
                for (Attribute attribute : entry) {
                    String att_name = attribute.getAttributeName();
                    //System.out.print("ENTRY NO = " + count ++ );
                    //System.out.print("  att_name = " + att_name  + " : ");
                    
                    
                    //for (AttributeValue attributeValue : attribute) {
                    //    System.out.println("    " + attributeValue);
                    //    
                    //}
                    
                    if(att_name.equals("atn-amhs-direct-access")) {
                        String val = attribute.getValue(0).toString();
                        if(val.equals("TRUE")) {
                            data.setAtn_amhs_direct_access(true);
                        } else {
                            data.setAtn_amhs_direct_access(false);
                        }
                        
                    }
                    if(att_name.equals("atn-ipm-heading-extensions")) {
                        String val = attribute.getValue(0).toString();
                        if(val.equals("TRUE")) {
                            data.setAtn_ipm_heading_extensions(true);
                            
                        } else {
                            data.setAtn_ipm_heading_extensions(false);
                            
                        }
                        
                    }
                       
                   
                }
            }
            return(data);
        }
        catch (DSAPIException e) {
            System.out.println("Exception : " + e);
            System.out.println("KHONG CO");
            return(null);
        }
        
        
    }
}
