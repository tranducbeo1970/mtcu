/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.common;

import java.io.IOException;
import javax.xml.bind.JAXBException;

/**
 *
 * @author ANDH
 */
public class ConfigBase {

    public void save(String file) throws IOException, JAXBException {
        XmlSerializer.serialize(file, this);
    }

    @SuppressWarnings("unchecked")
    public static <T extends ConfigBase> T load(String file, Class<T> type) throws JAXBException, IOException {
        return (T) XmlSerializer.deserialize(file, type);
    }
}
