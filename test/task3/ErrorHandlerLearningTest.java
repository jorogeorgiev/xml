package task3;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author georgi.hristov@clouway.com
 */
public class ErrorHandlerLearningTest {

  ErrorPrompt prompt;
  ErrorMessages message;
  MyErrorHandler handler;
  InputStream xmlData ;

  @Before
  public void setUp(){
    prompt = mock(ErrorPrompt.class);
    message = new ErrorMessages();
    handler = new MyErrorHandler(prompt, message);
    xmlData = ErrorHandlerLearningTest.class.getResourceAsStream("invalidXML.xml");
  }



  interface ErrorPrompt {

    void prompt(String message);

  }

  private class ErrorMessages {

    public String onWarning(Integer line, Integer column, String message) {

      return "Warning on line:" + line + " and column:" + column + " error: " + message;

    }

    public String onError(Integer line, Integer column, String message) {

      return "Error on line:" + line + " and column:" + column + " error: " + message;

    }

    public String onCriticalError(Integer line, Integer column, String message) {

      return "Critical error on line:" + line + " and column:" + column + " error: " + message;

    }

  }

  private class MyErrorHandler implements ErrorHandler {

    private ErrorPrompt prompt;

    private ErrorMessages errorMessage;

    public MyErrorHandler(ErrorPrompt prompt, ErrorMessages messages) {

      this.prompt = prompt;

      this.errorMessage = messages;

    }


    @Override
    public void warning(SAXParseException exception) {

      prompt.prompt(errorMessage.onWarning(exception.getLineNumber(), exception.getColumnNumber(), exception.getMessage()));

    }

    @Override
    public void error(SAXParseException exception) {

      prompt.prompt(errorMessage.onError(exception.getLineNumber(), exception.getColumnNumber(), exception.getMessage()));

    }

    @Override
    public void fatalError(SAXParseException exception) {

      prompt.prompt(errorMessage.onCriticalError(exception.getLineNumber(), exception.getColumnNumber(), exception.getMessage()));

    }

  }

  @Test
  public void errorHandlerNotifiesOnTagErrorDuringParsing() throws IOException {

    parseXML(xmlData);

    verify(prompt, times(1)).prompt(message.onCriticalError(32, 25, "The element type \"fistName\" must be terminated by the matching end-tag \"</fistName>\"."));
  }

  @Test
  public void errorHandlerNotifiesOnInvalidSequenceDuringValidation() throws SAXException, IOException {

    validateXML(xmlData);

    verify(prompt, times(1)).prompt(message.onError(18, 15, "cvc-complex-type.2.4.a: Invalid content was found starting with element 'position'. One of '{endDate}' is expected."));


  }

  private void parseXML(InputStream xmlStream) {
    try {
      XMLReader reader = XMLReaderFactory.createXMLReader();
      reader.setErrorHandler(handler);
      reader.parse(new InputSource(xmlStream));
    } catch (SAXException e) {
    } catch (IOException e) {
    }
  }

  private void validateXML(InputStream xmlStream) {
    try {
      Validator validator = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new StreamSource(ErrorHandlerLearningTest.class.getResourceAsStream("schema.xsd"))).newValidator();
      validator.setErrorHandler(handler);
      validator.validate(new StreamSource(xmlStream));
    } catch (SAXException e) {
    } catch (IOException e) {
    }
  }


}
