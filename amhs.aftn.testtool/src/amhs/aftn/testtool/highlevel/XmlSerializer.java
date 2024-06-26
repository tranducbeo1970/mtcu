/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amhs.aftn.testtool.highlevel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author root
 */
public class XmlSerializer {

    public static void serialize(String location, Object value) {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(location);
            JAXBContext context = JAXBContext.newInstance(value.getClass());
            Marshaller m = context.createMarshaller();
            m.marshal(value, os);
        } catch (FileNotFoundException | JAXBException ex) {
            Logger.getLogger(XmlSerializer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(XmlSerializer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static Object deserialize(String location, Class<?> cls) {
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(cls);
            Unmarshaller m = context.createUnmarshaller();
            FileInputStream stream = new FileInputStream(new File(location));
            return m.unmarshal(stream);
        } catch (JAXBException | FileNotFoundException ex) {
            Logger.getLogger(XmlSerializer.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
