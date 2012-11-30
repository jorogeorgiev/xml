package task2;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author georgi.hristov@clouway.com
 */

public class EmployeeTest {


  private Document document;


  @Before
  public void setUp() throws ParserConfigurationException, IOException, SAXException {

    DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
    documentFactory.setIgnoringElementContentWhitespace(true);
    DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
    document = documentBuilder.parse(ClassLoader.getSystemResourceAsStream("employees.xml"));

  }

  @Test
  public void validatesDom() throws SAXException {

    Source xmlSchemaSource = new StreamSource(ClassLoader.getSystemResourceAsStream("employees.xsd"));

    assertTrue(new XMLValidator().validate(document, xmlSchemaSource));

  }

  @Test
  public void getsEmployersFromTheDomTree() {

    List<Employee> employees = Lists.newArrayList();

    EmployeeParser employeeParser ;

    NodeList employeeNodes = document.getChildNodes().item(1).getChildNodes();

    for (int i = 0 ; i < employeeNodes.getLength() ; i++) {

      employeeParser = new EmployeeParser(employeeNodes);

      employees.add(employeeParser.parse(i));

    }
    assertThat(employees.size(),is(employeeNodes.getLength()));
  }



}
