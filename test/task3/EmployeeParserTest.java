package task3;


import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import sun.misc.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author georgi.hristov@clouway.com
 */
public class EmployeeParserTest {

  EmployeeDataContainer employees;

  @Before
  public void setUp() throws IOException, SAXException {

    employees = new EmployeeDataContainer();

    EmployeeParser parser = new EmployeeParser(employees);

    InputStream employeeData = EmployeeParserTest.class.getResourceAsStream("validXML.xml");

    XMLReader reader = XMLReaderFactory.createXMLReader();

    parser.parse(employeeData, reader);
  }



  interface DataContainer<InputType,OutputType> {

    void add(InputType data);

    OutputType get();

  }

  class EmployeeDataContainer implements  DataContainer<Employee,List<Employee>>{

     private List<Employee> employees = Lists.newArrayList();


    @Override
    public void add(Employee data) {

      employees.add(data);

    }

    @Override
    public List<Employee> get() {

      return  employees;

    }

    public Employee getElement(int index){

      return employees.get(index);

    }

  }

  private class EmployeeParser extends DefaultHandler {

    private DataContainer<Employee,List<Employee>> employeeContainer;

    private byte[] inputData;

    public EmployeeParser(DataContainer<Employee,List<Employee>> employeeContainer){

      this.employeeContainer = employeeContainer;

    }

    public void startDocument()  {

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if(qName.equals("employee")){
        employeeContainer.add(new Employee());
      }
    }


    public void parse(InputStream employeeData, XMLReader reader) throws IOException, SAXException {
      if(employeeData!=null){
        inputData = IOUtils.readFully(employeeData,Integer.MAX_VALUE,true);
      }
      reader.setContentHandler(this);
      reader.parse(new InputSource(new ByteArrayInputStream(inputData)));

    }


  }

  @Test
  public void parserReturnsEmployee() throws SAXException, IOException {

    assertThat(employees.get().size(), is(2));

  }


  @Test
  public void firstNameOfFirstEmployeeIsGeorge() throws SAXException, IOException {

    assertThat(employees.getElement(0).getFirstName(),is("Georgi"));

  }

}
