package task3;

import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import java.io.IOException;

/**
 * @author georgi.hristov@clouway.com
 */
public class SAXEmployeeTest {

  class SAXDefaultHandler extends DefaultHandler {



    public SAXDefaultHandler() {

      super();

    }

    public void startDocument() {

      System.out.println("START OF THE HELL");

    }

    public void endDocument() {

      System.out.println("STILL NOT THERE YET");

    }

    public void startElement(String uri, String name, String qName, Attributes atts) {



    }

    public void endElement(String uri, String name, String qName) {
    }

    public void characters (char ch[], int start, int length)
    {
      String variable="";

      for (int i = start; i < start + length; i++) {

           variable = variable+ch[i];

      }
      System.out.println(variable);
    }


  }


  @Test
  public void playingWithSAX() throws SAXException, ParserConfigurationException, IOException {
    XMLReader reader = XMLReaderFactory.createXMLReader();
    SAXDefaultHandler handler = new SAXDefaultHandler();
    reader.setContentHandler(handler);
    reader.setErrorHandler(handler);
    reader.parse(new InputSource(ClassLoader.getSystemResourceAsStream("employees.xml")));

  }


}
