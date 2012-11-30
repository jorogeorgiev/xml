package task2;

import java.util.List;

/**
 * @author georgi.hristov@clouway.com
 */
public class Employee {

  private final List<String> personalData;
  private final List<Employer> employers;
  private final List<Address> addresses;

  public Employee(List<String> personalData, List<Employer> employers, List<Address> addresses) {
    this.personalData = personalData;
    this.employers = employers;
    this.addresses = addresses;
  }

  public void displayInformation() {
    StringBuilder wholeInforamtion = new StringBuilder();

    for (String data : personalData) {
      wholeInforamtion.append(data).append(' ');
    }
     wholeInforamtion.append('\n');
    for (Employer employer : employers) {
      wholeInforamtion.append(employer.getInformation()).append('\n');
    }
    for (Address address : addresses) {
      wholeInforamtion.append(address.getInformation()).append('\n');
    }

    System.out.println(wholeInforamtion.toString());

  }

}
