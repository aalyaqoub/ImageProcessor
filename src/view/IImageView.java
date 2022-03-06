package view;

import java.io.IOException;

/**
 * This interface represents operations that should be offered by a view for IImage.
 */
public interface IImageView {
  /**
   * Render a specific message to the provided data destination.
   *
   * @param message the message to be transmitted
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  void renderMessage(String message) throws IOException;

  /**
   * Render an Error message to the provided data destination.
   *
   * @param message the error message to be transmitted
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  void renderErrorMessage(String message) throws IOException;
}
