package com.attech.amhs.mtcu.aftn;

/**
 *
 * @author ANDH
 */
public class MessageAttributes {

    public static final int MSG_TYPE_NA = 0;
    public static final int MSG_TYPE_NORMAL = 1;
    public static final int MSG_TYPE_SERVICE = 2;
    public static final int MSG_TYPE_CHECK = 3;

    // Validate message
    public static final int MSG_CHK_INVALID_FORMAT = -1;
    
    public static final int MSG_CHK_NOERROR = 0;
    
    public static final int MSG_CHK_CONTAIN_QTA = 10;
    public static final int MSG_CHK_MISSING_END_SIGNAL = 11;
    public static final int MSG_CHK_MISSING_START_SIGNAL = 12;
    public static final int MSG_CHK_CONTENT_OVER_LENGTH = 13;
    public static final int MSG_CHK_LONG_MESSAGE = 14;
    public static final int MSG_CHK_CONTAIN_INVALID_CHARACTER = 15;
    
    public static final int MSG_CID_ERROR = 20;
    public static final int MSG_CSN_ERROR = 21;
    public static final int MSG_CSN_REPEATED = 22; // The csn is repeated 
    public static final int MSG_CSN_MIS = 23; // csn is greater than expected
    public static final int MSG_CSN_UNDER_EXPECTED = 24; // csn is less than expected
    
    public static final int MSG_ADS_ERROR = 30;
    public static final int MSG_PRIORITY_ERROR = 31;
    
    public static final int MSG_CHK_TXT_LINE_OVER_LENGTH = 40;
    public static final int MSG_CHK_TXT_INVALID_CHARACTER = 41;
    public static final int MSG_CHK_TXT_OVER_LENGTH = 42;


    public static final int MSG_CHK_INVALID_HEADER = 1;
    public static final int MSG_CHK_INVALID_ADDRESS = 2;
    public static final int MSG_CHK_INVALID_ORIGINATOR = 3;
    public static final int MSG_CHK_INVALID_CONTENT = 4;
    public static final int MSG_CHK_INVALID_ENDING = 5;

    // Message content;
    public static final String MSG_CONTENT_START = "ZCZC";
    public static final String MSG_CONTENT_END = "NNNN";
    public static final String MSG_CONTENT_CHECKING = "CH";
    public static final String MSG_CONTENT_SERVICE = "SVC";
    public static final String MSG_CONTENT_QTA = "QTA QTA";
    public static final String MSG_CONTENT_ADDED_ENDING = "CHECK TEXT NEW ENDING ADDED VVNBYFYX\r\n\n\n\n\n\n\n\nNNNN";
    public static final String MSG_CONTENT_SEPERATOR = "\r\n";
    
    public static final int MSG_MINIMUM_LINE = 3;
    public static final int MSG_MAXIMUM_CHARACTER = 2100;
    public static final int MSG_MAXIMUM_CONTENT = 1800;
    public static final int MSG_MAXIMUM_CHARACTER_PER_LINE = 69;
    
    public static final String LOG_PROCESSING = "P";
    public static final String LOG_ERROR = "E";
    public static final String LOG_OK = "O";
    public static final String LOG_WARNING = "W";

}
