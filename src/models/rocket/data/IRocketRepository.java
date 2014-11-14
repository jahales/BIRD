package models.rocket.data;

import models.rocketmodel.Rocket;

/**
 * Represents a repository of rockets that supports basic data operations (CRUD)
 * @author Jacob
 */
public interface IRocketRepository {

  /**
   * Creates a new rocket in the repository
   * @param rocket The rocket to create
   */
  public void Create(Rocket rocket);

  /**
   * Retrieves the specified rocket from the repository
   * @param id The id of the rocket to retrieve
   * @return Returns the specified rocket, or null if it could not be found
   */
  public Rocket Retrieve(String id);

  /**
   * Updates the rocket state in the repository
   * @param rocket
   */
  public void Update(Rocket rocket);

  /**
   * Deletes the specified rocket from the repository
   * @param rocket The rocket to delete
   */
  public void Delete(Rocket rocket);
}
