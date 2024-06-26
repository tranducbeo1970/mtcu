/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.mtcu.utils.test;

import amhs.mtcu.config.EncodedInformationType;
import amhs.mtcu.config.X400Attributes;
import amhs.mtcu.utils.XmlSerializer;

/**
 *
 * @author andh
 */
public class TestSaveConfig {
    public static void main(String[] args) {

        /*
         ConfigCharacterSet config = new ConfigCharacterSet();
         EncodedInformationType charConfig = new EncodedInformationType();
         charConfig.setAllowEmpty(false);
         charConfig.setEncodedType("ia5-text");
         config.setCharacterSet(charConfig);
        
         XmlSerializer.serialize("D:\\config.xml", config);
         */
        X400Attributes x400Attributes = new X400Attributes();
        x400Attributes.addAttribute("X400_E_NOERROR", "0");
         XmlSerializer.serialize("D:\\att.xml", x400Attributes);
    }
}
