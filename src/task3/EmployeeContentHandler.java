package task3;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


import java.util.List;

/**
 * @author georgi.hristov@clouway.com
 */
public class EmployeeContentHandler extends DefaultHandler {


  public EmployeeContentHandler() {
    super();
  }


  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

  }
}
