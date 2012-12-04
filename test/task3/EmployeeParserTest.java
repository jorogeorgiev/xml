package task3;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author georgi.hristov@clouway.com
 */

public class EmployeeParserTest {


  private InputStream schema;
  private InputStream validSource;
  private InputStream invalidSource;

  @Before
  public void setUp() throws SAXException {

     schema = getClass().getResourceAsStream("schema.xsd");
     validSource = getClass().getResourceAsStream("validXML.xml");
     invalidSource = getClass().getResourceAsStream("invalidXML.xml");

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
    ErrorPrompt errorPrompt = mock(ErrorPrompt.class);
    XMLReader reader = XMLReaderFactory.createXMLReader();
    EmployeeContentHandler employeeContentHandler = new EmployeeContentHandler();
    reader.setContentHandler(employeeContentHandler);
    EmployeeDataContainer data = new EmployeeDataContainer(schema,invalidSource);
    EmployeeInformationParser parser = new EmployeeInformationParser(data,reader,errorPrompt);
    parser.parse();
    verify(errorPrompt, times(1)).prompt("Invalid Employee Data!");

  }

  @Test
  public void employeeParserNotifiesOnIOError() throws SAXException, IOException {
    ErrorPrompt errorPrompt = mock(ErrorPrompt.class);
    XMLReader reader = mock(XMLReader.class);
    EmployeeContentHandler employeeContentHandler = new EmployeeContentHandler();
    reader.setContentHandler(employeeContentHandler);
    EmployeeDataContainer data = new EmployeeDataContainer(schema,validSource);
    EmployeeInformationParser parser = new EmployeeInformationParser(data,reader,errorPrompt);
    parser.parse();
    doThrow(new IOException()).when(reader).parse(new InputSource(invalidSource));
    verify(errorPrompt, times(1)).prompt("Error occurred while reading data!");

  }




  private Boolean validate(InputStream source) {

    EmployeeDataContainer container = new EmployeeDataContainer(schema, source);

    return container.isDataValid();

  }

}
