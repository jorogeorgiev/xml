package task3;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.util.ArrayList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author georgi.hristov@clouway.com
 */

public class EmployeeParserTest {


  private Source schema;
  private Source validSource;
  private Source invalidSource;

  @Before
  public void setUp() throws SAXException {

     schema = new StreamSource(getClass().getResourceAsStream("schema.xsd"));
     validSource = new StreamSource(getClass().getResourceAsStream("validXML.xml"));
     invalidSource = new StreamSource(getClass().getResourceAsStream("invalidXML.xml"));

  }


  @Test
  public void validatorReturnFalseOnInvalidXMLAccordingSchema() {

    assertFalse(validate(invalidSource));

  }


  @Test
  public void validatorReturnTrueOnValidXMLAccordingSchema() {

    assertTrue(validate(validSource));

  }

  @Test
  public void employeeParserNotifiesOnReadingInvalidXML() throws SAXException {
    ErrorPrompt prompt = mock(ErrorPrompt.class);
    XMLReader reader = XMLReaderFactory.createXMLReader();
    EmployeeContentHandler employeeContentHandler = new EmployeeContentHandler();
    reader.setContentHandler(employeeContentHandler);
    EmployeeDataValidator validator = new EmployeeDataValidator(schema,invalidSource);
    EmployeeInformationParser parser = new EmployeeInformationParser(validator,prompt,reader,new ArrayList<Employee>());
    parser.parse();
    verify(prompt, times(1)).prompt("Invalid Employee Data");

  }


  private Boolean validate(Source source) {

    EmployeeDataValidator validator = new EmployeeDataValidator(schema, source);

    return validator.isDataValid();

  }

}
