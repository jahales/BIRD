package models;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Generic interface for serializing data
 * @author Jacob
 */
public interface ISerializer<T> {
  void serialize(T o, OutputStream outputStream) throws Exception;
  T deserialize(InputStream inputStream) throws Exception;
}
