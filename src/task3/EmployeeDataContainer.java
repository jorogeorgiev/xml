package task3;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author georgi.hristov@clouway.com
 */
public class EmployeeDataContainer implements DataContainer<InputSource> {


  private  String schema;
  private  String source;

  public EmployeeDataContainer(String schema, String source) {


    this.schema = schema;
    this.source = source;
  }

  @Override
  public Boolean isDataValid() {
    Boolean isValid;
    try {
      SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema employeeSchema = factory.newSchema(new StreamSource(getClass().getResourceAsStream(schema)));
      Validator validator = employeeSchema.newValidator();
      validator.validate(new StreamSource(getClass().getResourceAsStream(source)));
      isValid = true;
    } catch (SAXException e) {
      isValid = false;
    } catch (IOException e) {
      isValid = false;
    }
    return isValid;
  }

  @Override
  public InputSource getData() {
    return new InputSource(getClass().getResourceAsStream(source));
  }
}
