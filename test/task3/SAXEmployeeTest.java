package task3;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author georgi.hristov@clouway.com
 */

public class SAXEmployeeTest {

  class Employee {
  }

  class EmployeeContentHandler extends DefaultHandler {

    private List<Employee> employees;

    public EmployeeContentHandler(List<Employee> employees){
      super();
      this.employees = employees;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
     if(localName.equals("employee")){
       employees.add(new Employee());
     }
    }
  }

  @Test
  public void createTwoEmployeeObjectsFromXML() throws SAXException, IOException {
    List<Employee> employees = Lists.newArrayList();
    XMLReader xmlReader = XMLReaderFactory.createXMLReader();
    xmlReader.setContentHandler(new EmployeeContentHandler(employees));
    xmlReader.parse(new InputSource(ClassLoader.getSystemResourceAsStream("employees.xml")));
    assertThat(employees.size(), is(2));
  }

}
