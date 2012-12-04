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

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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


  class EmployeeContentHandler extends DefaultHandler {

    private List<Employee> employees;
    private EmployeeDataValidator validator;

    public EmployeeContentHandler(List<Employee> employees, EmployeeDataValidator validator) {
      super();
      this.employees = employees;
      this.validator = validator;
    }

    public void startDocument(){

      validator.isXMLValid();

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
  public void validatorReturnFalseOnInvalidXMLAccordingSchema() {

    source = "invalidXML.xml";

    assertFalse(validate(source));
  }

  @Test
  public void validatorReturnTrueOnValidXMLAccordingSchema() {

    source = "validXML.xml";

    assertTrue(validate(source));

  }

  @Test
  public void handlerInvokesEmployeeDataValidatorOnDocumentStart() throws SAXException, IOException {
    List<Employee> employees = Lists.newArrayList();
    EmployeeDataValidator validator = mock(EmployeeDataValidator.class);
    XMLReader xmlReader = XMLReaderFactory.createXMLReader();
    xmlReader.setContentHandler(new EmployeeContentHandler(employees, validator));
    xmlReader.parse(new InputSource(getClass().getResourceAsStream(this.source="validXML.xml")));
    verify(validator, times(1)).isXMLValid();
  }

  @Test
  public void createTwoEmployeeObjectsFromXML() throws SAXException, IOException {
    List<Employee> employees = Lists.newArrayList();
    XMLReader xmlReader = XMLReaderFactory.createXMLReader();
    xmlReader.setContentHandler(new EmployeeContentHandler(employees, new XMLEmployeeDataValidator(new StreamSource(getClass().getResourceAsStream(this.source = "validXML.xml")), schema)));
    xmlReader.parse(new InputSource(getClass().getResourceAsStream(this.source = "validXML.xml")));
    assertThat(employees.size(), is(2));
  }


  private Boolean validate(String sourceName) {

    Source sourceToValidate = new StreamSource(getClass().getResourceAsStream(sourceName));

    XMLEmployeeDataValidator validator = new XMLEmployeeDataValidator(schema, sourceToValidate);

    return validator.isXMLValid();

  }

}
