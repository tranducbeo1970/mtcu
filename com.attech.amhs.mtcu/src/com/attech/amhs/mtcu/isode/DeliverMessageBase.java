/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.isode;

import com.attech.amhs.mtcu.database.entity.MessageConversionLog;
import com.attech.amhs.mtcu.database.enums.Priority;
import com.isode.x400api.Recip;
import com.isode.x400api.X400_att;
import com.isode.x400mtapi.MTMessage;
import com.isode.x400mtapi.X400mt;

/**
 *
 * @author ANDH
 */
public abstract class DeliverMessageBase {
    
    protected String submissionTime;

    public DeliverMessageBase() {
    }

    public abstract MessageConversionLog createMessageConversionLog();

    public abstract void build(MTMessage mtmessage);

    protected void addRecipient(MTMessage mtMessage, int type, int rno, Recipient recipient) {

        final Recip recip = new Recip();

        final int status = com.isode.x400mtapi.X400mt.x400_mt_recipnew(mtMessage, type, recip);
        if (status != X400_att.X400_E_NOERROR) {
            return;
        }

        set(recip, X400_att.X400_N_ORIGINAL_RECIPIENT_NUMBER, rno);
        set(recip, X400_att.X400_S_OR_ADDRESS, recipient.getAddress());
        set(recip, X400_att.X400_N_MTA_REPORT_REQUEST, recipient.getMtaReportRequest());
        set(recip, X400_att.X400_N_REPORT_REQUEST, recipient.getReportRequest());
        set(recip, X400_att.X400_N_NOTIFICATION_REQUEST, recipient.getReceiptNotification());
        // set(recip, X400_att.X400_N_REPLY_REQUESTED, messageCfg.getReplyRequest());
        set(recip, X400_att.X400_S_FREE_FORM_NAME, recipient.getAddress());

    }

    
    protected void set(MTMessage mt, int attribute, String value) {
        if (value == null || value.isEmpty()) {
            X400mt.x400_mt_msgaddstrparam(mt, attribute, null, -1);
            return;
        }
        X400mt.x400_mt_msgaddstrparam(mt, attribute, value, value.length());
    }

    protected void set(MTMessage mt, int attribute, Integer value) {
        if(value == null) {
            return;
        }
        set(mt, attribute, value, 0);
    }

    protected void set(MTMessage mt, int attribute, Integer value, Integer mode) {
        if (value == null) {
            return;
        }

        X400mt.x400_mt_msgaddintparam(mt, attribute, value);

    }

    protected void set(Recip recipObj, int attribute, String value, int length, Integer mode) {
        if (value == null || value.isEmpty()) {
            return;
        }
        com.isode.x400mtapi.X400mt.x400_mt_recipaddstrparam(recipObj, attribute, value, length);

    }

    protected void set(Recip recipObj, int attribute, String value) {
        set(recipObj, attribute, value, value.length(), 0);
    }

    protected void set(Recip recipObj, int attribute, Integer value, Integer mode) {
        if (value == null) {
            return;
        }

        com.isode.x400mtapi.X400mt.x400_mt_recipaddintparam(recipObj, attribute, value);

    }

    protected void set(Recip recipObj, int attribute, Integer value) {
        set(recipObj, attribute, value, 0);
    }

    protected void set(Recip recipObj, int attribute, String value, int length) {
        set(recipObj, attribute, value, length, 0);
    }
    
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
}
