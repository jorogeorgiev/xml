package task3;

/**
 * @author georgi.hristov@clouway.com
 */
public class ErrorMessage {

  public String onWarning(Integer line, Integer column, String message) {

    return "Warning on line:" + line + " and column:" + column + " error: " + message;

  }

  public String onError(Integer line, Integer column, String message) {

    return "Error on line:" + line + " and column:" + column + " error: " + message;

  }

  public String onCriticalError(Integer line, Integer column, String message) {

    return "Critical error on line:" + line + " and column:" + column + " error: " + message;

  }
}
