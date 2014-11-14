package models.rocketmodel.data;



import java.io.InputStream;
import java.io.OutputStream;
import models.rocketmodel.Rocket;

/**
 *
 * @author Jacob
 */
public interface IRocketSerializer {

  void serialize(Rocket rocket, OutputStream outputStream) throws Exception;

  Rocket deserialize(InputStream inputStream) throws Exception;
}
