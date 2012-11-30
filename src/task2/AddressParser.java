package task2;

import org.w3c.dom.NodeList;

import java.util.List;

/**
 * @author georgi.hristov@clouway.com
 */
public class AddressParser implements AttributeParser<List<Address>>{

  private NodeList attributesList;
  private List<Address> addresses;

  public AddressParser(NodeList attributesList, List<Address> addresses){
    this.attributesList = attributesList;
    this.addresses = addresses;
  }

  @Override
  public List<Address> parse(Integer index) {
    NodeList employersInfo = attributesList.item(index).getChildNodes();
    addresses.add(new Address(employersInfo.item(0).getTextContent(),Integer.valueOf(employersInfo.item(1).getTextContent()),employersInfo.item(2).getTextContent()));
    return addresses;
  }
}