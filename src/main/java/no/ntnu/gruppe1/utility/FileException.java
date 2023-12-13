package no.ntnu.gruppe1.utility;

/**
 * Thrown if a file is invalid.
 *
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @version 2023.03.16
 */
public class FileException extends IllegalArgumentException {

  /**
   * Constructs an exception with a detailed message.
   *
   * @param message the detailed message to display
   */
  public FileException(String message) {
    super(message);
  }
}
