package models.rocketmodel.data;

import models.rocketmodel.Rocket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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
