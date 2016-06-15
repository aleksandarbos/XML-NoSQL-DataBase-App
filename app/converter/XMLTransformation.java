package converter;

import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import database.DatabaseAccessor;
import org.apache.fop.apps.*;
import org.xml.sax.SAXException;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;


public class XMLTransformation {	
	public static InputStream transformToPdf(String docUri) {

	File xsltFile = new File("conf/to_pdf.xsl");

	String docData = DatabaseAccessor.getInstance().readXmlFromDatabase(docUri);
	InputStream stream = new ByteArrayInputStream(docData.getBytes(StandardCharsets.UTF_8));
	
	PipedInputStream in = new PipedInputStream();
	PipedOutputStream outt = null;
	FopFactory fopFactory = FopFactory.newInstance();
	try {
		outt = new PipedOutputStream(in);
		fopFactory.setUserConfig(new File("conf/fop.xconf"));
		TransformerFactory transformerFactory = new TransformerFactoryImpl();	
		System.out.println("[INFO] Transformation to PDF: Started.");
		StreamSource transformSource = new StreamSource(xsltFile);
		StreamSource source = new StreamSource(stream);
		FOUserAgent userAgent = fopFactory.newFOUserAgent();			
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		Transformer xslFoTransformer = transformerFactory.newTransformer(transformSource);
		Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);
		Result res = new SAXResult(fop.getDefaultHandler());			
		xslFoTransformer.transform(source, res);
		outt.write(outStream.toByteArray());
		outt.flush();
		in.close();
		System.out.println("[INFO] Transformation to PDF: End.");
	} catch (TransformerException e) {
		e.printStackTrace();
	} catch (FOPException e) {
		e.printStackTrace();
	} catch (SAXException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return in;

}
	public static InputStream transformToPdfFile(String docUri) {
		File pdfFile = new File("tmp/result.pdf");
		File xsltFile = new File("conf/to_pdf.xsl");
		
		String docData = DatabaseAccessor.getInstance().readXmlFromDatabase(docUri);
		InputStream stream = new ByteArrayInputStream(docData.getBytes(StandardCharsets.UTF_8));
		InputStream targetStream = null;
		
		FopFactory fopFactory = FopFactory.newInstance();
		try {
			fopFactory.setUserConfig(new File("conf/fop.xconf"));
			TransformerFactory transformerFactory = new TransformerFactoryImpl();	
			System.out.println("[INFO] Transformation to PDF: Started.");
			StreamSource transformSource = new StreamSource(xsltFile);
			StreamSource source = new StreamSource(stream);
			FOUserAgent userAgent = fopFactory.newFOUserAgent();
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			Transformer xslFoTransformer = transformerFactory.newTransformer(transformSource);
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);
			Result res = new SAXResult(fop.getDefaultHandler());
			xslFoTransformer.transform(source, res);
			OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));
			out.write(outStream.toByteArray());
			out.close();
			targetStream = new FileInputStream(pdfFile);
			//pdfFile.delete();
			System.out.println("[INFO] Transformation to PDF: End.");
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (FOPException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return targetStream;
	}

	public static String transformToXhtml(String docUri) {

		File xsltFile = new File("conf/to_html.xsl");
		String result = null;
		
		String docData = DatabaseAccessor.getInstance().readXmlFromDatabase(docUri);
		InputStream stream = new ByteArrayInputStream(docData.getBytes(StandardCharsets.UTF_8));

		try {
		    StringWriter writerStream = new StringWriter();
		    StreamResult resultStream = new StreamResult(writerStream);
		    
			System.out.println("[INFO] Transformation to XHTML: Started.");
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));
			Source text = new StreamSource(stream);
			transformer.transform(text, resultStream);
			
		    result = writerStream.toString();		    
			System.out.println("[INFO] Transformation to XHTML: End.");
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
