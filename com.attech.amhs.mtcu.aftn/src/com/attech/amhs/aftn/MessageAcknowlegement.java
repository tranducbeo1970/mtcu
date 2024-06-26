/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.amhs.aftn;

/**
 *
 * @author andh
 */
public class MessageAcknowlegement extends Message{
    
    private String referenceFilingTime;
    private String referenceOriginator;
    
    public MessageAcknowlegement() {
    }
    
    public MessageAcknowlegement(Message message) {
        super.setMessage(message.getMessage());
        super.setHeaderLine(message.getHeaderLine());
        super.setAddressLine(message.getAddressLine());
        super.setOriginLine(message.getOriginLine());
        super.setText(message.getText());
        // super.setEnd(message.getEnd());
        
        super.setMessageId(message.getMessageId());
        // super.setTransChar(message.getTransChar());
        // super.setChanChar(message.getChanChar());
        // super.setReceiChar(message.getReceiChar());
        // super.setChanSequence(message.getChanSequence());
        // super.setAutoCorrectChanSeq(message.isAutoCorrectChanSeq());
        super.setServiceIndicator(message.getServiceIndicator());
        
        // Address
        super.setPriority(message.getPriority());
        super.setAddresses(message.getAddresses());

        // Origin
        super.setFilingTime(message.getFilingTime());
        super.setOriginator(message.getOriginator());
        super.setAdditionInfo(message.getAdditionInfo());

        // Text
        super.setTextLines(message.getTextLines());

        // End content
        super.setMessageType(Type.ACKNOWLEDGE);
    }

    /**
     * @return the referenceFilingTime
     */
    public String getReferenceFilingTime() {
        return referenceFilingTime;
    }

    /**
     * @param referenceFilingTime the referenceFilingTime to set
     */
    public void setReferenceFilingTime(String referenceFilingTime) {
        this.referenceFilingTime = referenceFilingTime;
    }

    /**
     * @return the referenceOriginator
     */
    public String getReferenceOriginator() {
        return referenceOriginator;
    }

    /**
     * @param referenceOriginator the referenceOriginator to set
     */
    public void setReferenceOriginator(String referenceOriginator) {
        this.referenceOriginator = referenceOriginator;
    }
}
