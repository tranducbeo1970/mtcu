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
public class CT607 {

    public static void main(String[] args) throws X400APIException {

        DOMConfigurator.configure("config/log.xml");
        String acc1 = "message/accounts/AAAAMHAA.account.xml";

        String msg01 = "message/CT607/CT607M01.xml";
        String msg02 = "message/CT607/CT607M02.xml";
        Sending.send(acc1, msg01);
        Sending.send(acc1, msg02);

    }
}
