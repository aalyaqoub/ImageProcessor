package model.pixel;

/**
 * This interface represents operations that should be offered by an non-mutable pixels. These
 * methods will not change the the current pixel.
 */
public interface IPixel {
  /**
   * Calculates the maximum value of the three components for this pixel.
   *
   * @return the maximum value of the three components for this pixel
   */
  int getValue();

  /**
   * Calculates the average of the three components for this pixel.
   *
   * @return the average of the three components for this pixel
   */
  double getIntensity();

  /**
   * Calculates the weighted sum of the three components for this pixel.
   *
   * @return the weighted sum of the three components for this pixel
   */
  double getLuma();

  /**
   * Creates a clone copy of this pixel.
   *
   * @return a clone copy of this pixel.
   */
  public IPixelMutable clonePixel();

  /**
   * Get the red rgb value.
   *
   * @return the red rgb value
   */
  int getRed();

  /**
   * Get the green rgb value.
   *
   * @return the green rgb value
   */
  int getGreen();

  /**
   * Get the blue rgb value.
   *
   * @return the blue rgb value
   */
  int getBlue();

  /**
   * Get the max rgb value.
   *
   * @return the max rgb value
   */
  int getMaxValue();
}
