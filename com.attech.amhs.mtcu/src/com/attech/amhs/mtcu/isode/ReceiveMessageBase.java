/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.isode;

import com.attech.amhs.mtcu.database.entity.MessageConversionLog;
import com.attech.amhs.mtcu.database.enums.Priority;
import com.isode.x400.highlevel.X400APIException;
import com.isode.x400api.BodyPart;
import com.isode.x400api.DLExpHist;
import com.isode.x400api.Recip;
import com.isode.x400api.X400_att;
import com.isode.x400mtapi.MTMessage;

/**
 *
 * @author ANDH
 */
public abstract class ReceiveMessageBase {

    /*
    protected String getStr(MTMessage message, int att, Integer mode) {
        final StringBuffer retValue = new StringBuffer();
        final int status = com.isode.x400mtapi.X400mt.x400_mt_msggetstrparam(message, att, retValue);

        if (status != X400_att.X400_E_NOERROR) {
            return null;
        }

        return retValue.toString();
    }
    */

    protected String getStr(MTMessage message, int att) {
        // return getStr(message, att, 0);
        final StringBuffer retValue = new StringBuffer();
        final int status = com.isode.x400mtapi.X400mt.x400_mt_msggetstrparam(message, att, retValue);
        if (status != X400_att.X400_E_NOERROR) return null;
        return retValue.toString();
    }

    /*
    protected Integer getInt(MTMessage message, int att, Integer mode) {

        final int status = com.isode.x400mtapi.X400mt.x400_mt_msggetintparam(message, att);

        if (status == X400_att.X400_E_NO_VALUE) {
            return null;
        }

        if (status != X400_att.X400_E_NOERROR) {
            return null;
        }

        final int value = message.GetIntValue();
        return value;
    }
    */

    protected Integer getInt(MTMessage message, int att) {
        // return getInt(message, att, 0);
        final int status = com.isode.x400mtapi.X400mt.x400_mt_msggetintparam(message, att);
        if (status == X400_att.X400_E_NO_VALUE) return null;
        if (status != X400_att.X400_E_NOERROR) return null;
        return message.GetIntValue();
    }

    /*
    protected String getStr(Recip recipObj, int att, Integer mode) {

        final StringBuffer value = new StringBuffer();
        final int status = com.isode.x400mtapi.X400mt.x400_mt_recipgetstrparam(recipObj, att, value);

        if (status != X400_att.X400_E_NOERROR) {
            return null;
        }

        return value.toString();
    }
    */

    protected String getStr(Recip recipObj, int att) {
        // return getStr(recipObj, att, 0);
        final StringBuffer value = new StringBuffer();
        final int status = com.isode.x400mtapi.X400mt.x400_mt_recipgetstrparam(recipObj, att, value);
        if (status != X400_att.X400_E_NOERROR) return null;
        return value.toString();
    }

    /*
    protected Integer getInt(Recip recipObj, int att, Integer mode) {
        final int status = com.isode.x400mtapi.X400mt.x400_mt_recipgetintparam(recipObj, att);

        if (status != X400_att.X400_E_NOERROR) {
            return null;
        }

        final int int_value = recipObj.GetIntValue();
        return int_value;
    }
    */

    protected Integer getInt(Recip recipObj, int att) {
        // return getInt(recipObj, att, 0);
        final int status = com.isode.x400mtapi.X400mt.x400_mt_recipgetintparam(recipObj, att);
        if (status != X400_att.X400_E_NOERROR) return null;
        return recipObj.GetIntValue();
    }

    protected String getStr(BodyPart bodypart, int att) {
        final StringBuffer value = new StringBuffer();
        final byte[] bytes = new byte[32000];
        final int status = com.isode.x400api.X400.x400_bodypartgetstrparam(bodypart, att, value, bytes);
        if (status != X400_att.X400_E_NOERROR) return null; 
        return value.toString();
    }

    /*
    protected String getStr(BodyPart bodypart, int att, int mode) {

        final StringBuffer value = new StringBuffer();
        final byte[] bytes = new byte[32000];

        final int status = com.isode.x400api.X400.x400_bodypartgetstrparam(bodypart, att, value, bytes);

        if (status != X400_att.X400_E_NOERROR) {
            return null;
        }

        return value.toString();
    }
    */

    protected Integer getInt(BodyPart bodypart, int att) {
        final int status = com.isode.x400api.X400.x400_bodypartgetintparam(bodypart, att);
        if (status != X400_att.X400_E_NOERROR) { return null; }
        return bodypart.GetIntValue();
    }

    /*
    protected Integer getInt(BodyPart bodypart, int att, int mode) {
        final int status = com.isode.x400api.X400.x400_bodypartgetintparam(bodypart, att);

        if (status != X400_att.X400_E_NOERROR) {
            return null;
        }

        final int value = bodypart.GetIntValue();
        return value;
    }
    */

    protected String getStr(DLExpHist dlHist, int att) {
        // return getStr(dlHist, att, 0);
        final StringBuffer buffer = new StringBuffer();
        final int status = com.isode.x400api.X400.x400_DLgetstrparam(dlHist, att, buffer);
        if (status != X400_att.X400_E_NOERROR) return null;
        return buffer.toString();
    }

    /*
    protected String getStr(DLExpHist dlHist, int att, int mode) {

        final StringBuffer buffer = new StringBuffer();
        final int status = com.isode.x400api.X400.x400_DLgetstrparam(dlHist, att, buffer);

        if (status != X400_att.X400_E_NOERROR) {
            return null;
        }

        return buffer.toString();
    }
    */

    protected Priority parseAmhsPriority(Integer value) {
	if (value == null || value < 0 || value > 2) {
	    return null;
	}
	
	switch (value) {
	    case 0:
		return Priority.NORMAL;
	    case 1:
		return Priority.NONE_URGENT;
	    case 2: 
		return Priority.URGENT;
	}
	return null;
    }
    
    protected abstract void parse(MTMessage mtMessage);

    public abstract MessageConversionLog createMessageConversionLog();

    public abstract DeliverReport createNonDeliverReport(int ndrReason, int ndrDianogsticReason, String suplementInfo) throws X400APIException;

    public abstract DeliverReport createDeliverReport() throws X400APIException;

}
