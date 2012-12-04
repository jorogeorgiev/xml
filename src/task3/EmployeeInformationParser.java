package task3;

import org.xml.sax.XMLReader;

import java.util.List;

/**
 * @author georgi.hristov@clouway.com
 */
public class EmployeeInformationParser {

  private final DataValidator validator;
  private final ErrorPrompt prompt;
  private final XMLReader reader;

  public EmployeeInformationParser(DataValidator validator, ErrorPrompt prompt , XMLReader reader , List<Employee> employeeList) {

    this.validator = validator;
    this.prompt = prompt;
    this.reader = reader;
  }

  public void parse(){

  }


}
