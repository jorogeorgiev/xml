package task3;

import org.junit.Test;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author georgi.hristov@clouway.com
 */
public class ErrorHandlerLearningTest {

  interface ErrorPrompt {

    void promptError(String message);

  }

  private class ErrorMessages {

    public String onWarning(Integer line, Integer column, String message) {

      return "Warning on line: " + line + " and column: " + column + "warning: " + message;

    }

    public String onError(Integer line, Integer column, String message) {

      return "Error on line: " + line + " and column: " + column + "warning: " + message;

    }

    public String onCriticalError(Integer line, Integer column, String message) {

      return "Critical error on line: " + line + " and column: " + column + "warning: " + message;

    }

  }

  private class MyErrorHandler implements ErrorHandler {

    private ErrorPrompt prompt;

    private ErrorMessages messages;

    public MyErrorHandler(ErrorPrompt prompt, ErrorMessages messages) {

      this.prompt = prompt;

      this.messages = messages;

    }


    @Override
    public void warning(SAXParseException exception){

      prompt.promptError(messages.onWarning(exception.getLineNumber(), exception.getColumnNumber(), exception.getMessage()));

    }

    @Override
    public void error(SAXParseException exception){

      prompt.promptError(messages.onError(exception.getLineNumber(), exception.getColumnNumber(), exception.getMessage()));

    }

    @Override
    public void fatalError(SAXParseException exception){

      prompt.promptError(messages.onCriticalError(exception.getLineNumber(), exception.getColumnNumber(), exception.getMessage()));

    }

  }

  @Test
  public void errorHandlerNotifiesOnTagErrorDuringParsing() throws IOException, SAXException {

    ErrorPrompt prompt = mock(ErrorPrompt.class);
    ErrorMessages message = new ErrorMessages();
    MyErrorHandler handler = new MyErrorHandler(prompt, message);
    XMLReader reader = XMLReaderFactory.createXMLReader();
    reader.setErrorHandler(handler);

    reader.parse(new InputSource(ErrorHandlerLearningTest.class.getResourceAsStream("invalidXML.xml")));
     verify(prompt,times(1)).promptError(message.onCriticalError(20,6,"The element type \"employer\" must be terminated by the matching end-tag \"</employer>\"."));


  }


}
