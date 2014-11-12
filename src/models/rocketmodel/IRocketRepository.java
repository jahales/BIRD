/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.rocketmodel;

/**
 *
 * @author Jacob
 */
public interface IRocketRepository {
  public Rocket Create(String id);
  public Rocket Retrieve(String id);
  public void Update(Rocket rocket);
  public void Delete(Rocket rocket);
}
