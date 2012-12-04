package task3;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
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


  private final InputStream schema;
  private final InputStream source;

  public EmployeeDataContainer(InputStream schema, InputStream source) {
    this.schema = schema;
    this.source = source;
  }

  @Override
  public Boolean isDataValid() {
    Boolean isValid;
    try {
      SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema employeeSchema = factory.newSchema(new StreamSource(schema));

      Validator validator = employeeSchema.newValidator();
      validator.validate(new StreamSource(source));

      isValid = true;
    } catch (SAXException e) {
      isValid = false;
    } catch (IOException e) {
      isValid = false;
    }  finally {

      try {
        schema.reset();
        source.reset();
      } catch (IOException ignored) {

      }

    }
    return isValid;
  }

  @Override
  public InputSource getData() throws IOException {
    InputSource inputSource = new InputSource(source);
    source.reset();
    return inputSource;
  }
}
