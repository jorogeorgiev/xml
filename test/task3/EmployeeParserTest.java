package task3;


import com.google.common.collect.Lists;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.ignoreStubs;
import static org.mockito.Mockito.mock;

/**
 * @author georgi.hristov@clouway.com
 */
public class EmployeeParserTest {

  private class EmployeeParser {
    public List<Employee> parse(InputStream employeeData) {
      return Lists.newArrayList();
    }
  }

  @Test
  public void parserReturnsEmployee() {


    EmployeeParser parser = new EmployeeParser();

    InputStream employeeData = EmployeeParserTest.class.getResourceAsStream("valid.xml");

    List<Employee> employees = parser.parse(employeeData);

    assertThat(employees.size(),is(2));

  }


}
