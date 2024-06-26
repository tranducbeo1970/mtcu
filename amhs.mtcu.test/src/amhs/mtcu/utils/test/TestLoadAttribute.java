/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amhs.mtcu.utils.test;

import amhs.mtcu.config.Attributes;
import amhs.mtcu.utils.XmlSerializer;

/**
 *
 * @author andh
 */
public class TestLoadAttribute {

    public static void main(String[] args) {
        
        load();
    }
    
    private static void writeDefault() {
        Attributes attribute = new Attributes();
        // Envelope
        attribute.add("X400_S_MESSAGE_IDENTIFIER", 100);
        attribute.add("X400_N_CONTENT_TYPE", 101);
        attribute.add("X400_N_CONTENT_LENGTH", 102);
        attribute.add("X400_S_CONTENT_IDENTIFIER", 103);
        attribute.add("X400_S_ORIGINAL_ENCODED_INFORMATION_TYPES", 104);
        attribute.add("X400_N_PRIORITY", 105);
        attribute.add("X400_N_DISCLOSURE", 106);
        attribute.add("X400_N_IMPLICIT_CONVERSION_PROHIBITED", 107);
        attribute.add("X400_N_ALTERNATE_RECIPIENT_ALLOWED", 108);
        attribute.add("X400_N_CONTENT_RETURN_REQUEST", 109);
        attribute.add("X400_S_MESSAGE_SUBMISSION_TIME", 110);
        attribute.add("X400_S_MESSAGE_DELIVERY_TIME", 111);
        attribute.add("X400_S_EXTERNAL_CONTENT_TYPE", 112);
        attribute.add("X400_N_RECIPIENT_REASSIGNMENT_PROHIBITED", 120);
        attribute.add("X400_N_DL_EXPANSION_PROHIBITED", 121);
        attribute.add("X400_N_CONVERSION_WITH_LOSS_PROHIBITED", 122);
        attribute.add("X400_S_LATEST_DELIVERY_TIME", 123);
        attribute.add("X400_S_ORIGINATOR_RETURN_ADDRESS", 124);
        attribute.add("X400_S_OBJECTTYPE", 125);
        attribute.add("X400_N_MMTS_PRIORITY_QUALIFIER", 126);
        attribute.add("X400_S_DEFERRED_DELIVERY_TIME", 127);
        attribute.add("X400_S_DLEXP_TIME", 128);
        attribute.add("X400_S_GLOBAL_DOMAIN_ID", 129);
        attribute.add("X400_S_DSI_ARRIVAL_TIME", 130);
        attribute.add("X400_N_DSI_ROUTING_ACTION", 131);
        attribute.add("X400_S_DSI_ATTEMPTED_DOMAIN", 132);
        attribute.add("X400_S_DSI_AA_DEF_TIME", 133);
        attribute.add("X400_S_DSI_AA_CEIT", 134);
        attribute.add("X400_N_DSI_AA_REDIRECTED", 135);
        attribute.add("X400_N_DSI_AA_DLOPERATION", 136);
        attribute.add("X400_S_CONTENT_CORRELATOR", 137);
        attribute.add("X400_N_REDIRECTION_REASON", 138);
        attribute.add("X400_S_REDIRECTION_TIME", 139);
        attribute.add("X400_N_CONTENT_RETURNED", 140);
        attribute.add("X400_S_CONTENT_CORRELATOR_IA5_STRING", 137);
        attribute.add("X400_S_CONTENT_CORRELATOR_OCTET_STRING", 141);

        // Ipm
        attribute.add("X400_S_IPM_IDENTIFIER", 300);
        attribute.add("X400_S_SUBJECT", 301);
        attribute.add("X400_S_REPLIED_TO_IDENTIFIER", 302);
        attribute.add("X400_S_OBSOLETED_IPMS", 303);
        attribute.add("X400_S_RELATED_IPMS", 304);
        attribute.add("X400_S_EXPIRY_TIME", 305);
        attribute.add("X400_S_REPLY_TIME", 306);
        attribute.add("X400_N_IMPORTANCE", 307);
        attribute.add("X400_N_SENSITIVITY", 308);
        attribute.add("X400_N_AUTOFORWARDED", 309);
        attribute.add("X400_S_SENT_IPM_IDENTIFIER", 310);
        attribute.add("X400_S_AUTHORIZATION_TIME", 320);
        attribute.add("X400_S_ORIGINATORS_REFERENCE", 321);
        attribute.add("X400_S_PRECEDENCE_POLICY_ID", 322);
        attribute.add("X400_S_CONTENT_STRING", 350);
        attribute.add("X400_S_CONTENT_FILENAME", 351);
        attribute.add("X400_N_DECODE_CONTENT", 352);
        attribute.add("X400_N_NUM_ATTACHMENTS", 400);
        attribute.add("X400_T_IA5TEXT", 401);
        attribute.add("X400_T_ISO8859_1", 402);
        attribute.add("X400_T_ISO8859_2", 403);
        attribute.add("X400_T_BINARY", 404);
        attribute.add("X400_T_MESSAGE", 405);
        attribute.add("X400_T_FTBP", 406);
        attribute.add("X400_T_GENERAL_TEXT", 407);
        attribute.add("X400_T_TELETEX", 460);
        attribute.add("X400_S_EXT_AUTH_INFO", 408);
        attribute.add("X400_N_EXT_CODRESS", 409);
        attribute.add("X400_N_EXT_MSG_TYPE", 410);
        attribute.add("X400_N_EXT_PRIM_PREC", 411);
        attribute.add("X400_N_EXT_COPY_PREC", 412);
        attribute.add("X400_PRINTABLE_STRING_SEQ", 413);
        attribute.add("X400_S_HANDLING_INSTRUCTIONS", 414);
        attribute.add("X400_S_MESSAGE_INSTRUCTIONS", 415);
        attribute.add("X400_S_ORIG_REF", 416);
        attribute.add("X400_S_ORIG_PLAD", 417);
        attribute.add("X400_S_DIST_CODES_SIC", 418);
        attribute.add("X400_DIST_CODES_EXT", 419);
        attribute.add("X400_S_DIST_CODES_EXT_OID", 420);
        attribute.add("X400_S_DIST_CODES_EXT_VALUE", 421);
        attribute.add("X400_OTHER_RECIP_INDICATOR", 422);
        attribute.add("X400_S_PILOT_FWD_INFO", 423);
        attribute.add("X400_S_ACP127_MSG_ID", 424);
        attribute.add("X400_ACP127_RESPONSE", 425);
        attribute.add("X400_N_ACP127_NOTI_TYPE", 426);
        attribute.add("X400_S_INFO_SEC_LABEL", 427);
        attribute.add("X400_N_ADATP3_PARM", 428);
        attribute.add("X400_N_ADATP3_CHOICE", 429);
        attribute.add("X400_S_ADATP3_DATA", 430);
        attribute.add("X400_T_ADATP3", 431);
        attribute.add("X400_T_CORRECTIONS", 432);
        attribute.add("X400_N_CORREC_PARM", 433);
        attribute.add("X400_S_CORREC_DATA", 434);
        attribute.add("X400_T_ACP127DATA", 435);
        attribute.add("X400_N_ACP127DATA_PARM", 436);
        attribute.add("X400_S_ACP127_DATA", 437);
        attribute.add("X400_T_MM", 438);
        attribute.add("X400_T_FWDENC", 439);
        attribute.add("X400_S_ENCRYPTED_DATA", 440);
        attribute.add("X400_S_FWD_CONTENT_STRING", 441);
        attribute.add("X400_T_FWD_CONTENT", 442);
        attribute.add("X400_S_ORIG_OR_EXAP_TIME", 443);
        attribute.add("X400_ORIG_OR_EXAP", 444);
        attribute.add("X400_S_ORIG_CERT", 445);
        attribute.add("X400_N_PROOF_OF_DEL_REQ", 446);
        attribute.add("X400_S_EXT_MSG_IDENTIFIER", 447);

        attribute.add("X400_N_ORIGINAL_RECIPIENT_NUMBER", 200);
        attribute.add("X400_N_RESPONSIBILITY", 201);
        attribute.add("X400_N_MTA_REPORT_REQUEST", 202);
        attribute.add("X400_N_REPORT_REQUEST", 203);
        attribute.add("X400_S_ORIGINATOR_REQUESTED_ALTERNATE_RECIPIENT", 204);
        attribute.add("X400_S_ORIGINATOR_REQUESTED_ALTERNATE_RECIPIENT_DN", 205);
        attribute.add("X400_S_FREE_FORM_NAME", 221);
        attribute.add("X400_S_TELEPHONE_NUMBER", 222);
        attribute.add("X400_N_NOTIFICATION_REQUEST", 223);
        attribute.add("X400_N_REPLY_REQUESTED", 224);
        attribute.add("X400_N_PRECEDENCE", 225);
        attribute.add("X400_S_ORIGINAL_RECIPIENT_ADDRESS", 226);
        attribute.add("X400_S_CONVERTED_ENCODED_INFORMATION_TYPES", 227);
        attribute.add("ATS_S_PRIORITY_INDICATOR", 1001);
        attribute.add("ATS_S_FILING_TIME", 1002);
        attribute.add("ATS_S_OPTIONAL_HEADING_INFO", 1003);
        attribute.add("ATS_S_TEXT", 1004);
        attribute.add("ATS_N_EXTENDED", 1005);
        attribute.add("ATS_EOH_MODE", 1006);
        attribute.add("X400_S_SUBJECT_IDENTIFIER", 600);
        attribute.add("X400_S_SUPPLEMENTARY_INFO", 610);
        attribute.add("X400_N_TYPE_OF_USER", 611);
        attribute.add("X400_S_ARRIVAL_TIME", 612);
        attribute.add("X400_N_NON_DELIVERY_REASON", 613);
        attribute.add("X400_N_NON_DELIVERY_DIAGNOSTIC", 614);
        
        XmlSerializer.serialize("D:\\attribute.xml", attribute);
        System.out.println("OK");
    }
    
    private static void load() {
        Attributes attributes = (Attributes) XmlSerializer.deserialize("config/attribute.xml", Attributes.class);
        System.out.println("Size: " + attributes.size());
    }
}
