/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import com.isode.x400api.MSMessage;
import com.isode.x400api.Recip;
import com.isode.x400api.X400_att;

/**
 *
 * @author andh
 */
public class Common {
    
    public static Integer getInt(Recip recip, int paramtype) {
        int status = com.isode.x400api.X400ms.x400_ms_recipgetintparam(recip, paramtype);
        if (status != X400_att.X400_E_NOERROR) {
            return null;
        }
        return recip.GetIntValue();
    }
    
    public static String getStr(Recip recip, int attribute) {
        StringBuffer ret_value = new StringBuffer();
        int status = com.isode.x400api.X400ms.x400_ms_recipgetstrparam(recip, attribute, ret_value);
        if (status != X400_att.X400_E_NOERROR) {
            return null;
        }
        return ret_value.toString();
    }
    
    public static Integer getInt(MSMessage message, int attribute) {
        // com.isode.x400api.X400.x400_msggetintparam(message, attribute);
        int status = com.isode.x400api.X400ms.x400_ms_msggetintparam(message, attribute);
        if (status != X400_att.X400_E_NOERROR) {
            return null;
        }
        return message.GetIntValue();
    }

    public static String getStr(MSMessage message, int attribute) {
        StringBuffer ret_value = new StringBuffer();
        int status = com.isode.x400api.X400ms.x400_ms_msggetstrparam(message, attribute, ret_value);
        if (status == X400_att.X400_E_NO_VALUE || status != X400_att.X400_E_NOERROR) {
            return null;
        }
        return ret_value.toString();
    }
}
