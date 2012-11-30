package task2;

import com.google.common.collect.Lists;
import org.w3c.dom.NodeList;

import java.util.List;

/**
 * @author georgi.hristov@clouway.com
 */
public class EmployeeParser{

  private NodeList employees;

  public EmployeeParser(NodeList employees){

    this.employees = employees;
  }


  public Employee parse(Integer index) {

    NodeList employeeAttributes = employees.item(index).getChildNodes();
    List<Employer> employers = Lists.newArrayList();
    List<Address> addresses = Lists.newArrayList();
    List<String> personalInfo = Lists.newArrayList();
    for(int i = 0 ; i < employeeAttributes.getLength() ; i++){
      AttributeParserCollection attributeParserCollection = new AttributeParserCollection(employeeAttributes,employers,addresses,personalInfo);
      String attributeName = employeeAttributes.item(i).getNodeName();
      attributeParserCollection.useParserFor(attributeName).parse(i);
    }
    return new Employee(personalInfo,employers,addresses);

  }


}
