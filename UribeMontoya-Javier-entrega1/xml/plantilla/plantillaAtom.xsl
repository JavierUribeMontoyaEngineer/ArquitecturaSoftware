<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:p="http://www.um.es/programasRTVE">

	<xsl:output method="xml" indent="yes" />
	<xsl:template match="/">
		<feed xmlns="http://www.w3.org/2005/Atom">
			<title type="text"><xsl:value-of select="p:programa/p:nombre" /></title>
			<id><xsl:value-of select="p:programa/@id" /></id>
			<link href="{p:programa/@urlPrograma}" />
			<link type="image/png" href="{p:programa/@urlImagen}" />
			<xsl:for-each select="p:programa/p:emision">
				<entry>
					<title type="text"><xsl:value-of select="p:titulo" /></title>
					<link href="{@url}" />
					<published><xsl:value-of select="@fecha" />T00:00:00Z</published>
					<updated><xsl:value-of select="@fecha" />T00:00:00Z</updated>
				</entry>
			</xsl:for-each>

		</feed>

	</xsl:template>

</xsl:stylesheet>