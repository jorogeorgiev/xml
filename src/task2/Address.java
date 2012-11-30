package task2;

/**
 * @author georgi.hristov@clouway.com
 */
public class Address {

  private final String street;
  private final Integer number;
  private final String city;

  public Address(String street , Integer number , String city){
    this.street = street;
    this.number = number;
    this.city = city;
  }

  public String getInformation(){
    //sort of implementation
   return street + " "  + number + " " + city;
  }

}
