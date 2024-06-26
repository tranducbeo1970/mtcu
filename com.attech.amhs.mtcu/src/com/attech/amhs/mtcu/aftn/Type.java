/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.aftn;

/**
 *
 * @author andh
 */
public enum Type {

    GENERAL(0),
    SERVICE(1),
    CHECK(2),
    ACKNOWLEDGE(3),
    UNKNOWN(4),
    RY(5);

    private final int code;

    /**
     * Initializing Error code
     *
     * @param code
     */
    Type(int code) {
        this.code = code;
    }

    /**
     * return error code
     *
     * @return
     */
    public int getValue() {
        return code;
    }
}
