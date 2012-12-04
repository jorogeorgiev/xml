package task3;

import java.io.IOException;

/**
 * @author georgi.hristov@clouway.com
 */
public interface DataContainer<T> {

  Boolean isDataValid();

  T getData() throws IOException;

}
