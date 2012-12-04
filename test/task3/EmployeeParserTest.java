package task3;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author georgi.hristov@clouway.com
 */

public class EmployeeParserTest {


  private InputStream schema;
  private InputStream validSource;
  private InputStream invalidSource;
  private ErrorPrompt errorPrompt;

  @Before
  public void setUp() throws SAXException {

    schema = getClass().getResourceAsStream("schema.xsd");
    schema.mark(0);
    validSource = getClass().getResourceAsStream("validXML.xml");
    validSource.mark(0);
    invalidSource = getClass().getResourceAsStream("invalidXML.xml");
    invalidSource.mark(0);
    errorPrompt = mock(ErrorPrompt.class);
  }

  @After
  public void tearDown() {
    try {
      schema.close();
      validSource.close();
      invalidSource.close();

    } catch (IOException e) {

    }
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
  public void promptNotifiesOnReadingErrorDuringValidation() throws IOException {
    InputStream fakedSource = mock(InputStream.class);
    EmployeeData data = new EmployeeData(schema,fakedSource, errorPrompt);
    doThrow(new IOException()).when(fakedSource).read();
    data.isDataValid();
    verify(errorPrompt,times(1)).prompt("Something happened during validation");

  }

  @Test
  public void employeeParserNotifiesOnReadingInvalidXML() throws SAXException {

    XMLReader reader = XMLReaderFactory.createXMLReader();
    EmployeeContentHandler employeeContentHandler = new EmployeeContentHandler();
    reader.setContentHandler(employeeContentHandler);
    EmployeeData data = new EmployeeData(schema, invalidSource, errorPrompt);
    EmployeeInformationParser parser = new EmployeeInformationParser(data, reader, errorPrompt);

    parser.parse();
    verify(errorPrompt, times(1)).prompt("Invalid Employee Data!");

  }

  @Test
  public void employeeParserNotifiesOnReadingError() throws SAXException, IOException {

    XMLReader reader = mock(XMLReader.class);
    EmployeeContentHandler employeeContentHandler = new EmployeeContentHandler();
    reader.setContentHandler(employeeContentHandler);
    EmployeeData data = new EmployeeData(schema, validSource, errorPrompt);
    EmployeeInformationParser parser = new EmployeeInformationParser(data, reader, errorPrompt);

    doThrow(new IOException()).when(reader).parse(new InputSource(validSource));
    parser.parse();
    verify(errorPrompt, times(1)).prompt("Error occurred while reading data!");

  }

  @Test
  public void employeeParserNotifiesOnParsingError() throws SAXException, IOException {

    XMLReader reader = mock(XMLReader.class);
    EmployeeContentHandler employeeContentHandler = new EmployeeContentHandler();
    reader.setContentHandler(employeeContentHandler);
    EmployeeData data = new EmployeeData(schema, validSource, errorPrompt);
    EmployeeInformationParser parser = new EmployeeInformationParser(data, reader, errorPrompt);

    doThrow(new SAXException()).when(reader).parse(new InputSource(validSource));
    parser.parse();
    verify(errorPrompt, times(1)).prompt("Error occurred while parsing data!");



  }


  private Boolean validate(InputStream source) {

    EmployeeData container = new EmployeeData(schema, source, errorPrompt);

    return container.isDataValid();

  }

}
