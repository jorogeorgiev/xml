package task3;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author georgi.hristov@clouway.com
 */
public class SAXEmployeeTest {

  class Employee {

    private List<String> personalInformation;
    private List<Employer> employers;
    private List<Address> address;

    public void addPersonalInformation(List<String> personalInformation) {
      this.personalInformation = personalInformation;
    }

    public void addEmployerInformation(List<Employer> employers) {
      this.employers = employers;
    }

    public void addAddressInformation(List<Address> address) {
      this.address = address;
    }

    public void printInfo() {
      for (String data : personalInformation) {
        System.out.println(data + " ");
      }
      for (Employer employer : employers) {
        employer.pritnInfo();
      }
      for (Address addr : address) {
        addr.printInfo();
      }


    }

  }

  class Employer {

    private List<String> employersInformation;

    public void addEmployersInformation(List<String> employersInformation) {

      this.employersInformation = employersInformation;
    }

    public void pritnInfo() {
      for (String data : employersInformation) {

        System.out.println(data + " ");

      }
    }

  }

  class Address {

    private List<String> addressInformation;

    public void addAddressInformation(List<String> addressInformation) {

      this.addressInformation = addressInformation;
    }

    public void printInfo() {
      for (String data : addressInformation) {

        System.out.println(data + " ");

      }

    }

  }

  class EmployeesInformationHandler extends DefaultHandler {


    Map<String, Boolean> attributeLocks = Maps.newHashMap();

    List<String> employeePersonalInformation;
    Map<Integer, Employee> employees = Maps.newHashMap();
    private int employeeIndex = 1;

    List<String> employersInformation;
    List<Employer> employerList;
    Map<Integer, Employer> employers = Maps.newHashMap();
    private int employersIndex = 1;

    List<Address> addressList;
    List<String> addressInformation;
    Map<Integer, Address> addresses = Maps.newHashMap();
    private int addressesIndex = 1;

    public EmployeesInformationHandler() {

      super();

    }

    public void startDocument() {
      attributeLocks.put("employee", false);
      attributeLocks.put("employer", false);
      attributeLocks.put("address", false);
    }

    public void endDocument() {
    }

    public void startElement(String uri, String name, String qName, Attributes atts) {

      if (name.equals("employee")) {
        createEmployee();
        attributeLocks.put(name, true);
      }

      if (name.equals("employer")) {
        createEmployer();
        attributeLocks.put(name, true);
      }

      if (name.equals("address")) {
        createAddress();
        attributeLocks.put(name, true);
      }

    }

    private void createEmployee() {
      employeePersonalInformation = Lists.newArrayList();
      employees.put(employeeIndex, new Employee());
      employeeIndex++;
    }

    private void createEmployer() {
      employerList = Lists.newArrayList();
      employersInformation = Lists.newArrayList();
      employers.put(employersIndex, new Employer());
      employersIndex++;
    }

    private void createAddress() {
      addressList = Lists.newArrayList();
      addressInformation = Lists.newArrayList();
      addresses.put(addressesIndex, new Address());
      addressesIndex++;
    }

    public void endElement(String uri, String name, String qName) {
    }

    public void characters(char ch[], int start, int length) {
      StringBuilder builder = new StringBuilder();
      for (int i = start; i < start + length; i++) {
        builder.append(ch[i]);
      }
      for (Map.Entry<String, Boolean> locks : attributeLocks.entrySet()) {


        if (locks.getValue()) {

          if (locks.getKey().equals("employee")) {
            employeePersonalInformation.add(builder.toString());
            employees.get(employeeIndex - 1).addPersonalInformation(employeePersonalInformation);
          }

          if (locks.getKey().equals("employer")) {
            employersInformation.add(builder.toString());
            employers.get(employersIndex - 1).addEmployersInformation(employersInformation);
            employees.get(employeeIndex - 1).addEmployerInformation(employerList);
          }

          if (locks.getKey().equals("address")) {
            addressInformation.add(builder.toString());
            addresses.get(addressesIndex - 1).addAddressInformation(addressInformation);
            employees.get(employeeIndex - 1).addAddressInformation(addressList);
          }


        }

      }


    }


    public void printEmployesInformation() {
      for (Employee employee : employees.values()) {
        employee.printInfo();
        System.out.println("<---------END OF USERS INFO--------->");
      }
    }

  }


  @Test
  public void playingWithSAX() throws SAXException, ParserConfigurationException, IOException {
    XMLReader reader = XMLReaderFactory.createXMLReader();
    EmployeesInformationHandler handler = new EmployeesInformationHandler();
    reader.setContentHandler(handler);
    reader.setErrorHandler(handler);
    reader.parse(new InputSource(ClassLoader.getSystemResourceAsStream("employees.xml")));
    handler.printEmployesInformation();
  }


}
