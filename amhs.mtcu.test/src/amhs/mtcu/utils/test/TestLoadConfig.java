/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.mtcu.utils.test;

import amhs.mtcu.config.Config;

/**
 *
 * @author andh
 */
public class TestLoadConfig {
    public static void main(String [] args) {
        
        Config.configure("config/gateway.xml");
        
        System.out.println(Config.instance.getAddressConversion().getDnName());
        System.out.println(Config.instance.getAddressConversion().getDsaPresentationAdd());
        System.out.println(Config.instance.getAmhsChannel().getAllowedCharacters());
        System.out.println(Config.instance.getAmhsChannel().getAllowEIT().getEncodedType().size());
        System.out.println(Config.instance.getAmhsChannel().getAllowEIT().isAllowEmpty());
        System.out.println(Config.instance.getAmhsChannel().getCharacterLimit());
        System.out.println(Config.instance.getAmhsChannel().getAddressLimit());
        System.out.println("Allow Character Set: " + Config.instance.getAmhsChannel().getAllowedCharacterSet());
    }
}
