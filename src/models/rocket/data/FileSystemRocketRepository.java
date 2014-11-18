package models.rocket.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import models.ApplicationException;
import models.rocket.Rocket;

/**
 *
 * @author Jacob
 */
public class FileSystemRocketRepository implements IRocketRepository {

  String folder;
  IRocketSerializer serializer;

  /**
   *
   * @param folder
   * @param serializer
   */
  public FileSystemRocketRepository(String folder, IRocketSerializer serializer) {
    this.folder = folder;
    this.serializer = serializer;
  }

  /**
   *
   * @param rocket
   * @throws models.ApplicationException
   */
  @Override
  public void Create(Rocket rocket) throws ApplicationException {
    String fileName = Paths.get(folder, rocket.getName() + serializer.getDefaultExtension()).toString();
    
    try (OutputStream stream = new FileOutputStream(fileName))
    {
      serializer.serialize(rocket, stream);
    } catch (Exception ex) {
      throw new ApplicationException("Unable to create the specified rocket.", ex);
    }
  }

  /**
   *
   * @param id
   * @return
   * @throws models.ApplicationException
   */
  @Override
  public Rocket Retrieve(String id) throws ApplicationException {
    String fileName = Paths.get(folder, id + serializer.getDefaultExtension()).toString();
    
     try (InputStream stream = new FileInputStream(fileName))
     {
       return serializer.deserialize(stream);
     }  catch (Exception ex) {
       throw new ApplicationException("Unable to retrieve the specified rocket.", ex);
    }
  }

  /**
   *
   * @param rocket
   * @throws models.ApplicationException
   */
  @Override
  public void Update(Rocket rocket) throws ApplicationException {
    Create(rocket);
  }

  /**
   *
   * @param rocket
   * @throws models.ApplicationException
   */
  @Override
  public void Delete(Rocket rocket) throws ApplicationException {
    try {
      Path path = Paths.get(folder, rocket.getName() + serializer.getDefaultExtension());
      Files.delete(path);
    } catch (IOException ex) {
      throw new ApplicationException("Failed to delete specified rocket.", ex);
    }
  }
}
