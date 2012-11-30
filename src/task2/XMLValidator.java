package task2;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;

/**
 * @author georgi.hristov@clouway.com
 */
public class XMLValidator {

  public boolean validate(Document xmlDOM , Source xmlSchemaSource) throws SAXException {
    Boolean isValid= false;
    SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema xmlSchema =  schemaFactory.newSchema(xmlSchemaSource);
    Validator schemaValidator = xmlSchema.newValidator();
    try {
      schemaValidator.validate(new DOMSource(xmlDOM));
      isValid = true;
    } catch (IOException e) {
      //ini mini maini mo
    }
    return isValid;
  }

}
