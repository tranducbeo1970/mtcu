/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.isode;

/**
 *
 * @author ANDH
 */
public class MessageContent {

    private int type;
    private String content;
    private String characterSet;

    public MessageContent() {
    }

    public MessageContent(int type, String content, String charset) {
        this.type = type;
        this.content = content;
        this.characterSet = charset;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the characterSet
     */
    public String getCharacterSet() {
        return characterSet;
    }

    /**
     * @param characterSet the characterSet to set
     */
    public void setCharacterSet(String characterSet) {
        this.characterSet = characterSet;
    }
    
    
}
