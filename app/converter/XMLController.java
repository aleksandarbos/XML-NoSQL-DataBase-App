package converter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;

public class XMLController {

	@SuppressWarnings("unused")
	public static boolean checkWellFormness(String xml) {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		InputStream stream = new ByteArrayInputStream(xml.getBytes());
		try {
			builder = factory.newDocumentBuilder();			
			Document document = builder.parse(stream);
		} catch (SAXException e) {
			return false;
		} catch (ParserConfigurationException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public static boolean checkValidity(String xml, String documentType) {		
		Source schemaFile = null;
		try {
			if (documentType.equals("regulation"))
				schemaFile = new StreamSource(new File("xml_schema/propisi.xsd"));
			else
				schemaFile = new StreamSource(new File("xml_schema/amandmani.xsd"));
			
			Source stream = new StreamSource(new ByteArrayInputStream(xml.getBytes()));
		    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		    Schema schema = factory.newSchema(schemaFile);
		    Validator validator = schema.newValidator();	    
	        validator.validate(stream);
	    } catch (IOException e) {
	    	return true;
	    } catch (SAXException e) {
	    	return false;
		}
    	return true;
	}
}
