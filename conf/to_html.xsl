<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:a="http://www.parlament.gov.rs/amandmani" 
	xmlns:p="http://www.parlament.gov.rs/propisi"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/">
		<html>
			<head>
				<title><xsl:value-of select="//p:Naziv"/><xsl:value-of select="//a:Naziv"/></title>
				<style type="text/css">
					h1 {
						font-size: 24pt;
						text-align: center;
						margin: 24pt;
					}
					h2 {
						font-size: 20pt;
						text-align: center;
						margin: 24pt;
					}
					h3 {
						font-size: 18pt;
						text-align: center;
						margin: 18pt;
					}
					h4 {
						font-size: 16pt;
						text-align: center;
						margin: 18pt;
					}
					h5 {
						font-size: 14pt;
						text-align: center;
						margin: 18pt;
					}
					p {
  					    text-indent: 30pt;
  					    text-align: justify;
					}
					li {
  					    text-indent: 30pt;
  					    text-align: justify;
						list-style-type: none;
					}
					.hide {
  					    display: none;
					}
				</style>
			</head>
			<body>
				<xsl:apply-templates/> 
			</body>
		</html>
	</xsl:template>
	
	<xsl:template match="p:Naziv">
		<h1><xsl:value-of select="text()"/></h1>
	</xsl:template>
	
	<xsl:template match="p:Deo">
		<h2>DEO 
			<xsl:choose>
				<xsl:when test="@Oznaka_dela = 1">
					PRVI
				</xsl:when>
				<xsl:when test="@Oznaka_dela = 2">
					DRUGI
				</xsl:when>
				<xsl:when test="@Oznaka_dela = 3">
					TREĆI
				</xsl:when>
				<xsl:when test="@Oznaka_dela = 4">
					ČETVRTI
				</xsl:when>
				<xsl:when test="@Oznaka_dela = 5">
					PETI
				</xsl:when>
				<xsl:when test="@Oznaka_dela = 6">
					ŠESTI
				</xsl:when>
				<xsl:when test="@Oznaka_dela = 7">
					SEDMI
				</xsl:when>
				<xsl:when test="@Oznaka_dela = 8">
					OSMI
				</xsl:when>
				<xsl:when test="@Oznaka_dela = 9">
					DEVETI
				</xsl:when>
				<xsl:when test="@Oznaka_dela = 10">
					DESETI
				</xsl:when>
				<xsl:otherwise>
					PRETERA GA...
				</xsl:otherwise>
			</xsl:choose><br/>
		<xsl:value-of select="@Naziv_glave"/></h2>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="p:Glava">
		<h3>
			<xsl:choose>
				<xsl:when test="@Oznaka_glave = 1">
					I
				</xsl:when>
				<xsl:when test="@Oznaka_glave = 2">
					II
				</xsl:when>
				<xsl:when test="@Oznaka_glave = 3">
					III
				</xsl:when>
				<xsl:when test="@Oznaka_glave = 4">
					IV
				</xsl:when>
				<xsl:when test="@Oznaka_glave = 5">
					V
				</xsl:when>
				<xsl:when test="@Oznaka_glave = 6">
					VI
				</xsl:when>
				<xsl:when test="@Oznaka_glave = 7">
					VII
				</xsl:when>
				<xsl:when test="@Oznaka_glave = 8">
					VIII
				</xsl:when>
				<xsl:when test="@Oznaka_glave = 9">
					IX
				</xsl:when>
				<xsl:when test="@Oznaka_glave = 10">
					X
				</xsl:when>
				<xsl:otherwise>
					PRETERA GA...
				</xsl:otherwise>
			</xsl:choose>. <xsl:value-of select="@Naziv_glave"/>
		</h3>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="p:Odeljak">
		<h3><xsl:value-of select="@Oznaka_odeljka"/>. <xsl:value-of select="@Naziv_odeljka"/></h3>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="p:Pododeljak">
		<h4>
			<xsl:choose>
				<xsl:when test="@Oznaka_pododeljka = 1">
					a
				</xsl:when>
				<xsl:when test="@Oznaka_pododeljka = 2">
					b
				</xsl:when>
				<xsl:when test="@Oznaka_pododeljka = 3">
					v
				</xsl:when>
				<xsl:when test="@Oznaka_pododeljka = 4">
					g
				</xsl:when>
				<xsl:when test="@Oznaka_pododeljka = 5">
					d
				</xsl:when>
				<xsl:when test="@Oznaka_pododeljka = 6">
					đ
				</xsl:when>
				<xsl:when test="@Oznaka_pododeljka = 7">
					e
				</xsl:when>
				<xsl:when test="@Oznaka_pododeljka = 8">
					ž
				</xsl:when>
				<xsl:when test="@Oznaka_pododeljka = 9">
					z
				</xsl:when>
				<xsl:when test="@Oznaka_pododeljka = 10">
					i
				</xsl:when>
				<xsl:when test="@Oznaka_pododeljka = 11">
					j
				</xsl:when>
				<xsl:when test="@Oznaka_pododeljka = 12">
					k
				</xsl:when>
				<xsl:when test="@Oznaka_pododeljka = 13">
					l
				</xsl:when>
				<xsl:when test="@Oznaka_pododeljka = 14">
					lj
				</xsl:when>
				<xsl:when test="@Oznaka_pododeljka = 15">
					m
				</xsl:when>
				<xsl:when test="@Oznaka_pododeljka = 16">
					n
				</xsl:when>
				<xsl:when test="@Oznaka_pododeljka = 17">
					nj
				</xsl:when>
				<xsl:otherwise>
					PRETERA GA...
				</xsl:otherwise>
			</xsl:choose>) <xsl:value-of select="@Naziv_pododeljka"/>
		</h4>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="p:Clan">
		<h5><xsl:value-of select="@Naziv_clana"/><br/>
		Član <xsl:value-of select="@Oznaka_clana"/>.</h5>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="p:Stav">
		<p><xsl:value-of select="text()"/></p>
		<xsl:apply-templates select="p:Tacka"/> 
	</xsl:template>
	
	<xsl:template match="p:Tacka">
		<li>
			<xsl:value-of select="@Oznaka_tacke"/>) <xsl:value-of select="text()"/>
		</li><br/>
		<xsl:apply-templates select="p:Podtacka"/>
	</xsl:template>
	
	<xsl:template match="p:Podtacka">
		<li>
			(<xsl:value-of select="@Oznaka_podtacke"/>) <xsl:value-of select="text()"/>
		</li><br/>
		<xsl:apply-templates select="p:Alineja"/> 
	</xsl:template>
	
	<xsl:template match="p:Alineja">
		<li>
			- <xsl:value-of select="@Oznaka_alineje"/> <xsl:value-of select="text()"/>
		</li><br/>
	</xsl:template>
	
	<xsl:template match="a:Amandman">
		<h5>
			AMANDMAN
		</h5>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="a:Naziv">
		<h1><xsl:value-of select="text()"/></h1>
	</xsl:template>
	
	<xsl:template match="a:Sadrzaj">
		<p>
			<xsl:value-of select="text()"/>
		</p>
		<xsl:apply-templates select="a:Referenca"/> 
	</xsl:template>
	
	<xsl:template match="a:Obrazlozenje">
		<h5>
			Obrazloženje:
		</h5>
		<p>
			<xsl:value-of select="text()"/>
		</p>
		<xsl:apply-templates select="a:Referenca"/> 
	</xsl:template>
	
	<xsl:template match="a:Preambula">
		<p class="hide">
			<xsl:value-of select="text()"/>
		</p>
	</xsl:template>
	
	<xsl:template match="a:Deo_za_izmenu">
		<p class="hide">
			<xsl:value-of select="text()"/>
		</p>
	</xsl:template>
	
</xsl:stylesheet>