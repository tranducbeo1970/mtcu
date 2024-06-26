/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.dsa;

import java.util.Objects;

/**
 *
 * @author root
 */
public class Key {

    private String key;

    public Key(String value, boolean isOriginAddress) {
        this.key = getKey(value, isOriginAddress);
    }

    private String getKey(String value, boolean isOriginAddress) {
        if (isOriginAddress) {
            return String.format("%s:ORG", value);
        }
        return value;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (!(that instanceof Key)) {
            return false;
        }
        Key thatPeople = (Key) that;

        if (this.key.equalsIgnoreCase(thatPeople.toString())) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public String toString() {
        return this.key;
    }

    @Override
    /* Overriding finalize method to check which object 
     is garbage collected */
    protected void finalize() throws Throwable {
        super.finalize();
        // will print name of object 
        System.out.println(this.getClass() + " successfully garbage collected [" + this.key + "]");
    }
}
