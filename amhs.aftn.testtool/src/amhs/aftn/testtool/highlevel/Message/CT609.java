/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amhs.aftn.testtool.highlevel.Message;

import com.isode.x400.highlevel.X400APIException;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author andh
 */
public class CT609 {

    public static void main(String[] args) throws X400APIException {

        DOMConfigurator.configure("config/log.xml");
        String acc1 = "message/accounts/ABCBMHAA.account.xml";
        String acc2 = "message/accounts/ABCBMHAM.account.xml";

        String msg01 = "message/CT609/CT609M01.xml";
        String msg02 = "message/CT609/CT609M02.xml";
        Sending.send(acc1, msg01);
        Sending.send(acc2, msg02);

    }
}
