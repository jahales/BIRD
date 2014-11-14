package models.rocket.data;



import java.io.InputStream;
import java.io.OutputStream;
import models.rocketmodel.Rocket;

/**
 * Serializes and deserializes a rocket for interoperability with other programs
 * @author Jacob
 */
public interface IRocketSerializer {

  /**
   * Serializes a rocket to the specified output stream
   * @param rocket
   * @param outputStream
   * @throws Exception
   */
  void serialize(Rocket rocket, OutputStream outputStream) throws Exception;

  /**
   * Deserializes a rocket from the specified input stream
   * @param inputStream
   * @return
   * @throws Exception
   */
  Rocket deserialize(InputStream inputStream) throws Exception;
}
