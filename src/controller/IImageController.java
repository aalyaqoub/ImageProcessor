package controller;

/**
 * This interface represents operations that should be offered by a controller for the IImage.
 */
public interface IImageController {
  /**
   * Allows user to run the image program by using various command inputs.
   *
   * @throws IllegalStateException if the controller is unable to successfully read input or
   *                               transmit output.
   */
  void runImageEditor() throws IllegalStateException;
}
