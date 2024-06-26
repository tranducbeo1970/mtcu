/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import amhs.aftn.Message;
import amhs.aftn.testtool.highlevel.XmlSerializer;
import amhs.database.CommonUtils;
import com.isode.x400.highlevel.X400APIException;

/**
 *
 * @author andh
 */
public class CT601 {
    public static void main(String[] args) throws X400APIException {
        CommonUtils.configure("config/database.xml");
        
        AFTNUtil.clean();
        
        String acc1 = "message/accounts/AAAAMHAA.account.xml";
        String acc2 = "message/accounts/ABAAMHAA.account.xml";
        String acc3 = "message/accounts/ABBAMHAA.account.xml";
        String acc4 = "message/accounts/ABCAMHAA.account.xml";
        String acc5 = "message/accounts/ACAAFTAA.account.xml";

        String msg01 = "message/CT601/CT601M01.xml";
        String msg02 = "message/CT601/CT601M02.xml";
        String msg03 = "message/CT601/CT601M03.xml";
        String msg04 = "message/CT601/CT601M04.xml";
        String msg05 = "message/CT601/CT601M05.xml";
        String msg06 = "message/CT601/CT601M06.xml";
        String msg07 = "message/CT601/CT601M07.xml";
        String msg08 = "message/CT601/CT601M08.xml";
        String msg09 = "message/CT601/CT601M09.xml";
        String msg10 = "message/CT601/CT601M10.xml";
        
//        Sending.send(acc1, msg01);
//        Message aftn = AFTNUtil.getMessage(20);
//        XmlSerializer.serialize(Out.output + "\\CT601M01.aftn.xml", aftn);
//
//        Sending.send(acc2, msg02);
//        aftn = AFTNUtil.getMessage(20);
//        XmlSerializer.serialize(Out.output + "\\CT601M02.aftn.xml", aftn);
//
//        Sending.send(acc3, msg03);
//        aftn = AFTNUtil.getMessage(20);
//        XmlSerializer.serialize(Out.output + "\\CT601M03.aftn.xml", aftn);
//
//        Sending.send(acc4, msg04);
//        aftn = AFTNUtil.getMessage(20);
//        XmlSerializer.serialize(Out.output + "\\CT601M04.aftn.xml", aftn);

        Sending.send(acc5, msg05);
        
//        aftn = AFTNUtil.getMessage(20);
//        XmlSerializer.serialize(Out.output + "\\CT601M05.aftn.xml", aftn);
//
//        Sending.send(acc1, msg06);
//        aftn = AFTNUtil.getMessage(20);
//        XmlSerializer.serialize(Out.output + "\\CT601M06.aftn.xml", aftn);
//
//        Sending.send(acc2, msg07);
//        aftn = AFTNUtil.getMessage(20);
//        XmlSerializer.serialize(Out.output + "\\CT601M07.aftn.xml", aftn);
//
//        Sending.send(acc3, msg08);
//        aftn = AFTNUtil.getMessage(20);
//        XmlSerializer.serialize(Out.output + "\\CT601M08.aftn.xml", aftn);
//
//        Sending.send(acc4, msg09);
//        aftn = AFTNUtil.getMessage(20);
//        XmlSerializer.serialize(Out.output + "\\CT601M09aftn.xml", aftn);
//
//        Sending.send(acc5, msg10);
//        aftn = AFTNUtil.getMessage(20);
//        XmlSerializer.serialize(Out.output + "\\CT601M10.aftn.xml", aftn);
       
    }
}
