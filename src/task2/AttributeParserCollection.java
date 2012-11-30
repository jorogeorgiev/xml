package task2;

import com.google.common.collect.Maps;
import org.w3c.dom.NodeList;

import java.util.List;
import java.util.Map;

public class AttributeParserCollection {


    Map<String, AttributeParser> attributeParserMap = Maps.newHashMap();

    public AttributeParserCollection(NodeList attributes, List<Employer> employers, List<Address> addresses, List<String> personalInfo) {

      this.attributeParserMap.put("firstName", new FirstNameParser(attributes, personalInfo));
      this.attributeParserMap.put("lastName", new LastNameParser(attributes, personalInfo));
      this.attributeParserMap.put("age", new AgeParser(attributes, personalInfo));
      this.attributeParserMap.put("employer", new EmployerParser(attributes, employers));
      this.attributeParserMap.put("address", new AddressParser(attributes, addresses));

    }

    public AttributeParser useParserFor(String attribute) {
      return attributeParserMap.get(attribute);
    }
  }