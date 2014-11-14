package models.rocket.data;

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
   */
  @Override
  public void Create(Rocket rocket) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  /**
   *
   * @param id
   * @return
   */
  @Override
  public Rocket Retrieve(String id) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  /**
   *
   * @param rocket
   */
  @Override
  public void Update(Rocket rocket) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  /**
   *
   * @param rocket
   */
  @Override
  public void Delete(Rocket rocket) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
