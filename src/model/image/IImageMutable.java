package model.image;

import model.pixel.IPixel;

/**
 * This interface represents mutable operations that should be offered by an IImage implementation.
 * These operations will change the Image
 */
public interface IImageMutable extends IImage {
  /**
   * Sets a pixel at the specific row, height, and column, width.
   *
   * @param row   the row for the pixel to be placed
   * @param col   the column for the pixel to be placed
   * @param pixel the pixel to be placed
   */
  void setPixel(int row, int col, IPixel pixel);


}
