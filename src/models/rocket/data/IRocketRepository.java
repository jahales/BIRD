package models.rocket.data;

import models.rocketmodel.Rocket;

/**
 *
 * @author Jacob
 */
public interface IRocketRepository {

  /**
   *
   * @param rocket
   */
  public void Create(Rocket rocket);

  /**
   *
   * @param id
   * @return
   */
  public Rocket Retrieve(String id);

  /**
   *
   * @param rocket
   */
  public void Update(Rocket rocket);

  /**
   *
   * @param rocket
   */
  public void Delete(Rocket rocket);
}
