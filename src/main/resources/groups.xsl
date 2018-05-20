<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" omit-xml-declaration="yes" indent="yes"/>
  <xsl:param name="project" />
  <xsl:strip-space elements="*"/>
  <xsl:template match="/">
    <xsl:variable name="projectId"
      select="/*[name()='Payload']/*[name()='Projects']/*[name()='Project' and text()=$project]/@id"/>
    <html>
      <body>
        <h1>Groups</h1>
        <table>
          <tr>
            <td>ID</td>
            <td>Group</td>
          </tr>
          <xsl:for-each select="/*[name()='Payload']/*[name()='Groups']/*[name()='Group' and @project=$projectId]">
             <tr>
               <td>
                 <xsl:value-of select="@id"/>
               </td>
               <td>
                 <xsl:value-of select="current()/text()"/>
               </td>
             </tr>
          </xsl:for-each>
        </table>
      </body>
    </html>
  </xsl:template>
  <xsl:template match="text()"/>
</xsl:stylesheet>