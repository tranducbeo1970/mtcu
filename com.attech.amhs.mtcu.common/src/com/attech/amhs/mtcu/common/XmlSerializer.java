/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.common;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author root
 */
public class XmlSerializer {

    public static void serialize(String location, Object value) throws IOException, JAXBException {
        try (FileOutputStream os = new FileOutputStream(location)) {
            JAXBContext context = JAXBContext.newInstance(value.getClass());
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler() {
                @Override
                public void escape(char[] ac, int i, int j, boolean flag, Writer writer) throws IOException {
                    writer.write(ac, i, j);
                }
            });
            m.marshal(value, os);
        }
    }

    public static Object deserialize(String location, Class<?> cls) throws JAXBException, IOException {
        // JAXBContext context;
        JAXBContext context = JAXBContext.newInstance(cls);
        Unmarshaller m = context.createUnmarshaller();

        try (FileInputStream stream = new FileInputStream(new File(location))) {
            return m.unmarshal(stream);
        }
    }

    public void save(String file) throws IOException, JAXBException {
        XmlSerializer.serialize(file, this);
    }

    @SuppressWarnings("unchecked")
    public static <T extends XmlSerializer> T load(String file, Class<T> type) throws JAXBException, IOException {
        return (T) XmlSerializer.deserialize(file, type);
    }
}
