package task3;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author georgi.hristov@clouway.com
 */
public class SAXEmployeeTest {

  class Employee {
    StringBuffer information = new StringBuffer();


    public void addInformation(String newInformation) {

      information.append(newInformation).append(" ");

    }

  }

  class EmployeesInformationHandler extends DefaultHandler {

    List<Employee> employees = Lists.newArrayList();
    private int index = 0;

    public EmployeesInformationHandler() {

      super();

    }

    public void startDocument() { }

    public void endDocument() {}

    public void startElement(String uri, String name, String qName, Attributes atts) {

      if (name.equals("employee") && index == 0) {
        employees.add(new Employee());
      } else if(name.equals("employee")){
        employees.add(new Employee());
        index++;
      }

    }

    public void endElement(String uri, String name, String qName) {
    }

    public void characters(char ch[], int start, int length) {

      Employee employee = employees.get(index);
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = start; i < start + length; i++) {
        stringBuilder.append(ch[i]);

      }
      employee.addInformation(stringBuilder.toString());
    }

    public List<Employee> getEmployees(){

      return  employees;

    }

  }


  @Test
  public void playingWithSAX() throws SAXException, ParserConfigurationException, IOException {
    XMLReader reader = XMLReaderFactory.createXMLReader();
    EmployeesInformationHandler handler = new EmployeesInformationHandler();
    reader.setContentHandler(handler);
    reader.setErrorHandler(handler);
    reader.parse(new InputSource(ClassLoader.getSystemResourceAsStream("employees.xml")));
    List<Employee> employees = handler.getEmployees();
    assertThat(employees.size(),is(2)) ;


  }


}
