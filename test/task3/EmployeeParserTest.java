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
import java.util.ArrayList;
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


  interface DataContainer<InputType, OutputType> {

    void add(InputType data);

    OutputType get();

  }

  class EmployeeDataContainer implements DataContainer<Employee, List<Employee>> {

    private List<Employee> employees = Lists.newArrayList();


    @Override
    public void add(Employee data) {

      employees.add(data);

    }

    @Override
    public List<Employee> get() {

      return employees;

    }

    public Employee getEmployee(int index) {

      return employees.get(index);

    }

  }














  private class EmployeeParser extends DefaultHandler {

    private DataContainer<Employee, List<Employee>> employeeContainer;

    private byte[] inputData;
    private List<String> firstName=new ArrayList<String>();
    private List<String> lastName= new ArrayList<String>();
    private boolean firstNameFlag = false;
    private boolean lastNameFlag = false;

    public EmployeeParser(DataContainer<Employee, List<Employee>> employeeContainer) {

      this.employeeContainer = employeeContainer;

    }

    public void startDocument() {

    }

    @Override
    public void endDocument() throws SAXException {
      for(int i =0;i<firstName.size();i++){
        employeeContainer.add(new Employee(firstName.get(i),lastName.get(i)));
      }


    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if (localName.equals("firstName")) {
        firstNameFlag = true;
      }
      if(localName.equals("lastName")){
        lastNameFlag = true;
      }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
      if(localName.equals("firstName")) {
        firstNameFlag = false;
      }
      if(localName.equals("lastName")){
        lastNameFlag = false;
      }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
      StringBuilder builder = new StringBuilder();
      if (firstNameFlag) {
        for (int i = start; i < start + length; i++) {
          builder.append(ch[i]);
        }
        firstName.add(builder.toString());
        builder.delete(0,builder.length());
      }
      if(lastNameFlag) {
        for (int i = start; i < start + length; i++) {
          builder.append(ch[i]);
        }
        lastName.add(builder.toString());
        builder.delete(0,builder.length());
      }
    }


    public void parse(InputStream employeeData, XMLReader reader) throws IOException, SAXException {
      if (employeeData != null) {
        inputData = IOUtils.readFully(employeeData, Integer.MAX_VALUE, true);
      }
      reader.setContentHandler(this);
      reader.parse(new InputSource(new ByteArrayInputStream(inputData)));

    }


  }

  @Test
  public void parserReturnsEmployee() throws SAXException, IOException {

    final Integer totalEmployees = 3;

    assertThat(employees.get().size(), is(totalEmployees));

  }


  @Test
  public void firstNameOfFirstEmployeeIsGeorge() throws SAXException, IOException {

    final Integer first = 0;

    assertThat(employees.getEmployee(first).getFirstName(), is("Georgi"));

  }

  @Test
  public void firstNameOfSecondEmployeeIsGrisha() throws SAXException, IOException {

    final Integer second = 1;

    assertThat(employees.getEmployee(second).getFirstName(), is("Grisha"));

  }

  @Test
  public void firstNameOfThirdEmployeeIsGrigor() throws SAXException, IOException {

    final Integer third = 2;

    assertThat(employees.getEmployee(third).getFirstName(), is("Grigor"));

  }


  //will skip the traditional hardcoded return principle and we will try the real implementation.

  @Test
  public void lastNameOfFirstEmployeeIsGeorgiev(){

    final Integer first = 0;

    assertThat(employees.getEmployee(first).getLastName(),is("Georgiev"));

  }

  @Test
  public void lastNameOfSecondEmployeeIsAngelov(){
     Integer second = 1;

    assertThat(employees.getEmployee(second).getLastName(),is("Angelov"));

  }


}
