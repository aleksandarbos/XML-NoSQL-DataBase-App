package converter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by aleksandar on 5.6.16..
 */
public class Converter {

    /**
     * Converts Java object -> XML. Notice: Java object must be an XMLRoot element!
     * @param objectToMarshall Java object that needs to be converted to XML.
     * @param filePathToSave Place where new XML file will be saved. If marshallType is TO_STRING then leave empty.
     * @return Marshalled XML string if {@link MarshallType} is set on TO_STRING.
     * @throws JAXBException
     */
    public static String marshall(MarshallType marshallType, Object objectToMarshall, String filePathToSave) throws JAXBException {
        System.out.println("[INFO] JAXB marshalling instance of " + objectToMarshall.getClass() + " class.\n");

        Class objectClass = objectToMarshall.getClass();
        Package classPackage = objectClass.getPackage();
        String classPackageName = classPackage.getName();

        StringWriter sw = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(classPackageName);

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        // marshaller.marshal(objectToMarshall, System.out); for debugging purposes

        switch (marshallType) {
            case TO_DISK:
                marshaller.marshal(objectToMarshall, new File(filePathToSave));
                System.out.println("[INFO] Marshalled file at: " + filePathToSave);
                break;
            case TO_STRING:
                marshaller.marshal(objectToMarshall, sw);
                break;
        }
        return sw.toString();
    }

    /**
     * Converts XML -> Java object.
     * @param unmarshallType If is set to FROM_DISK then 'data' is filePath, else if it's FROM_STRING then 'data' is xml instance data.
     * @param data File path of xml file to open.
     * @param classToCreate Targeted class to create Java object instance.
     * @return  Converted Java object.
     * @throws JAXBException
     */
    public static Object unmarshall(UnmarshallType unmarshallType, String data, Class classToCreate) throws JAXBException {
        System.out.println("[INFO] JAXB unmarshalling to: " + classToCreate.getName() + " Java object.\n");

        Package objectPackage = classToCreate.getPackage();
        JAXBContext context = JAXBContext.newInstance(objectPackage.getName());

        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object unmarshalledObject = null;

        switch (unmarshallType) {
            case FROM_DISK:
                unmarshalledObject = unmarshaller.unmarshal(new File(data));
                break;
            case FROM_STRING:
                StringReader stringReader = new StringReader(data);
                unmarshalledObject = unmarshaller.unmarshal(stringReader);
                break;
        }

        return unmarshalledObject;
    }


}
