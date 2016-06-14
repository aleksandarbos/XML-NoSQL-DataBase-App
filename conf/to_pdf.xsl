<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:p="http://www.parlament.gov.rs/propisi"
	xmlns:a="http://www.parlament.gov.rs/amandmani" 
 	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	
	<xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
            	<fo:simple-page-master master-name="simple">
                    <fo:region-body margin="1in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="simple">
                <fo:flow flow-name="xsl-region-body">
                    <xsl:apply-templates/> 
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
	</xsl:template>
	
	<xsl:template match="p:Naziv">
		<fo:block font-family="Arial" 
			font-weight="bold" 
			font-size="18pt" 
			text-align="center"
			space-before="12pt"
			space-after="12pt">
			<xsl:value-of select="text()"/>
		</fo:block>
	</xsl:template>
	
	<xsl:template match="p:Deo">
		<fo:block font-family="Arial" 
			font-weight="bold" 
			font-size="16pt" 
			text-align="center"
			space-before="12pt"
			space-after="12pt">DEO 
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
			</xsl:choose>
		</fo:block>
		<fo:block font-family="Arial" 
			font-weight="bold" 
			font-size="16pt" 
			text-align="center"
			space-before="12pt"
			space-after="12pt">
			<xsl:value-of select="@Naziv_dela"/>
		</fo:block>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="p:Glava">
		<fo:block font-family="Arial" 
			font-weight="bold" 
			font-size="15pt" 
			text-align="center"
			space-before="12pt"
			space-after="12pt">
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
		</fo:block>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="p:Odeljak">
		<fo:block font-family="Arial" 
			font-weight="bold" 
			font-size="14pt" 
			text-align="center"
			space-before="22pt"
			space-after="12pt">
			<xsl:value-of select="@Oznaka_odeljka"/>. <xsl:value-of select="@Naziv_odeljka"/>
		</fo:block>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="p:Pododeljak">
		<fo:block font-family="Arial" 
			font-weight="bold" 
			font-size="14pt" 
			text-align="center"
			space-before="12pt"
			space-after="12pt">
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
		</fo:block>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="p:Clan">
		<fo:block font-family="Arial" 
			font-size="14pt" 
			font-weight="bold" 
			text-align="center"
			space-before="12pt"
			space-after="12pt">
			<xsl:value-of select="@Naziv_clana"/>
		</fo:block>
		<fo:block font-family="Arial" 
			font-size="14pt" 
			font-weight="bold" 
			text-align="center"
			space-before="12pt"
			space-after="12pt">
			Član <xsl:value-of select="@Oznaka_clana"/>.
		</fo:block>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="p:Stav">
		<fo:block  
			font-size="12pt" 
			text-align="justify"
			text-indent="1.5cm"
			space-before="12pt"
			space-after="12pt">
			<xsl:apply-templates/>
		</fo:block>
	</xsl:template>
	
	<xsl:template match="p:Tacka">
		<fo:block 
			font-size="12pt" 
			text-align="justify"
			text-indent="1.5cm"
			space-before="12pt"
			space-after="12pt">
			<xsl:value-of select="@Oznaka_tacke"/>) <xsl:apply-templates/>
		</fo:block>
	</xsl:template>
	
	<xsl:template match="p:Podtacka">
		<fo:block font-family="Arial" 
			font-size="12pt" 
			text-align="justify"
			text-indent="1.5cm"
			space-before="12pt"
			space-after="12pt">
			(<xsl:value-of select="@Oznaka_podtacke"/>) <xsl:apply-templates/>
		</fo:block>
	</xsl:template>
	
	<xsl:template match="p:Alineja">
		<fo:block font-family="Arial" 
			font-size="12pt" 
			text-align="justify"
			text-indent="1.5cm"
			space-before="12pt"
			space-after="12pt">
			- <xsl:value-of select="@Oznaka_alineje"/> <xsl:apply-templates/>
		</fo:block>
	</xsl:template>
	
	<xsl:template match="p:Referenca">
		<fo:basic-link color="#069"
			text-decoration="underline">
			<xsl:attribute name="external-destination">
				<xsl:value-of select="@Uri_propisa"/>
			</xsl:attribute>
			<xsl:apply-templates/> 
		</fo:basic-link>
	</xsl:template>
	
	<xsl:template match="a:Amandman">
		<fo:block font-family="Arial" 
			font-weight="bold" 
			font-size="14pt" 
			text-align="center"
			space-before="12pt"
			space-after="12pt">
			AMANDMAN
		</fo:block>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="a:Naziv">
		<fo:block font-family="Arial" 
			font-weight="bold" 
			font-size="18pt" 
			text-align="center"
			space-before="12pt"
			space-after="12pt">
			<xsl:value-of select="text()"/>
		</fo:block>
	</xsl:template>
	
	<xsl:template match="a:Sadrzaj">
		<fo:block font-family="Arial" 
			font-size="12pt" 
			text-align="justify"
			space-before="26pt"
			space-after="12pt">
			<xsl:apply-templates/>
		</fo:block>
	</xsl:template>
	
	<xsl:template match="a:Obrazlozenje">
		<fo:block font-family="Arial" 
			font-size="12pt" 
			font-weight="bold" 
			text-align="center"
			space-before="12pt"
			space-after="12pt">
			Obrazloženje:
		</fo:block>
		<fo:block font-family="Arial" 
			font-size="12pt" 
			text-align="justify"
			space-before="12pt"
			space-after="12pt">
			<xsl:apply-templates/> 
		</fo:block>
	</xsl:template>
	
	<xsl:template match="a:Referenca">
		<fo:basic-link color="#069"
			text-decoration="underline">
			<xsl:attribute name="external-destination">
				<xsl:value-of select="@Uri_propisa"/>
			</xsl:attribute>
			<xsl:apply-templates/> 
		</fo:basic-link>
	</xsl:template>
	
	<xsl:template match="text()">
		<xsl:value-of select="concat(' ',normalize-space(.),' ')"/>
	</xsl:template>
	
</xsl:stylesheet>