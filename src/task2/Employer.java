package task2;

/**
 * @author georgi.hristov@clouway.com
 */
public class Employer {

  private String name;
  private String startDate;
  private String endDate;
  private String position;

  public Employer(String name , String startDate , String endDate , String position){
    this.name = name;
    this.startDate = startDate;
    this.endDate = endDate;
    this.position = position;
  }

  public String getInformation(){
    return name + " " + startDate + " " + endDate + " " + position;
  }

}
