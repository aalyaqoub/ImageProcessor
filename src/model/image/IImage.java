package model.image;

import java.awt.image.BufferedImage;

import controller.commands.brightness.Darken;
import model.pixel.IPixelMutable;

/**
 * This interface represents non-mutable operations that should be offered by an IImage
 * implementation. These operations will not change the Image
 */
public interface IImage {

  /**
   * Gets the width of the image.
   *
   * @return the width of the image
   */
  int getWidth();

  /**
   * Gets the height of the image.
   *
   * @return the height of the image
   */
  int getHeight();

  /**
   * Gets the pixel at the specific row and column.
   *
   * @param row the row of the wanted pixel
   * @param col the column of the wanted pixel
   * @return a Mutable version of the pixel
   */
  IPixelMutable getPixel(int row, int col);

  /**
   * Get the max rgb value.
   *
   * @return the max rgb value
   */
  int getMaxValue();

  /**
   * Gives back a clone of the image.
   *
   * @return a clone of the image.
   */
  IImageMutable getClone();

  /**
   * Gives back a {@link BufferedImage} of the current image.
   *
   * @return a BufferedImage of the current image.
   */
  BufferedImage getBufferedImage();
}
