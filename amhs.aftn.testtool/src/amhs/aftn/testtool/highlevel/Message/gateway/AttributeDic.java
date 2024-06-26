/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amhs.aftn.testtool.highlevel.Message.gateway;

import java.util.Hashtable;

/**
 *
 * @author andh
 */
public class AttributeDic {

    private static AttributeDic instance;
    private final Hashtable<Integer, String> tables = new Hashtable<>();

    public AttributeDic() {

        tables.put(1, "X400_S_OR_ADDRESS");
        tables.put(100, "X400_S_MESSAGE_IDENTIFIER");
        tables.put(101, "X400_N_CONTENT_TYPE");
        tables.put(102, "X400_N_CONTENT_LENGTH");
        tables.put(103, "X400_S_CONTENT_IDENTIFIER");
        tables.put(104, "X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES");
        tables.put(105, "X400_N_PRIORITY");
        tables.put(106, "X400_N_DISCLOSURE");
        tables.put(107, "X400_N_IMPLICIT_CONVERSION_PROHIBITED");
        tables.put(108, "X400_N_ALTERNATE_RECIPIENT_ALLOWED");
        tables.put(109, "X400_N_CONTENT_RETURN_REQUEST");
        tables.put(110, "X400_S_MESSAGE_SUBMISSION_TIME");
        tables.put(111, "X400_S_MESSAGE_DELIVERY_TIME");
        tables.put(112, "X400_S_EXTERNAL_CONTENT_TYPE");
        tables.put(120, "X400_N_RECIPIENT_REASSIGNMENT_PROHIBITED");
        tables.put(121, "X400_N_DL_EXPANSION_PROHIBITED");
        tables.put(122, "X400_N_CONVERSION_WITH_LOSS_PROHIBITED");
        tables.put(123, "X400_S_LATEST_DELIVERY_TIME");
        tables.put(124, "X400_S_ORIGINATOR_RETURN_ADDRESS");
        tables.put(125, "X400_S_OBJECTTYPE");
        tables.put(126, "X400_N_MMTS_PRIORITY_QUALIFIER");
        tables.put(127, "X400_S_DEFERRED_DELIVERY_TIME");
        tables.put(128, "X400_S_DLEXP_TIME");
        tables.put(129, "X400_S_GLOBAL_DOMAIN_ID");
        tables.put(130, "X400_S_DSI_ARRIVAL_TIME");
        tables.put(131, "X400_N_DSI_ROUTING_ACTION");
        tables.put(132, "X400_S_DSI_ATTEMPTED_DOMAIN");
        tables.put(133, "X400_S_DSI_AA_DEF_TIME");
        tables.put(134, "X400_S_DSI_AA_CEIT");
        tables.put(135, "X400_N_DSI_AA_REDIRECTED");
        tables.put(136, "X400_N_DSI_AA_DLOPERATION");
        tables.put(137, "X400_S_CONTENT_CORRELATOR");
        tables.put(138, "X400_N_REDIRECTION_REASON");
        tables.put(139, "X400_S_REDIRECTION_TIME");
        tables.put(140, "X400_N_CONTENT_RETURNED");
        tables.put(141, "X400_S_CONTENT_CORRELATOR_OCTET_STRING");

        tables.put(300, "X400_S_IPM_IDENTIFIER");
        tables.put(301, "X400_S_SUBJECT");
        tables.put(302, "X400_S_REPLIED_TO_IDENTIFIER");
        tables.put(303, "X400_S_OBSOLETED_IPMS");
        tables.put(304, "X400_S_RELATED_IPMS");
        tables.put(305, "X400_S_EXPIRY_TIME");
        tables.put(306, "X400_S_REPLY_TIME");
        tables.put(307, "X400_N_IMPORTANCE");
        tables.put(308, "X400_N_SENSITIVITY");
        tables.put(309, "X400_N_AUTOFORWARDED");
        tables.put(310, "X400_S_SENT_IPM_IDENTIFIER");
        tables.put(320, "X400_S_AUTHORIZATION_TIME");
        tables.put(321, "X400_S_ORIGINATORS_REFERENCE");
        tables.put(322, "X400_S_PRECEDENCE_POLICY_ID");
        tables.put(350, "X400_S_CONTENT_STRING");
        tables.put(351, "X400_S_CONTENT_FILENAME");
        tables.put(352, "X400_N_DECODE_CONTENT");
        tables.put(400, "X400_N_NUM_ATTACHMENTS");
        tables.put(401, "X400_T_IA5TEXT");
        tables.put(402, "X400_T_ISO8859_1");
        tables.put(403, "X400_T_ISO8859_2");
        tables.put(404, "X400_T_BINARY");
        tables.put(405, "X400_T_MESSAGE");
        tables.put(406, "X400_T_FTBP");
        tables.put(407, "X400_T_GENERAL_TEXT");
        tables.put(460, "X400_T_TELETEX");
        tables.put(408, "X400_S_EXT_AUTH_INFO");
        tables.put(409, "X400_N_EXT_CODRESS");
        tables.put(410, "X400_N_EXT_MSG_TYPE");
        tables.put(411, "X400_N_EXT_PRIM_PREC");
        tables.put(412, "X400_N_EXT_COPY_PREC");
        tables.put(413, "X400_PRINTABLE_STRING_SEQ");
        tables.put(414, "X400_S_HANDLING_INSTRUCTIONS");
        tables.put(415, "X400_S_MESSAGE_INSTRUCTIONS");
        tables.put(416, "X400_S_ORIG_REF");
        tables.put(417, "X400_S_ORIG_PLAD");
        tables.put(418, "X400_S_DIST_CODES_SIC");
        tables.put(419, "X400_DIST_CODES_EXT");
        tables.put(420, "X400_S_DIST_CODES_EXT_OID");
        tables.put(421, "X400_S_DIST_CODES_EXT_VALUE");
        tables.put(422, "X400_OTHER_RECIP_INDICATOR");
        tables.put(423, "X400_S_PILOT_FWD_INFO");
        tables.put(424, "X400_S_ACP127_MSG_ID");
        tables.put(425, "X400_ACP127_RESPONSE");
        tables.put(426, "X400_N_ACP127_NOTI_TYPE");
        tables.put(427, "X400_S_INFO_SEC_LABEL");
        tables.put(428, "X400_N_ADATP3_PARM");
        tables.put(429, "X400_N_ADATP3_CHOICE");
        tables.put(430, "X400_S_ADATP3_DATA");
        tables.put(431, "X400_T_ADATP3");
        tables.put(432, "X400_T_CORRECTIONS");
        tables.put(433, "X400_N_CORREC_PARM");
        tables.put(434, "X400_S_CORREC_DATA");
        tables.put(435, "X400_T_ACP127DATA");
        tables.put(436, "X400_N_ACP127DATA_PARM");
        tables.put(437, "X400_S_ACP127_DATA");
        tables.put(438, "X400_T_MM");
        tables.put(439, "X400_T_FWDENC");
        tables.put(440, "X400_S_ENCRYPTED_DATA");
        tables.put(441, "X400_S_FWD_CONTENT_STRING");
        tables.put(442, "X400_T_FWD_CONTENT");
        tables.put(443, "X400_S_ORIG_OR_EXAP_TIME");
        tables.put(444, "X400_ORIG_OR_EXAP");
        tables.put(445, "X400_S_ORIG_CERT");
        tables.put(446, "X400_N_PROOF_OF_DEL_REQ");
        tables.put(447, "X400_S_EXT_MSG_IDENTIFIER");

        tables.put(500, "X400_N_IS_IPN");
        tables.put(501, "X400_S_SUBJECT_IPM");
        tables.put(502, "X400_S_CONVERSION_EITS");
        tables.put(510, "X400_N_NON_RECEIPT_REASON");
        tables.put(511, "X400_N_DISCARD_REASON");
        tables.put(512, "X400_S_AUTOFORWARD_COMMENT");
        tables.put(520, "X400_S_RECEIPT_TIME");
        tables.put(521, "X400_N_ACK_MODE");
        tables.put(522, "X400_S_SUPP_RECEIPT_INFO");

        tables.put(200, "X400_N_ORIGINAL_RECIPIENT_NUMBER");
        tables.put(201, "X400_N_RESPONSIBILITY");
        tables.put(202, "X400_N_MTA_REPORT_REQUEST");
        tables.put(203, "X400_N_REPORT_REQUEST");
        tables.put(204, "X400_S_ORIGINATOR_REQUESTED_ALTERNATE_RECIPIENT");
        tables.put(205, "X400_S_ORIGINATOR_REQUESTED_ALTERNATE_RECIPIENT_DN");
        tables.put(221, "X400_S_FREE_FORM_NAME");
        tables.put(222, "X400_S_TELEPHONE_NUMBER");
        tables.put(223, "X400_N_NOTIFICATION_REQUEST");
        tables.put(224, "X400_N_REPLY_REQUESTED");
        tables.put(225, "X400_N_PRECEDENCE");
        tables.put(226, "X400_S_ORIGINAL_RECIPIENT_ADDRESS");
        tables.put(227, "X400_S_CONVERTED_ENCODED_INFORMATION_TYPES");
        tables.put(200, "X400_N_ORIGINAL_RECIPIENT_NUMBER");
        tables.put(201, "X400_N_RESPONSIBILITY");
        tables.put(202, "X400_N_MTA_REPORT_REQUEST");
        tables.put(203, "X400_N_REPORT_REQUEST");
        tables.put(204, "X400_S_ORIGINATOR_REQUESTED_ALTERNATE_RECIPIENT");
        tables.put(205, "X400_S_ORIGINATOR_REQUESTED_ALTERNATE_RECIPIENT_DN");
        tables.put(221, "X400_S_FREE_FORM_NAME");
        tables.put(222, "X400_S_TELEPHONE_NUMBER");
        tables.put(223, "X400_N_NOTIFICATION_REQUEST");
        tables.put(224, "X400_N_REPLY_REQUESTED");
        tables.put(225, "X400_N_PRECEDENCE");
        tables.put(226, "X400_S_ORIGINAL_RECIPIENT_ADDRESS");
        tables.put(227, "X400_S_CONVERTED_ENCODED_INFORMATION_TYPES");
        tables.put(131072, "X400_RECIP_ENVELOPE");
        tables.put(196608, "X400_RECIP_INVALID");
        tables.put(196609, "X400_RECIP_STANDARD");
        
        tables.put(1001, "ATS_S_PRIORITY_INDICATOR");
        tables.put(1002, "ATS_S_FILING_TIME");
        tables.put(1003, "ATS_S_OPTIONAL_HEADING_INFO");
        tables.put(1004, "ATS_S_TEXT");
        tables.put(1005, "ATS_N_EXTENDED");
        tables.put(1006, "ATS_EOH_MODE");

    }
    
    public String get(int key) {
        String value = tables.get(key);
        if (value != null) {
            return value;
        }
        
        return key + "";
    }
    
    
    public static AttributeDic getInstance() {
        if (instance == null) {
            instance = new AttributeDic();
        }
        
        return instance;
    }
    
}
