package models.rocket.data;

import java.io.InputStream;
import java.io.OutputStream;
import models.rocket.Rocket;

/**
 * Serializes and deserializes a rocket for interoperability with other programs
 * @author Jacob
 */
public interface IRocketSerializer {

  /**
   * Serializes a rocket to the specified output stream
   * @param rocket The rocket to serialize
   * @param outputStream The stream to write to
   * @throws Exception
   */
  void serialize(Rocket rocket, OutputStream outputStream) throws Exception;

  /**
   * Deserializes a rocket from the specified input stream
   * @param inputStream The stream to read from
   * @return Returns the deserialized Rocket
   * @throws Exception
   */
  Rocket deserialize(InputStream inputStream) throws Exception;
}
