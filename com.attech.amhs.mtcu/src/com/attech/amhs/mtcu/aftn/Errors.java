/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.aftn;

/**
 *
 * @author ANDH
 */
public enum Errors {

    NO_ERROR(0), 
    OMMIT_START_SIGNAL(1),
    LONG_MESSAGE(2),
    
    // Header error
    MSG_CID_ERROR(3),
    MSG_CSN_ERROR(4),
    MSG_CSN_REPEATED(5),
    MSG_CSN_UNDER_EXPECTED(6),
    MSG_CSN_MIS(7),
    MSG_INVALID_HEADER(16),
    
    // Address error
    MSG_ADS_ERROR(8),
    MSG_PRIORITY_ERROR(9),
    
    OGN_ERROR(10),
    
    INVALID_CHARACTER(11),
    LINE_LENGTH_EXCEED_LIMIT(12),
    CONTENT_LENGTH_EXCEED_LIMIT(13),
    INVALID_FORMAT(14),
    NO_MESSAGE(15);

    private final int code;

    /**
     * Initializing Error code
     * @param code 
     */
    Errors(int code) {
        this.code = code;
    }

    /**
     * return error code
     * @return 
     */
    public int getValue() {
        return code;
    }
}
