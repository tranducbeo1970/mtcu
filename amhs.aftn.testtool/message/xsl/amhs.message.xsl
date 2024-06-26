<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <body style="font-family:calibri;">
                <h2>AMHS Message</h2>
                <hr/>
                <table style="border:solid 1px #CCCCCC;width: 700px;">
                    <tr style="background-color: #CCCCCC;">
                        <td>Subject:</td>
                    </tr>
                    <tr style="">
                        <td>
                             <input type="text" name="subject" style="width: 690px;">
                                <xsl:attribute name="value">
                                    <xsl:value-of select="Message/Subject" />
                                </xsl:attribute>
                                 <xsl:if test="not(Message/Subject)">
                                    <xsl:attribute name="disabled">true</xsl:attribute>
                                </xsl:if>
                            </input>
                        </td>
                    </tr>
                </table><br/>
                
                <table style="border:solid 1px #CCCCCC;width: 700px;">
                    <tr style="background-color: #CCCCCC;">
                        <td>ContentID:</td>
                    </tr>
                    <tr style="">
                        <td>
                             <input type="text" name="contentID" style="width: 690px;">
                                <xsl:attribute name="value">
                                    <xsl:value-of select="Message/ContentID" />
                                </xsl:attribute>
                                 <xsl:if test="not(Message/ContentID)">
                                    <xsl:attribute name="disabled">true</xsl:attribute>
                                </xsl:if>
                            </input>
                        </td>
                    </tr>
                </table><br/>
                
                <table style="border:solid 1px #CCCCCC;width: 700px;">
                    <tr style="background-color: #CCCCCC;">
                        <td>Message ID</td>
                    </tr>
                    <tr style="">
                        <td>
                            <input type="text" name="contentID" style="width: 690px;">
                                <xsl:attribute name="value">
                                    <xsl:value-of select="Message/MessageID" />
                                </xsl:attribute>
                                 <xsl:if test="not(Message/MessageID)">
                                    <xsl:attribute name="disabled">true</xsl:attribute>
                                </xsl:if>
                            </input>
                        </td>
                    </tr>
                </table><br/>
                
                <table style="border:solid 1px #CCCCCC;width: 700px;">
                    <tr style="background-color: #CCCCCC;">
                        <td>IPMID</td>
                    </tr>
                    <tr style="">
                        <td>
                            <input type="text" name="IPMID" style="width: 690px;">
                                <xsl:attribute name="value">
                                    <xsl:value-of select="Message/IPMID" />
                                </xsl:attribute>
                                <xsl:if test="not(Message/IPMID)">
                                    <xsl:attribute name="disabled">true</xsl:attribute>
                                </xsl:if>
                            </input>
                        </td>
                    </tr>
                </table><br/>
                
                <table style="border:solid 1px #CCCCCC;width: 700px;">
                    <tr style="background-color: #CCCCCC;">
                        <td>Origin Encoded Information Type</td>
                    </tr>
                    <tr style="">
                        <td>
                            <input type="text" name="OriginEncodeInformationType" style="width: 690px;">
                                <xsl:attribute name="value">
                                    <xsl:value-of select="Message/OriginEncodeInformationType" />
                                </xsl:attribute>
                                <xsl:if test="not(Message/OriginEncodeInformationType)">
                                    <xsl:attribute name="disabled">true</xsl:attribute>
                                </xsl:if>
                            </input>
                        </td>
                    </tr>
                </table><br/>
                
                <table style="border:solid 1px #CCCCCC;width: 700px;">
                    <tr style="background-color: #CCCCCC;">
                        <td>GlobalDomain</td>
                    </tr>
                    <tr style="">
                        <td>
                            <input type="text" name="GlobalDomain" style="width: 690px;">
                                <xsl:attribute name="value">
                                    <xsl:value-of select="Message/GlobalDomain" />
                                </xsl:attribute>
                                <xsl:if test="not(Message/GlobalDomain)">
                                    <xsl:attribute name="disabled">true</xsl:attribute>
                                </xsl:if>
                            </input>
                        </td>
                    </tr>
                </table><br/>
                
                 <table style="border:solid 1px #CCCCCC;width: 700px;">
                    <tr style="background-color: #CCCCCC;">
                        <td>Priority [<xsl:value-of select="Message/Priority" />]</td>
                    </tr>
                    <tr style="">
                        <td>
                            <input type="radio" name="radio1" value="0">
                                <xsl:if test="Message/Priority='0'">
                                    <xsl:attribute name="checked">checked</xsl:attribute>
                                </xsl:if>
                                <xsl:if test="not(Message/Priority)">
                                    <xsl:attribute name="disabled">true</xsl:attribute>
                                </xsl:if>
                            </input>Normal (0)
                            
                            <input type="radio" name="radio1" value="1">
                                <xsl:if test="Message/Priority='1'">
                                    <xsl:attribute name="checked">checked</xsl:attribute>
                                </xsl:if>
                                <xsl:if test="not(Message/Priority)">
                                    <xsl:attribute name="disabled">true</xsl:attribute>
                                </xsl:if>
                            </input>Non Urgent (1)

                            <input type="radio" name="radio1" value="2">
                                <xsl:if test="Message/Priority='2'">
                                    <xsl:attribute name="checked">checked</xsl:attribute>
                                </xsl:if>
                                <xsl:if test="not(Message/Priority)">
                                    <xsl:attribute name="disabled">true</xsl:attribute>
                                </xsl:if>
                            </input>Urgent (2)
                        </td>
                    </tr>
                </table><br/>
                
                <table style="border:solid 1px #CCCCCC;width: 700px;">
                    <tr style="">
                        <td style="width: 20px;">
                            <input type="checkbox" name="Disclosure" value="1">
                                <xsl:if test="Message/Disclosure='1'">
                                    <xsl:attribute name="checked">checked</xsl:attribute>
                                </xsl:if>
                                <xsl:if test="not(Message/Disclosure)">
                                    <xsl:attribute name="disabled">true</xsl:attribute>
                                </xsl:if>
                            </input>
                        </td>
                        <td>
                            Disclosure [<xsl:value-of select="Message/Disclosure"/>]
                        </td>
                    </tr>
                    <tr style="">
                        <td style="width: 20px;">
                            <input type="checkbox" name="ConversionProhibited" value="1">
                                <xsl:if test="Message/ConversionProhibited='1'">
                                    <xsl:attribute name="checked">checked</xsl:attribute>
                                </xsl:if>
                                <xsl:if test="not(Message/ConversionProhibited)">
                                    <xsl:attribute name="disabled">true</xsl:attribute>
                                </xsl:if>
                            </input>
                        </td>
                        <td>
                            Conversion prohibited[<xsl:value-of select="Message/ConversionProhibited"/>]
                        </td>
                    </tr>
                    <tr style="">
                        <td style="width: 20px;">
                            <input type="checkbox" name="ReassignmentProhibited" value="1">
                                <xsl:if test="Message/ReassignmentProhibited='1'">
                                    <xsl:attribute name="checked">checked</xsl:attribute>
                                </xsl:if>
                                <xsl:if test="not(Message/ReassignmentProhibited)">
                                    <xsl:attribute name="disabled">true</xsl:attribute>
                                </xsl:if>
                            </input>
                        </td>
                        <td>
                            Reassignment prohibited[<xsl:value-of select="Message/ReassignmentProhibited"/>]
                        </td>
                    </tr>
                    <tr style="">
                        <td style="width: 20px;">
                            <input type="checkbox" name="ConversionWithLossProhibited" value="1">
                                <xsl:if test="Message/ConversionWithLossProhibited='1'">
                                    <xsl:attribute name="checked">checked</xsl:attribute>
                                </xsl:if>
                                <xsl:if test="not(Message/ConversionWithLossProhibited)">
                                    <xsl:attribute name="disabled">true</xsl:attribute>
                                </xsl:if>
                            </input>
                        </td>
                        <td>
                            Conversion with loss prohibited[<xsl:value-of select="Message/ConversionWithLossProhibited"/>]
                        </td>
                    </tr>
                    <tr style="">
                        <td style="width: 20px;">
                            <input type="checkbox" name="DLExpasionProhibited" value="1">
                                <xsl:if test="Message/DLExpasionProhibited='1'">
                                    <xsl:attribute name="checked">checked</xsl:attribute>
                                </xsl:if>
                                <xsl:if test="not(Message/DLExpasionProhibited)">
                                    <xsl:attribute name="disabled">true</xsl:attribute>
                                </xsl:if>
                            </input>
                        </td>
                        <td>
                            DL Expansion Prohibited[<xsl:value-of select="Message/DLExpasionProhibited"/>]
                        </td>
                    </tr>
                </table><br/>
                
                
                <xsl:for-each select="Message/ATS">
                   
                    <table style="border:solid 1px #CCCCCC;width: 700px;">
                        <tr style="background-color: #CCCCCC;">
                            <td> ATS</td>
                        </tr>
                        <tr style="">
                            <td>
                                Extended [<xsl:value-of select="Extended"/>]
                                <input type="checkbox" name="Extended" value="1">
                                    <xsl:if test="Extended='1'">
                                        <xsl:attribute name="checked">checked</xsl:attribute>
                                    </xsl:if>
                                    <xsl:if test="not(Extended)">
                                        <xsl:attribute name="disabled">true</xsl:attribute>
                                    </xsl:if>
                                </input>
                            </td>
                        </tr>
                        <tr style="">
                            <td>EOH [<xsl:value-of select="EOHMode"/>]</td>
                        </tr>
                        <tr style="">
                            <td>
                                
                                <input type="radio" name="EOH1" value="1">
                                    <xsl:if test="EOHMode='1'">
                                        <xsl:attribute name="checked">checked</xsl:attribute>
                                    </xsl:if>
                                    <xsl:if test="not(EOHMode)">
                                        <xsl:attribute name="disabled">true</xsl:attribute>
                                    </xsl:if>
                                </input>Carriage return, line feed (1)
                                <input type="radio" name="EOH1" value="2">
                                    <xsl:if test="EOHMode='2'">
                                        <xsl:attribute name="checked">checked</xsl:attribute>
                                    </xsl:if>
                                    <xsl:if test="not(EOHMode)">
                                        <xsl:attribute name="disabled">true</xsl:attribute>
                                    </xsl:if>
                                </input>Line feed (2)
                                <input type="radio" name="EOH1" value="2">
                                    <xsl:if test="EOHMode='3'">
                                        <xsl:attribute name="checked">checked</xsl:attribute>
                                    </xsl:if>
                                    <xsl:if test="not(EOHMode)">
                                        <xsl:attribute name="disabled">true</xsl:attribute>
                                    </xsl:if>
                                </input>None (3)
                            </td>
                        </tr>
                        <tr style="">
                            <td>Filing time</td>
                        </tr>
                         <tr style="">
                            <td>
                                <input type="text" name="FilingTIme" value="1" style="width: 690px;">
                                    <xsl:attribute name="value">
                                        <xsl:value-of select="FilingTime"/>
                                    </xsl:attribute>
                                    <xsl:if test="not(FilingTime)">
                                        <xsl:attribute name="disabled">true</xsl:attribute>
                                    </xsl:if>
                                </input>
                            </td>
                        </tr>
                         <tr style="">
                            <td>Text</td>
                        </tr>
                        <tr style="">
                            <td>
                                <TextArea style="width: 690px; height: 100px;">
                                    <xsl:value-of select="FilingTime"/>
                                    <xsl:if test="not(FilingTime)">
                                        <xsl:attribute name="disabled">true</xsl:attribute>
                                    </xsl:if>
                                </TextArea>
                            </td>
                        </tr>
                    </table>
                </xsl:for-each><br/>
                
                <table style="border:solid 1px #CCCCCC;width: 700px;">
                    <tr style="background-color: #CCCCCC;">
                        <td colspan="3">Recipient</td>
                    </tr>
                    <xsl:for-each select="Message/Recipient">
                        <tr>
                            <td><xsl:value-of select="OR"/></td>
                            <td>
                                <Select>
                                    <xsl:if test="not(Report)">
                                        <xsl:attribute name="disabled">true</xsl:attribute>
                                    </xsl:if>
                                    <option value="DR_NO_REPORT">
                                        <xsl:if test="Report='DR_NO_REPORT'">
                                            <xsl:attribute name="selected">true</xsl:attribute>
                                        </xsl:if>
                                        NR
                                    </option>
                                    <option value="DR_NON_DELIVERY_REPORT">
                                        <xsl:if test="Report='DR_NON_DELIVERY_REPORT'">
                                            <xsl:attribute name="selected">true</xsl:attribute>
                                        </xsl:if>
                                        NDR
                                    </option>
                                    <option value="DR_DELIVERY_REPORT">
                                        <xsl:if test="Report='DR_DELIVERY_REPORT'">
                                            <xsl:attribute name="selected">true</xsl:attribute>
                                        </xsl:if>
                                        DR
                                    </option>
                                    <option value="AUDITED_REPORT">
                                        <xsl:if test="Report='AUDITED_REPORT'">
                                            <xsl:attribute name="selected">true</xsl:attribute>
                                        </xsl:if>
                                        AR
                                    </option>
                                </Select>
                            </td>
                             <td>
                                <Select>
                                    <xsl:if test="not(ReceiptNotify)">
                                        <xsl:attribute name="disabled">true</xsl:attribute>
                                    </xsl:if>
                                    <option value="1">
                                        <xsl:if test="ReceiptNotify='1'">
                                            <xsl:attribute name="selected">true</xsl:attribute>
                                        </xsl:if>
                                        RN
                                    </option>
                                    <option value="2">
                                        <xsl:if test="ReceiptNotify='2'">
                                            <xsl:attribute name="selected">true</xsl:attribute>
                                        </xsl:if>
                                        NRN
                                    </option>
                                </Select>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table><br/>
                
                <xsl:for-each select="Message/Body">
                    <table style="border:solid 1px #CCCCCC;width: 700px;">
                        <tr style="background-color: #CCCCCC;">
                            <td>Body</td>
                        </tr>
                        <tr style="">
                            <td>Type
                                <Select>
                                    <xsl:if test="not(Type)">
                                        <xsl:attribute name="disabled">true</xsl:attribute>
                                    </xsl:if>
                                    <option value="General">
                                        <xsl:if test="Type='General'">
                                            <xsl:attribute name="selected">true</xsl:attribute>
                                        </xsl:if>
                                        General
                                    </option>
                                    <option value="IA5-Text">
                                        <xsl:if test="Type='IA5-Text'">
                                            <xsl:attribute name="selected">true</xsl:attribute>
                                        </xsl:if>
                                        IA5-Text
                                    </option>
                                </Select>
                            </td>
                        </tr>
                        <tr style="">
                            <td>
                                Character Set
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="Text" name="CharacterSet" style="width: 690px;">
                                    <xsl:if test="not(CharSet)">
                                        <xsl:attribute name="disabled">true</xsl:attribute>
                                    </xsl:if>
                                    <xsl:attribute name="value">
                                        <xsl:value-of select="CharSet"/>
                                    </xsl:attribute>
                                </input>
                            </td>
                        </tr>
                        <tr style="">
                            <td>Content</td>
                        </tr>
                        <tr style="">
                            <td>
                                <TextArea style="width: 690px; height: 100px;">
                                    <xsl:value-of select="Text"/>
                                    <xsl:if test="not(Text)">
                                        <xsl:attribute name="disabled">true</xsl:attribute>
                                    </xsl:if>
                                </TextArea>
                            </td>
                        </tr>
                    </table>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>