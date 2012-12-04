package task3;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;

/**
 * @author georgi.hristov@clouway.com
 */
public class EmployeeInformationParser {

  private final Data<InputSource> data;
  private final ErrorPrompt prompt;
  private final XMLReader reader;


  public EmployeeInformationParser(Data<InputSource> data, XMLReader reader, ErrorPrompt prompt) {

    this.data = data;
    this.prompt = prompt;
    this.reader = reader;
  }

  public void parse() {

    if (data.isDataValid()) {
      try {
        reader.parse(data.getData());
      } catch (IOException e) {
        prompt.prompt("Error occurred while reading data!");
      } catch (SAXException e) {
        prompt.prompt("Error occurred while parsing data!");
      }

    } else {
      prompt.prompt("Invalid Employee Data!");
    }


  }


}
