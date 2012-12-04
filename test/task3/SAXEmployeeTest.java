package task3;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author georgi.hristov@clouway.com
 */

public class SAXEmployeeTest {

  class Employee {
    private String firstName;

    public String getFirstName() {
      return firstName;
    }
  }


  interface EmployeeDataValidator {
    Boolean isValid();
  }

  class XMLEmployeeDataValidator implements EmployeeDataValidator {

    private SchemaFactory factory;
    private Validator validator;
    private Schema schema;
    private final Source schemaSource;
    private final Source sourceToValidate;

    public XMLEmployeeDataValidator(Source schemaSource, Source sourceToValidate) {

      this.schemaSource = schemaSource;

      this.sourceToValidate = sourceToValidate;

    }

    @Override
    public Boolean isValid() {
      Boolean isValid = false;
      try {
        factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        schema = factory.newSchema(schemaSource);
        validator = schema.newValidator();
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

  class EmployeeContentHandler extends DefaultHandler {

    private List<Employee> employees;

    public EmployeeContentHandler(List<Employee> employees) {
      super();
      this.employees = employees;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if (localName.equals("employee")) {
        employees.add(new Employee());
      }
    }
  }

  private Source schema;
  private String source;

  @Before
  public void setUp() {

    schema = new StreamSource(getClass().getResourceAsStream("schema.xsd"));
  }



  @Test
  public void createTwoEmployeeObjectsFromXML() throws SAXException, IOException {
    List<Employee> employees = Lists.newArrayList();
    XMLReader xmlReader = XMLReaderFactory.createXMLReader();
    xmlReader.setContentHandler(new EmployeeContentHandler(employees));
    xmlReader.parse(new InputSource(getClass().getResourceAsStream("validXML.xml")));
    assertThat(employees.size(), is(2));
  }

  @Test
  public void validatorReturnFalseOnInvalidXMLAccordingSchema() {

    source = "invalidXML.xml";

    assertFalse(validate(source));
  }

  @Test
  public void validatorReturnTrueOnValidXMLAccordingSchema() {

    source = "validXML.xml";

    assertTrue(validate(source));

  }

  private Boolean validate(String sourceName) {

    Source sourceToValidate = new StreamSource(getClass().getResourceAsStream(sourceName));

    XMLEmployeeDataValidator validator = new XMLEmployeeDataValidator(schema, sourceToValidate);

    return validator.isValid();

  }

}
