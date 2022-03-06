package model.pixel;

/**
 * This interface represents operations that should be offered by an mutable pixels. These methods
 * will change the the current pixel.
 */
public interface IPixelMutable extends IPixel {
  /**
   * Sets the red rgb value.
   *
   * @param value the red rgb value.
   */
  void setRed(int value);

  /**
   * Sets the green rgb value.
   *
   * @param value the green rgb value.
   */
  void setGreen(int value);

  /**
   * Sets the blue rgb value.
   *
   * @param value the blue rgb value.
   */
  void setBlue(int value);
}
