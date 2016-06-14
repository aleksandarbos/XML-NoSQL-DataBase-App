package converter;

import org.junit.Test;

/**
 * Created by aleksandar on 14.6.16..
 */
public class XMLTransformationTest {

    // TODO: make methods return stream, and return that by renderBinary() method...

    @Test
    public void transformToPdf() throws Exception {
        XMLTransformation.transformToPdf("/parliament/regulations/4702994916404006768.xml");
    }

    @Test
    public void transformToXHtml() throws Exception {
        XMLTransformation.transformToXhtml("/parliament/amendments/12909923994514104964.xml");
    }

}