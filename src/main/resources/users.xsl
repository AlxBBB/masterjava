<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" omit-xml-declaration="yes" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <html>
      <body>
        <h1>Users</h1>
        <table>
          <tr>
            <td>Full Name</td>
            <td>Email</td>
          </tr>
          <xsl:for-each select="/*[name()='Payload']/*[name()='Users']/*[name()='User']">
             <tr>
               <td>
                 <xsl:value-of select="current()/*[name()='fullName']"/>
               </td>
               <td>
                 <xsl:value-of select="@email"/>
               </td>
             </tr>
          </xsl:for-each>
        </table>
      </body>
    </html>
  </xsl:template>
  <xsl:template match="text()"/>
</xsl:stylesheet>