package models.rocket.data;

import models.ApplicationException;
import models.rocket.Rocket;

/**
 * Represents a repository of rockets that supports basic data operations (CRUD)
 * @author Jacob
 */
public interface IRocketRepository {

  /**
   * Creates a new rocket in the repository
   * @param rocket The rocket to create
   * @throws models.ApplicationException
   */
  public void create(Rocket rocket) throws ApplicationException;

  /**
   * Retrieves the specified rocket from the repository
   * @param id The id of the rocket to retrieve
   * @return Returns the specified rocket, or null if it could not be found
   * @throws models.ApplicationException
   */
  public Rocket retrieve(String id) throws ApplicationException;

  /**
   * Updates the rocket state in the repository
   * @param rocket
   * @throws models.ApplicationException
   */
  public void update(Rocket rocket) throws ApplicationException;

  /**
   * Deletes the specified rocket from the repository
   * @param rocket The rocket to delete
   * @throws models.ApplicationException
   */
  public void delete(Rocket rocket) throws ApplicationException;
}
