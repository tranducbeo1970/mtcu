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
public class CT303 {
    public static void main(String [] args) throws X400APIException {
        String account = "message/accounts/AAAAMHAA.account.xml";

        String message = "message/CT303/CT303M01.probe.xml";
        Sending.sendProbe(account, message);

        message = "message/CT303/CT303M02.probe.xml";
        Sending.sendProbe(account, message);

        
        
    }
}
