<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
  <html>
  <body>
    <h2>X400 Attributes</h2>
    <table border="1">
      <tr bgcolor="#9acd32">
        <th>Name</th>
        <th>Value</th>
      </tr>
      <xsl:for-each select="MtAttributes/Items">
        <tr>
          <td><xsl:value-of select="@Key"/></td>
          <td><xsl:value-of select="@Value"/></td>
        </tr>
      </xsl:for-each>
    </table>
  </body>
  </html>
</xsl:template>
</xsl:stylesheet>