/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author ANDH
 */
public class DsapiSample {

    public static void main(String[] args) {
        DSapi.initialize();
        DirectorySession ds = null;
        Indication ind = null;
        try {
            ds = new DirectorySession("URI+0000+URL+itot://192.168.1.201:19999");
            ds.bind(null); // anonymous bind

            ind = ds.searchSubTree(
                    new DN("ou=staff,o=big corp"),
                    DirectorySession.MATCHALL, // match all entries
                    new Selection());          // request all user attributes
            // No exception was thrown so the search returned something
            for (Entry entry : ind) {
                System.out.println(entry.getDN());
                for (Attribute attribute : entry) {
                    System.out.println("  " + attribute.getAttributeName() + ":");
                    for (AttributeValue attributeValue : attribute) {
                        System.out.println("    " + attributeValue);
                    }
                }
            }
        } catch (DSAPIException e) {
            System.out.println("Exception: " + e);
        }
    }
}
