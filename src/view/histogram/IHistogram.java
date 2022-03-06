package view.histogram;

import java.awt.image.BufferedImage;

import model.image.IImage;

/**
 * This interface represents operations that should be offered by histogram implementations.
 */
public interface IHistogram {
  /**
   * Creates a histogram with the given IImage and saves it to the res/appData as histogram.png.
   *
   * @param image the IImage with which to create a histogram
   */
  public BufferedImage showHistogram(IImage image);

//  /**
//   * Creates a histogram with the given IImage and saves it to the path.
//   *
//   * @param image the IImage with which to create a histogram
//   * @param path  the path to save the histogram to
//   */
//  public void showHistogram(IImage image, String path);

  /**
   * Get the count for each pixel value for the red channel.
   *
   * @param value the value to get the count of
   * @return the count for each pixel value for the green channel
   */
  public int redCount(int value);

  /**
   * Get the count for each pixel value for the green channel.
   *
   * @param value the value to get the count of
   * @return the count for each pixel value for the green channel
   */
  public int greenCount(int value);

  /**
   * Get the count for each pixel value for the blue channel.
   *
   * @param value the value to get the count of
   * @return the count for each pixel value for the blue channel
   */
  public int blueCount(int value);

  /**
   * Get the count for each pixel value for the intensity channel.
   *
   * @param value the value to get the count of
   * @return the count for each pixel value for the intensity channel
   */
  public int intensityCount(int value);
}
