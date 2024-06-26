/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.common;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 *
 * @author andh
 */
public class XmlCharacterHandler implements CharacterEscapeHandler {

    @Override
    public void escape(char[] buf, int start, int len, boolean isAttValue, Writer out) throws IOException {
        StringWriter buffer = new StringWriter();
        for (int i = start; i < start + len; i++) {
            buffer.write(buf[i]);
        }
        String st = buffer.toString();
        if (!st.contains("CDATA")) {
            st = buffer.toString().replace("&", "&amp;").replace("<", "&lt;")
                    .replace(">", "&gt;").replace("'", "&apos;")
                    .replace("\"", "&quot;");
        }
        out.write(st);
        System.out.println(st);
    }
}
