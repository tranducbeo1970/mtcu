<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <body style="font-family:calibri;">
                <h2>AFTN Message</h2>
                <hr/>
                <table style="border:solid 1px #CCCCCC;">
                    <th width="100px"></th>
                    <th width="500px"></th>
                    <tr style="background-color: #CCCCCC;">
                        <td>Channel ID</td>
                        <td><xsl:value-of select="Message/MessageID"/></td>
                    </tr>
                    <tr>
                        <td>Priority</td>
                        <td><xsl:value-of select="Message/Priority"/></td>
                    </tr>
                     <tr style="background-color: #CCCCCC;">
                        <td>Addresses</td>
                        <td>
                            <xsl:for-each select="Message/Addresses">
                                <span><xsl:value-of select="current()"/></span><br/>
                            </xsl:for-each>
                        </td>
                    </tr>
                    <tr>
                        <td>Filing Time</td>
                        <td><xsl:value-of select="Message/FilingTime"/></td>
                    </tr>
                    <tr style="background-color: #CCCCCC;">
                        <td>Originator</td>
                        <td><xsl:value-of select="Message/Originator"/></td>
                    </tr>
                     <tr>
                        <td>OHI</td>
                        <td><xsl:value-of select="Message/OHI"/>
                        </td>
                    </tr>
                     <tr style="background-color: #CCCCCC;">
                        <td>Content</td>
                        <td><xsl:value-of select="Message/Content"/></td>
                    </tr>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>