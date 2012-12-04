package task3;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;

/**
 * @author georgi.hristov@clouway.com
 */
public class XMLEmployeeDataValidator implements EmployeeDataValidator {


  private final Source schemaSource;
  private final Source sourceToValidate;

  public XMLEmployeeDataValidator(Source schemaSource, Source sourceToValidate) {

    this.schemaSource = schemaSource;

    this.sourceToValidate = sourceToValidate;

  }

  @Override
  public Boolean isXMLValid() {
    Boolean isValid = false;
    try {
      SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = factory.newSchema(schemaSource);
      Validator validator = schema.newValidator();
      validator.validate(sourceToValidate);
      isValid = true;
    } catch (SAXException e) {
      isValid = false;
    } catch (IOException e) {
      System.out.println("Something happened reading the source");
    }
    return isValid;
  }

}
