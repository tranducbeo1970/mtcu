/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.attech.amhs.mtcu.isode;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class SearchDSA {
    static public void main(String[] args) {

        String dns = "VVTSOPTA";
        readDSA dsa = new readDSA();
        dsaChannelData data = dsa.ReadVal(dns);

        if (data == null) {
            System.out.println("KHONG CO GI");
        } else {
            System.out.println(dns);
            if (data.isAtn_amhs_direct_access()) {
                System.out.println("DIRECT");
            } else {
                System.out.println("NO DIRECT");
            }
            if (data.isAtn_ipm_heading_extensions()) {
                System.out.println("USE IHE");
            } else {
                System.out.println("NOT USE IHE");
            }
        }
    }
}
