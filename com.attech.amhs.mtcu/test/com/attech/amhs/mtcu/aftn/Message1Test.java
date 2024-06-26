/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.aftn;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author andh
 */
public class Message1Test {

    public Message1Test() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of parsing method, of class ITA2Message.
     */
//    @Test
//    public void testParsing() {
//        testCase01();
//        testCase02();
//        testCase03();
////        
////        // Unknow addressee indicator
//        testCase04();
//        testCase05();
//        testCase06();
//        testCase07();
//        testCase08();
//    }

    @Test
    public void testCase01() {
        StringBuilder builder = new StringBuilder();
        builder.append("ZCZC H&A0005 070251     \r\n");
        builder.append("GG VVNBAPPN\r\n");
        builder.append("070251 VVNBYFYX\r\n");
        builder.append("TEST NHAN TOT TRA LOI\r\n");
        builder.append("\r\n");
        builder.append("\r\n");
        builder.append("\r\n");
        builder.append("\r\n");
        builder.append("\r\n");
        builder.append("NNNN");
        
        Message message = new Message(builder.toString());
        
        
        assertEquals(Type.GENERAL, message.getMessageType());
        assertEquals("H&A0005", message.getChannelId());
        // assertEquals('H', message.getTransChar());
        // assertEquals('&', message.getReceiChar());
        // assertEquals('A', message.getChanChar());
        // assertEquals("0005", message.getChanSequence());
        // assertEquals(false, message.isAutoCorrectChanSeq());
        assertEquals("070251", message.getServiceIndicator());
        assertEquals("GG", message.getPriority());

        List<String> addresses = new ArrayList<>();
        addresses.add("VVNBAPPN");
        assertEquals(addresses, message.getAddresses());
        assertEquals("070251", message.getFilingTime());
        assertEquals("VVNBYFYX", message.getOriginator());
        assertEquals("", message.getAdditionInfo());

        List<String> text = new ArrayList<>();
        text.add("TEST NHAN TOT TRA LOI");
        text.add("");
        text.add("");
        text.add("");
        text.add("");
        text.add("");
        assertEquals(text, message.getContents());
    }

    @Test
    public void testCase02() {
        StringBuilder builder = new StringBuilder();
        builder.append("ZCZC H&A0005 070251     \r\n");
        builder.append("SS VVNBAPPN\r\n");
        builder.append("070251 VVNBYFYX\r\n");
        builder.append("R 070245 VVNBAPPN\r\n");
        builder.append("\r\n");
        builder.append("\r\n");
        builder.append("\r\n");
        builder.append("\r\n");
        builder.append("\r\n");
        builder.append("NNNN");

        Message message = new Message(builder.toString());
        
        assertEquals(Type.ACKNOWLEDGE, message.getMessageType());
        assertEquals("H&A0005", message.getChannelId());
        assertEquals("070251", message.getServiceIndicator());
        assertEquals("SS", message.getPriority());

        List<String> addresses = new ArrayList<>();
        addresses.add("VVNBAPPN");
        assertEquals(addresses, message.getAddresses());
        assertEquals("070251", message.getFilingTime());
        assertEquals("VVNBYFYX", message.getOriginator());
        assertEquals("", message.getAdditionInfo());

        assertEquals("070245", message.getAckInfo().getRefTime());
        assertEquals("VVNBAPPN", message.getAckInfo().getRefOriginator());

        List<String> text = new ArrayList<>();
        text.add("R 070245 VVNBAPPN");
        text.add("");
        text.add("");
        text.add("");
        text.add("");
        text.add("");
        assertEquals(text, message.getContents());
    }

    @Test
    public void testCase03() {
        StringBuilder builder = new StringBuilder();
        builder.append("ZCZC H&A0005\r\n");
        builder.append("SS VVNBAPPN\r\n");
        builder.append("070251 VVNBYFYX\r\n");
        builder.append("R 070245 VVNBAPPN\r\n");
        builder.append("\r\n");
        builder.append("\r\n");
        builder.append("\r\n");
        builder.append("NNNN");

        Message message = new Message(builder.toString());
        assertEquals(Type.ACKNOWLEDGE, message.getMessageType());
        assertEquals("H&A0005", message.getChannelId());
        assertEquals("SS", message.getPriority());

        List<String> addresses = new ArrayList<>();
        addresses.add("VVNBAPPN");
        assertEquals(addresses, message.getAddresses());
        assertEquals("070251", message.getFilingTime());
        assertEquals("VVNBYFYX", message.getOriginator());
        assertEquals("", message.getAdditionInfo());

        assertEquals("070245", message.getAckInfo().getRefTime());
        assertEquals("VVNBAPPN", message.getAckInfo().getRefOriginator());

        List<String> text = new ArrayList<>();
        text.add("R 070245 VVNBAPPN");
        text.add("");
        text.add("");
        text.add("");
        assertEquals(text, message.getContents());
    }

    @Test
    public void testCase04() {
        StringBuilder builder = new StringBuilder();
        builder.append("ZCZC H&A0005\r\n");
        builder.append("SS VVNBAPPN\r\n");
        builder.append("070251 VVNBYFYX\r\n");
        builder.append("SVC ADS HBA0001\r\n");
        builder.append("GG VVNBAAAA VVNBAAAB VVNBAAAC VVNBAAAD VVNBAAAE VVNBAAAF VVNBAAAG\r\n");
        builder.append("UNKNOWN VVNBAAAB VVNBAAAD VVNBAAAF\r\n");
        builder.append("\r\n");
        builder.append("\r\n");
        builder.append("\r\n");
        builder.append("\r\n");
        builder.append("\r\n");
        builder.append("NNNN");

        Message message = new Message(builder.toString());
        assertEquals(Type.UNKNOWN, message.getMessageType());
        assertEquals("H&A0005", message.getChannelId());
        assertEquals("SS", message.getPriority());

        List<String> addresses = new ArrayList<>();
        addresses.add("VVNBAPPN");
        assertEquals(addresses, message.getAddresses());
        assertEquals("070251", message.getFilingTime());
        assertEquals("VVNBYFYX", message.getOriginator());
        assertEquals("", message.getAdditionInfo());

        // assertEquals("070245", message.getReferenceFilingTime());
        // assertEquals("VVNBAPPN", message.getReferenceOriginator());
        List<String> text = new ArrayList<>();
        text.add("SVC ADS HBA0001");
        text.add("GG VVNBAAAA VVNBAAAB VVNBAAAC VVNBAAAD VVNBAAAE VVNBAAAF VVNBAAAG");
        text.add("UNKNOWN VVNBAAAB VVNBAAAD VVNBAAAF");
        text.add("");
        text.add("");
        text.add("");
        text.add("");
        text.add("");
        assertEquals(text, message.getContents());

        // MessageServiceAddressUnknown unknowMessage = (MessageServiceAddressUnknown) message;
        UnknownAddressInfo unknownInfo = message.getUnknownAddressInfo();
        assertEquals(false, unknownInfo.isIdentifiedByOriginGroup());
        assertEquals("HBA0001", unknownInfo.getRefChannelID());

        List<String> unknownAddresses = new ArrayList<>();
        unknownAddresses.add("VVNBAAAB");
        unknownAddresses.add("VVNBAAAD");
        unknownAddresses.add("VVNBAAAF");
        assertEquals(unknownAddresses, unknownInfo.getUnknownAddresses());

        assertEquals("GG", unknownInfo.getRefPriority());

        List<String> addrs = new ArrayList<>();
        addrs.add("VVNBAAAA");
        addrs.add("VVNBAAAB");
        addrs.add("VVNBAAAC");
        addrs.add("VVNBAAAD");
        addrs.add("VVNBAAAE");
        addrs.add("VVNBAAAF");
        addrs.add("VVNBAAAG");
        assertEquals(addrs, unknownInfo.getRefAddresses());
    }

    @Test
    public void testCase05() {
        StringBuilder builder = new StringBuilder();
        builder.append("ZCZC H&A0005\r\n");
        builder.append("SS VVNBAPPN\r\n");
        builder.append("070251 VVNBYFYX\r\n");
        builder.append("SVC ADS HBA0001\r\n");
        builder.append("GG VVNBAAAA VVNBAAAB VVNBAAAC VVNBAAAD VVNBAAAE VVNBAAAF VVNBAAAG\r\n");
        builder.append("VVNBAAAH VVNBAAAI VVNBAAAJ VVNBAAAK VVNBAAAL VVNBAAAM VVNBAAAN\r\n");
        builder.append("VVNBAAAO VVNBAAAP VVNBAAAQ VVNBAAAR VVNBAAAS VVNBAAAT VVNBAAAU\r\n");
        builder.append("UNKNOWN VVNBAAAB VVNBAAAD VVNBAAAF VVNBAAAH VVNBAAAI VVNBAAAJ VVNBAAAK\r\n");
        builder.append("VVNBAAAL VVNBAAAM VVNBAAAN VVNBAAAO VVNBAAAP VVNBAAAQ VVNBAAAR\r\n");
        builder.append("VVNBAAAS VVNBAAAT VVNBAAAU\r\n");
        builder.append("\r\n");
        builder.append("\r\n");
        builder.append("\r\n");
        builder.append("\r\n");
        builder.append("\r\n");
        builder.append("NNNN");

        Message message = new Message(builder.toString());
        // Message message = ITA2Message.parsing(builder.toString());
        assertEquals(Type.UNKNOWN, message.getMessageType());
        assertEquals("H&A0005", message.getChannelId());
        assertEquals("SS", message.getPriority());

        List<String> addresses = new ArrayList<>();
        addresses.add("VVNBAPPN");
        assertEquals(addresses, message.getAddresses());
        assertEquals("070251", message.getFilingTime());
        assertEquals("VVNBYFYX", message.getOriginator());
        assertEquals("", message.getAdditionInfo());

        // assertEquals("070245", message.getReferenceFilingTime());
        // assertEquals("VVNBAPPN", message.getReferenceOriginator());
        UnknownAddressInfo unknownInfo = message.getUnknownAddressInfo();
        List<String> text = new ArrayList<>();
        text.add("SVC ADS HBA0001");
        text.add("GG VVNBAAAA VVNBAAAB VVNBAAAC VVNBAAAD VVNBAAAE VVNBAAAF VVNBAAAG"); //\\ \r\nVVNBAAAH VVNBAAAI VVNBAAAJ VVNBAAAK VVNBAAAL VVNBAAAM VVNBAAAN\r\nVVNBAAAO VVNBAAAP VVNBAAAQ VVNBAAAR VVNBAAAS VVNBAAAT VVNBAAAU\r\n");
        text.add("VVNBAAAH VVNBAAAI VVNBAAAJ VVNBAAAK VVNBAAAL VVNBAAAM VVNBAAAN");
        text.add("VVNBAAAO VVNBAAAP VVNBAAAQ VVNBAAAR VVNBAAAS VVNBAAAT VVNBAAAU");
        text.add("UNKNOWN VVNBAAAB VVNBAAAD VVNBAAAF VVNBAAAH VVNBAAAI VVNBAAAJ VVNBAAAK"); //\r\nVVNBAAAL VVNBAAAM VVNBAAAN VVNBAAAO VVNBAAAP VVNBAAAQ VVNBAAAR\r\nVVNBAAAS VVNBAAAT VVNBAAAU");
        text.add("VVNBAAAL VVNBAAAM VVNBAAAN VVNBAAAO VVNBAAAP VVNBAAAQ VVNBAAAR"); //\nVVNBAAAS VVNBAAAT VVNBAAAU");
        text.add("VVNBAAAS VVNBAAAT VVNBAAAU");
        text.add("");
        text.add("");
        text.add("");
        text.add("");
        text.add("");
        
        for (int i = 0 ; i < text.size(); i++) {
            assertEquals(text.get(i), message.getContents().get(i));
        }
        assertEquals(text, message.getContents());

        // MessageServiceAddressUnknown unknowMessage = (MessageServiceAddressUnknown) message;
        assertEquals(false, unknownInfo.isIdentifiedByOriginGroup());
        assertEquals("HBA0001", unknownInfo.getRefChannelID());

        List<String> unknownAddresses = new ArrayList<>();
        unknownAddresses.add("VVNBAAAB");
        unknownAddresses.add("VVNBAAAD");
        unknownAddresses.add("VVNBAAAF");
        unknownAddresses.add("VVNBAAAH");
        unknownAddresses.add("VVNBAAAI");
        unknownAddresses.add("VVNBAAAJ");
        unknownAddresses.add("VVNBAAAK");
        unknownAddresses.add("VVNBAAAL");
        unknownAddresses.add("VVNBAAAM");
        unknownAddresses.add("VVNBAAAN");
        unknownAddresses.add("VVNBAAAO");
        unknownAddresses.add("VVNBAAAP");
        unknownAddresses.add("VVNBAAAQ");
        unknownAddresses.add("VVNBAAAR");
        unknownAddresses.add("VVNBAAAS");
        unknownAddresses.add("VVNBAAAT");
        unknownAddresses.add("VVNBAAAU");
        assertEquals(unknownAddresses, unknownInfo.getUnknownAddresses());

        assertEquals("GG", unknownInfo.getRefPriority());

        List<String> addrs = new ArrayList<>();
        addrs.add("VVNBAAAA");
        addrs.add("VVNBAAAB");
        addrs.add("VVNBAAAC");
        addrs.add("VVNBAAAD");
        addrs.add("VVNBAAAE");
        addrs.add("VVNBAAAF");
        addrs.add("VVNBAAAG");
        addrs.add("VVNBAAAH");
        addrs.add("VVNBAAAI");
        addrs.add("VVNBAAAJ");
        addrs.add("VVNBAAAK");
        addrs.add("VVNBAAAL");
        addrs.add("VVNBAAAM");
        addrs.add("VVNBAAAN");
        addrs.add("VVNBAAAO");
        addrs.add("VVNBAAAP");
        addrs.add("VVNBAAAQ");
        addrs.add("VVNBAAAR");
        addrs.add("VVNBAAAS");
        addrs.add("VVNBAAAT");
        addrs.add("VVNBAAAU");
        assertEquals(addrs, unknownInfo.getRefAddresses());
    }

    @Test
    public void testCase06() {
        StringBuilder builder = new StringBuilder();
        builder.append("ZCZC AAA123\r\n");
        builder.append("GG VVVVVVVV\r\n");
        builder.append("050948 VVTSAMHS\r\n");
        builder.append("SVC ADS 040945 VVTSVAIR\r\n");
        builder.append("GG VVGLVAIR\r\n");
        builder.append("UNKNOWN VVGLVAIR\r\n");
        builder.append("\r\n");
        builder.append("\r\n");
        builder.append("NNNN");

        Message message = new Message(builder.toString());
        // Message message = ITA2Message.parsing(builder.toString());
        assertEquals(Type.UNKNOWN, message.getMessageType());
        // assertEquals("ZCZC AAA123", message.getHeaderLine());
        // assertEquals("GG VVVVVVVV", message.getAddressLine());
        // assertEquals("050948 VVTSAMHS", message.getOriginLine());
        // assertEquals("NNNN", message.getEnd());

        List<String> addresses = new ArrayList<>();
        addresses.add("VVVVVVVV");
        assertEquals(addresses, message.getAddresses());
        assertEquals("050948", message.getFilingTime());
        assertEquals("VVTSAMHS", message.getOriginator());
        assertEquals("", message.getAdditionInfo());

        UnknownAddressInfo unknownInfo = message.getUnknownAddressInfo();
        // MessageServiceAddressUnknown unknowMessage = (MessageServiceAddressUnknown) message;
        assertEquals(true, unknownInfo.isIdentifiedByOriginGroup());
        assertEquals("040945", unknownInfo.getRefFilingTime());
        assertEquals("VVTSVAIR", unknownInfo.getRefOriginator());
        

        List<String> unknownAddresses = new ArrayList<>();
        unknownAddresses.add("VVGLVAIR");
        assertEquals(unknownAddresses, unknownInfo.getUnknownAddresses());

        assertEquals("GG", unknownInfo.getRefPriority());

        List<String> addrs = new ArrayList<>();
        addrs.add("VVGLVAIR");
        assertEquals(addrs, unknownInfo.getRefAddresses());
    }

    @Test
    public void testCase07() {

        StringBuilder builder = new StringBuilder();

        builder.append("ZCZC[s]ABC123[r][n]");
        builder.append("SS[s]VVTSOPTB[r][n]");
        builder.append("051028[s]VVTSAMHS[s][r][n]");
        builder.append("R[s]051028[s]VVTSOPTB[r][n]");
        builder.append("[r][n]");
        builder.append("[r][n]");
        builder.append("[r][n]");
        builder.append("NNNN");

        String tem = builder.toString().replace("[r]", "\r").replace("[n]", "\n").replace("[s]", " ");

        // Message message = ITA2Message.parsing(tem);
        // assertEquals(Type.ACKNOWLEDGE, message.getMessageType());
//        assertEquals("ZCZC AAA123", message.getHeader());
//        assertEquals("GG VVTSOPTB\r\n", message.getAddress());
//        assertEquals("051028 VVTSAMHS", message.getOrigin());
//        assertEquals("NNNN", message.getEnd());

//        List<String> addresses = new ArrayList<>();
//        addresses.add("VVVVVVVV");
//        assertEquals(addresses, message.getAddresses());
//        assertEquals("050948", message.getFilingTime());
//        assertEquals("VVTSAMHS", message.getOriginator());
//        assertEquals("", message.getAdditionInfo());
//        MessageServiceAddressUnknown unknowMessage = (MessageServiceAddressUnknown) message;
//        assertEquals(true, unknowMessage.isIdentifiedByOriginGroup());
//        
//        List<String> unknownAddresses = new ArrayList<>();
//        unknownAddresses.add("VVGLVAIR");
//        assertEquals(unknownAddresses, unknowMessage.getSvcUnknownAddresses());
//        
//        assertEquals("0", unknowMessage.getSvcPriority());
//        
//        List<String> addrs = new ArrayList<>();
//        addrs.add("VVGLVAIR");
//        assertEquals(addrs, unknowMessage.getSvcRefAddresses());
    }

    @Test
    public void testCase08() {
//        StringBuilder builder = new StringBuilder();
//
//        builder.append("ZCZC[s]ZVA0794[s]180804[r][n]");
//        builder.append("FF[s]VVTSZRZF[r][n]");
//        builder.append("180804[s]WSJCZQZQ[s]2.009542-3.VVTS063469-4.170218080418-5.CF71-[r][n]");
//        builder.append("(LAM)[r][n]");
//        builder.append("NNNN");
//        String tem = builder.toString().replace("[r]", "\r").replace("[n]", "\n").replace("[s]", " ");
//
//        Message message = ITA2Message.parsing(tem);
//        List<String> addresses = new ArrayList<>();
//        addresses.add("VVTSZRZF");
//        assertEquals(addresses, message.getAddresses());
//        assertEquals("180804", message.getFilingTime());
//        assertEquals("WSJCZQZQ", message.getOriginator());
//        assertEquals("2.009542-3.VVTS063469-4.170218080418-5.CF71-", message.getAdditionInfo());

    }
    
    @Test
    public void testCase09() {
//        StringBuilder builder = new StringBuilder();
//
//        builder.append("ZCZC[s]VGB8052[s]280859[s]RPT[s][s][s][s][s][r][n]");
//        builder.append("FF[s]VVTSZRZF[r][n]");
//        builder.append("180804[s]WSJCZQZQ[s]2.009542-3.VVTS063469-4.170218080418-5.CF71-[r][n]");
//        builder.append("(LAM)[r][n]");
//        builder.append("NNNN");
//        String tem = builder.toString().replace("[r]", "\r").replace("[n]", "\n").replace("[s]", " ");
//
//        Message message = ITA2Message.parsing(tem);
//        System.out.println("ERR:" + message.getErrorMessage());
//        assertEquals(true, message.isValid());
//        
//        List<String> addresses = new ArrayList<>();
//        addresses.add("VVTSZRZF");
//        assertEquals(addresses, message.getAddresses());
//        assertEquals("180804", message.getFilingTime());
//        assertEquals("WSJCZQZQ", message.getOriginator());

    }
    
    @Test
    public void testCase10() {
//        StringBuilder builder = new StringBuilder();
//
//        builder.append("ZCZC[s]VGB8052[s]280859[s][s]RPT[s][s][s][s][s][r][n]");
//        builder.append("FF[s]VVTSZRZF[r][n]");
//        builder.append("180804[s]WSJCZQZQ[s]2.009542-3.VVTS063469-4.170218080418-5.CF71-[r][n]");
//        builder.append("(LAM)[r][n]");
//        builder.append("NNNN");
//        String tem = builder.toString().replace("[r]", "\r").replace("[n]", "\n").replace("[s]", " ");
//
//        Message message = ITA2Message.parsing(tem);
//        System.out.println("ERR:" + message.getErrorMessage());
//        assertEquals(true, message.isValid());
//        
//        List<String> addresses = new ArrayList<>();
//        addresses.add("VVTSZRZF");
//        assertEquals(addresses, message.getAddresses());
//        assertEquals("180804", message.getFilingTime());
//        assertEquals("WSJCZQZQ", message.getOriginator());

    }
    
     @Test
    public void testCase11() {
//        StringBuilder builder = new StringBuilder();
//
//        builder.append("ZCZC[s]VGB8052[s]280859[s][s][s][s][s][s]RPT[s][s][s][s][s][r][n]");
//        builder.append("FF[s]VVTSZRZF[r][n]");
//        builder.append("180804[s]WSJCZQZQ[s]2.009542-3.VVTS063469-4.170218080418-5.CF71-[r][n]");
//        builder.append("(LAM)[r][n]");
//        builder.append("NNNN");
//        String tem = builder.toString().replace("[r]", "\r").replace("[n]", "\n").replace("[s]", " ");
//
//        Message message = ITA2Message.parsing(tem);
//        System.out.println("ERR:" + message.getErrorMessage());
//        assertEquals(true, message.isValid());
//        
//        List<String> addresses = new ArrayList<>();
//        addresses.add("VVTSZRZF");
//        assertEquals(addresses, message.getAddresses());
//        assertEquals("180804", message.getFilingTime());
//        assertEquals("WSJCZQZQ", message.getOriginator());

    }
    

}
