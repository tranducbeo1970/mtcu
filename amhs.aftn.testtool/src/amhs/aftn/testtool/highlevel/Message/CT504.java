/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import amhs.database.CommonUtils;
import com.isode.x400.highlevel.X400APIException;

/**
 *
 * @author andh
 */
public class CT504 {
    public static void main(String[] args) throws X400APIException {
        
        CommonUtils.configure("config/database.xml");
        Sending.sendAFTN("message/CT504/CT504M01.xml");
    }
}
