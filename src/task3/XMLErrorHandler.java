package task3;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

/**
 * @author georgi.hristov@clouway.com
 */
public class XMLErrorHandler implements ErrorHandler {
  private ErrorPrompt prompt;

  private ErrorMessage errorMessage;

  public XMLErrorHandler(ErrorPrompt prompt, ErrorMessage message) {

    this.prompt = prompt;

    this.errorMessage = message;

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
