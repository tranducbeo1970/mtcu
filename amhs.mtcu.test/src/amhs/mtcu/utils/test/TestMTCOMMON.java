/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.mtcu.utils.test;

import amhs.mt.MtCommon;

/**
 *
 * @author andh
 */
public class TestMTCOMMON {
    
    
    public static void main(String [] args) {
        String original = "/CN=VVNBYFYX/OU=VVNB/PRMD=VV/ADMD=ICAO/C=XX/";
        System.out.println(MtCommon.generateIpmId(original));
    }
    
}
