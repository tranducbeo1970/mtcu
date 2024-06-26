/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import com.isode.x400.highlevel.X400APIException;

/**
 *
 * @author andh
 */
public class CT421 {
    public static void main(String [] args) throws X400APIException {
        String account = "message/accounts/IUTAMHAA.account.xml";
        String message = "message/CT421/CT421M01.probe.xml";
        
        Sending.sendProbe(account, message);

        message = "message/CT421/CT421M02.probe.xml";
        Sending.sendProbe(account, message);

        message = "message/CT421/CT421M03.probe.xml";
        Sending.sendProbe(account, message);

        message = "message/CT421/CT421M04.probe.xml";
        Sending.sendProbe(account, message);

        message = "message/CT421/CT421M05.probe.xml";
        Sending.sendProbe(account, message);

        message = "message/CT421/CT421M06.probe.xml";
        Sending.sendProbe(account, message);

        message = "message/CT421/CT421M07.probe.xml";
        Sending.sendProbe(account, message);
    }
}
