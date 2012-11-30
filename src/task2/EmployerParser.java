package task2;

import org.w3c.dom.NodeList;

import java.util.List;

/**
 * @author georgi.hristov@clouway.com
 */
public class EmployerParser implements AttributeParser<List<Employer>>{

  private NodeList attributesList;
  private List<Employer> employers;

  public EmployerParser(NodeList attributesList,List<Employer> employers){
    this.attributesList = attributesList;
    this.employers = employers;
  }

  @Override
  public List<Employer> parse(Integer index) {
    NodeList employersInfo = attributesList.item(index).getChildNodes();
    employers.add(new Employer(employersInfo.item(0).getTextContent(),employersInfo.item(1).getTextContent(),employersInfo.item(2).getTextContent(),employersInfo.item(3).getTextContent()));
    return employers;
  }
}