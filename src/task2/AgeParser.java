package task2;

import org.w3c.dom.NodeList;

import java.util.List;

/**
 * @author georgi.hristov@clouway.com
 */
public class AgeParser implements  AttributeParser<List<String>>{
  private NodeList attributesList;
  private List<String> personalInfo;

  public  AgeParser(NodeList attributesList , List<String> personalInfo){
    this.attributesList = attributesList;
    this.personalInfo = personalInfo;
  }

  @Override
  public List<String> parse(Integer index) {
    personalInfo.add(attributesList.item(index).getTextContent());
    return personalInfo;
  }

}

