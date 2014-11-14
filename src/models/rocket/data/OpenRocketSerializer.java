/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.rocket.data;

import java.io.InputStream;
import java.io.OutputStream;
import models.rocketmodel.Rocket;

/**
 *
 * @author Jacob
 */
public class OpenRocketSerializer implements IRocketSerializer {

  @Override
  public void serialize(Rocket rocket, OutputStream outputStream) throws Exception {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Rocket deserialize(InputStream inputStream) throws Exception {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
}
