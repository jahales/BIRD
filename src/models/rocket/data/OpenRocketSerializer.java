/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.rocket.data;

import java.io.InputStream;
import java.io.OutputStream;

import models.ISerializer;
import models.rocket.Rocket;

/**
 * Serializes and deserializes a rocket for interoperability with other programs
 * using the OpenRocket file format.
 * 
 * @author Jacob
 */
public class OpenRocketSerializer implements ISerializer<Rocket> {

  /**
   * Serializes a rocket to the specified output stream
   * 
   * @param rocket
   *          The rocket to serialize
   * @param outputStream
   *          The stream to write to
   * @throws Exception
   */
  @Override
  public void serialize(Rocket rocket, OutputStream outputStream) throws Exception {
    throw new UnsupportedOperationException("Not supported yet."); // To change
                                                                   // body of
                                                                   // generated
                                                                   // methods,
                                                                   // choose
                                                                   // Tools |
                                                                   // Templates.
  }

  /**
   * Deserializes a rocket from the specified input stream
   * 
   * @param inputStream
   *          The stream to read from
   * @return Returns the deserialized Rocket
   * @throws Exception
   */
  @Override
  public Rocket deserialize(InputStream inputStream) throws Exception {
    throw new UnsupportedOperationException("Not supported yet."); // To change
                                                                   // body of
                                                                   // generated
                                                                   // methods,
                                                                   // choose
                                                                   // Tools |
                                                                   // Templates.
  }

}
