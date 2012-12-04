package task3;

/**
 * @author georgi.hristov@clouway.com
 */
public interface DataContainer<T> {

  Boolean isDataValid();

  T getData();

}
