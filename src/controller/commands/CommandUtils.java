package controller.commands;

/**
 * class for useful utilities for image commands.
 */
public class CommandUtils {
  /**
   * returns the truncated pixel value.
   * @param val rgb value for pixel
   * @param maxval max value allowed
   * @return the maximum in bounds rgb value
   */
  public int pixelInBounds(int val, int maxval) {
    if (val >= 0) {
      return Math.min(val, maxval);
    }
    return 0;
  }
}
