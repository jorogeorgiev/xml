package task3;


import com.google.common.collect.Lists;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import sun.misc.IOUtils;


import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author georgi.hristov@clouway.com
 */
public class EmployeeParserTest {


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

    ErrorPrompt prompt  = mock(ErrorPrompt.class);

    ErrorHandler handler = new XMLErrorHandler(prompt,new ErrorMessage());

    DataContainer<Employee,List<Employee>> employees = new EmployeeDataContainer();

    EmployeeParser parser = new EmployeeParser(employees);

    InputStream employeeData = EmployeeParserTest.class.getResourceAsStream("validXML.xml");

    XMLReader reader = XMLReaderFactory.createXMLReader();

    reader.setErrorHandler(handler);

    parser.parse(employeeData ,reader);

    assertThat(employees.get().size(), is(2));

  }


}
