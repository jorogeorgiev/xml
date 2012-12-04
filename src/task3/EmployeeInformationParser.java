package task3;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;

/**
 * @author georgi.hristov@clouway.com
 */
public class EmployeeInformationParser {

  private final DataContainer<InputSource> dataContainer;
  private final ErrorPrompt prompt;
  private final XMLReader reader;


  public EmployeeInformationParser(DataContainer<InputSource> dataContainer,XMLReader reader ,ErrorPrompt prompt) {

    this.dataContainer = dataContainer;
    this.prompt = prompt;
    this.reader = reader;
  }

  public void parse(){

    if(dataContainer.isDataValid()){
      try {
        reader.parse(dataContainer.getData());
      } catch (IOException e) {
        prompt.prompt("Error occurred while reading data!");
      } catch (SAXException e) {
        prompt.prompt("Error occurred while parsing data!");
      }
    } else{
      prompt.prompt("Invalid Employee Data!");
    }


  }


}
