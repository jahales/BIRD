package models;

/**
 * Generic application exception (distinguishes business logic exceptions from framework exceptions)
 * @author Jacob
 */
public class ApplicationException extends Exception {
  Exception innerException;
  
  /**
   *
   * @param message
   */
  public ApplicationException(String message) {
    super(message);
  }
  
  /**
   *
   * @param message
   * @param innerException
   */
  public ApplicationException(String message, Exception innerException) {
    super(message + " (" + innerException.getMessage() + ")");
    this.innerException = innerException;
  }
}
