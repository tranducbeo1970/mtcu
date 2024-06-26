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
public class CT422 {
    public static void main(String [] args) throws X400APIException {
        String account = "message/accounts/IUTAMHAA.account.xml";
        String message = "message/CT422/CT422M01.xml";
        
        Sending.send(account, message);

        message = "message/CT422/CT422M02.xml";
        Sending.send(account, message);

        message = "message/CT422/CT422M03.xml";
        Sending.send(account, message);

        message = "message/CT422/CT422M04.xml";
        Sending.send(account, message);
    }
}
