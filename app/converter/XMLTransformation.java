package converter;

import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.apache.fop.apps.*;
import org.xml.sax.SAXException;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;


public class XMLTransformation {

	public static void transformToPdf() {
		
		File xmlFile = new File("xml_schema/primeri/regulation.xml");
		File pdfFile = new File("tmp/amandment.pdf");
		File xsltFile = new File("conf/to_pdf.xsl");

		FopFactory fopFactory = FopFactory.newInstance();
		try {
			fopFactory.setUserConfig(new File("conf/fop.xconf"));
			TransformerFactory transformerFactory = new TransformerFactoryImpl();	
			System.out.println("[INFO] Transformation to PDF: Started.");
			StreamSource transformSource = new StreamSource(xsltFile);
			StreamSource source = new StreamSource(xmlFile);
			FOUserAgent userAgent = fopFactory.newFOUserAgent();
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			Transformer xslFoTransformer = transformerFactory.newTransformer(transformSource);
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);
			Result res = new SAXResult(fop.getDefaultHandler());
			xslFoTransformer.transform(source, res);
			OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));
			out.write(outStream.toByteArray());
			out.close();
			System.out.println("[INFO] Transformation to PDF: End.");
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (FOPException e) {
			e.printStackTrace();
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}

	}

	public static void transformToXhtml() {
		File xmlFile = new File("xml_schema/primeri/amandment.xml");
		File xhtmlFile = new File("tmp/amandment.html");
		File xsltFile = new File("conf/to_html.xsl");

		try {
			System.out.println("[INFO] Transformation to XHTML: Started.");
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));
			Source text = new StreamSource(xmlFile);
			transformer.transform(text, new StreamResult(xhtmlFile));
			System.out.println("[INFO] Transformation to XHTML: End.");
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}
}
