/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu;

/**
 *
 * @author ANDH
 */
public class MtAttributes {

    // Reason code
    public static final int RS_TRANFER_FAIL = 0;
    public static final int RS_UNABLE_TO_TRANSFER = 1;
    public static final int RS_CONVERSION_NOT_PERFORM = 2;
    public static final int RS_PHYSICAL_RENDITION_NOT_PERFORM = 3;
    public static final int RS_PHYSICAL_DELIVERY_NOT_PERFORM = 4;
    public static final int RS_RETRICTED_DELIVERY = 5;
    public static final int RS_DIRECTORY_OPERATION_UNSUCCESSFUL = 6;
    public static final int RS_DEFERRED_DELIVERY_NOT_PERFORM = 7;

    // Dianogtic reason code
    public static final int D_UNRECOGNISED_OR_NAME = 0;
    public static final int D_AMBIGUOUS_OR_NAME = 1;
    public static final int D_MTS_CONGESTION = 2;
    public static final int D_LOOP_DETECTED = 3;
    public static final int D_RECIPIENT_UNAVAILABLE = 4;
    public static final int D_MAXIMUM_TIME_EXPIRED = 5;
    public static final int D_ENCODED_INFORMATION_TYPES_UNSUPPORTED = 6;
    public static final int D_CONTENT_TOO_LONG = 7;
    public static final int D_CONVERSION_IMPRACTICAL = 8;
    public static final int D_IMPLICIT_CONVERSION_PROHIBITED = 9;
    public static final int D_CONVERSION_NOT_SUBSCRIBED = 10;
    public static final int D_INVALID_ARGUMENTS = 11;
    public static final int D_CONTENT_SYNTAX_ERROR = 12;
    public static final int D_SIZE_CONSTRAINT_VIOLATION = 13;
    public static final int D_PROTOCOL_VIOLATION = 14;
    public static final int D_CONTENT_TYPE_NOT_SUPPORTED = 15;
    public static final int D_TOO_MANY_RECIPIENTS = 16;
    public static final int D_NO_BILATERAL_AGREEMENT = 17;
    public static final int D_UNSUPPORTED_CRITICAL_FUNCTION = 18;
    public static final int D_CONVERSION_WITH_LOSS_PROHIBITED = 19;
    public static final int D_LINE_TOO_LONG = 20;
    public static final int D_PAGE_SPLIT = 21;
    public static final int D_PICTORIAL_SYMBOL_LOSS = 22;
    public static final int D_PUNCTUATION_SYMBOLS_LOSS = 23;
    public static final int D_ALPHABETIC_SYMBOLS_LOSS = 24;
    public static final int D_MULTIPLE_INFORMATION_LOSS = 25;
    public static final int D_RECIPIENT_REASSIGNMENT_PROHIBITED = 26;
    public static final int D_REDIRECTION_LOOP_DETECTED = 27;
    public static final int D_DL_EXPANSION_PROHIBITED = 28;
    public static final int D_NO_DL_SUBMIT_PERMISSION = 29;
    public static final int D_DL_EXPANSION_FAILURE = 30;
    public static final int D_PHYSICAL_RENDITION_ATTRIBUTES_NOT_SUPPORTED = 31;
    public static final int D_UNDELIVERABLE_MAIL_PHYSICAL_DELIVERY_ADDRESS_INCORRECT = 32;
    public static final int D_UNDELIVERABLE_MAIL_PHYSICAL_DELIVERY_OFFICE_INCORRECT_OR_INVALID = 33;
    public static final int D_UNDELIVERABLE_MAIL_PHYSICAL_DELIVERY_ADDRESS_INCOMPLETE = 34;
    public static final int D_UNDELIVERABLE_MAIL_RECIPIENT_UNKNOWN = 35;
    public static final int D_UNDELIVERABLE_MAIL_RECIPIENT_DECEASED = 36;
    public static final int D_UNDELIVERABLE_MAIL_ORGANIZATION_EXPIRED = 37;
    public static final int D_UNDELIVERABLE_MAIL_RECIPIENT_REFUSED_TO_ACCEPT = 38;
    public static final int D_UNDELIVERABLE_MAIL_RECIPIENT_DID_NOT_CLAIM = 39;
    public static final int D_UNDELIVERABLE_MAIL_RECIPIENT_CHANGED_ADDRESS_PERMANENTLY = 40;
    public static final int D_UNDELIVERABLE_MAIL_RECIPIENT_CHANGED_ADDRESS_TEMPORARILY = 41;
    public static final int D_UNDELIVERABLE_MAIL_RECIPIENT_CHANGED_TEMPORARY_ADDRESS = 42;
    public static final int D_UNDELIVERABLE_MAIL_NEW_ADDRESS_UNKNOWN = 43;
    public static final int D_UNDELIVERABLE_MAIL_RECIPIENT_DID_NOT_WANT_FORWARDING = 44;
    public static final int D_UNDELIVERABLE_MAIL_ORIGINATOR_PROHIBITED_FORWARDING = 45;
    public static final int D_SECURE_MESSAGE_ERROR = 46;
    public static final int D_UNABLE_TO_DOWNGRADE = 47;
    public static final int D_UNABLE_TO_COMPLETE_TRANSFER = 48;
    public static final int D_TRANSFER_ATTEMPTS_LIMIT_REACHED = 49;

    // MTA REPORT REQUEST
    public static final int MTA_NON_DELIVERY_REPORT = 1;
    public static final int MTA_REPORT = 2;
    public static final int MTA_AUDITED_REPORT = 3;

    // REPORT REQUEST
    public static final int ORIGIN_NO_REPORT = 0;
    public static final int ORIGIN_NON_DELIVERY_REPORT = 1;
    public static final int ORIGIN_REPORT = 2;
    
    // IMPLICIT CONVERSION PROHIBITED
    public static final int CONVERSION_PROHIBITED_NO = 0;
    public static final int CONVERSION_PROHIBITED_YES = 1;
    
    // Conversion Condition Error
    public static final int E_NO_ERROR = 0;
    public static final int E_CONVERSION_PROHITED = 1;
    public static final int E_BODYPART_TYPE_NOT_SUPPORT = 2;
    public static final int E_MULTI_BODYPART = 3;
    public static final int E_ATS_MESSAGE_HEADER_ERROR = 4;
    public static final int E_EXCEED_MAXIMUM_OF_RECIPIENTS = 5;
    public static final int E_CONTENT_CONTAIN_INVALID_SYMBOLS = 6;
    public static final int E_CONTENT_CONTAIN_INVALID_CHARACTER = 7;
    public static final int E_CONTENT_CONTAIN_INVALID_INFO = 8;
    public static final int E_LINE_TOO_LONG = 9;
    public static final int E_MESSAGE_TO_LONG = 10;
    public static final int E_MESSAGE_UNSUPPORT_CONTENT_TYPE = 11;
    public static final int E_MESSAGE_UNSUPPORT_ENCODED_INFORMATION_TYPE = 12;
    public static final int E_DISTRIBUTION_PROHIBITED = 13;
    public static final int E_INVALID_CHARACTERSET = 14;
    
    // Priority
    public static final int PRIORITY_NORMAL = 0;
    public static final int PRIORITY_NON_URGENT = 1;
    public static final int PRIORITY_URGENT = 2;
    
    // Built-in content type
    public static final int BI_CONTENT_TYPE_UNIDENTIFIED = 0;
    public static final int BI_CONTENT_TYPE_EXTERNAL = 1;
    public static final int BI_CONTENT_TYPE_IPM_1984 = 2;
    public static final int BI_CONTENT_TYPE_IPM_1988 = 22;
    public static final int BI_CONTENT_TYPE_EDI_MESSAGE = 35;
    public static final int BI_CONTENT_TYPE_VOICE_MESSAGE = 40;
    
    
    // Encode infomation type
    public static final String EIT_IA5_TEXT = "ia5-text";
    public static final String EIT_OID_1 = "OID {id-cs-eit-authority 1}"; // ISO 8859-1
    public static final String EIT_OID_2 = "OID {id-cs-eit-authority 2}";
    public static final String EIT_OID_6 = "OID {id-cs-eit-authority 6}"; // G0 set
    public static final String EIT_OID_100 = "OID {id-cs-eit-authority 100}"; // G1 set
    
    
}
