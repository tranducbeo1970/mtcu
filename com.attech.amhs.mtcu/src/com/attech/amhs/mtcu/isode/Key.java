/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.isode;

/**
 *
 * @author root
 */
public class Key {

    private final String key;
    
    public static Key create(String value, boolean isOrAddress) {
        return new Key(value, isOrAddress);
    }

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

        return this.hashCode() == thatPeople.hashCode();
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
    
    @Override
    public String toString() {
        return this.key;
    }

}
