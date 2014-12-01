package models;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Generic interface for serializing data
 * @author Jacob
 * @param <T> The type of object to serialize.
 */
public interface ISerializer<T> {

  /**
   * Serializes the specified object instance.
   * @param o The instance to serialize.
   * @param outputStream The output stream for the result.
   * @throws Exception
   */
  void serialize(T o, OutputStream outputStream) throws Exception;

  /**
   * Deserializes the specified stream into an object instance.
   * @param inputStream The stream to deserialize from.
   * @return Returns an object instance as deserialized.
   * @throws Exception
   */
  T deserialize(InputStream inputStream) throws Exception;
}
