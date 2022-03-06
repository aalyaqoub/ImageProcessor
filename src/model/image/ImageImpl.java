package model.image;

import java.awt.*;
import java.awt.image.BufferedImage;

import controller.commands.reader.ContentPair;
import model.pixel.IPixel;
import model.pixel.IPixelMutable;
import model.pixel.Pixel;

/**
 * Represents an implementation of IImageMutable that allows for the representation of image files
 * as a 2D array of pixels.
 */
public class ImageImpl implements IImageMutable {
  private IPixel[][] image;
  private int maxValue;

  /**
   * Constructs an ImageImpl with a null image and 0 maxvalue.
   */
  public ImageImpl() {
    this.image = null;
    this.maxValue = 0;
  }

  /**
   * Constructs an ImageImpl by extracting the 2D array of pixels and maxvalue from ContentPair.
   *
   * @param fileContent the ContentPair with the 2D array of pixels and and integer maxvalue
   */
  public ImageImpl(ContentPair<IPixel[][], Integer> fileContent) {
    if (fileContent == null) {
      throw new IllegalArgumentException("Can't have null inputs");
    }
    this.image = fileContent.getFirst();
    this.maxValue = fileContent.getSecond();
  }

  /**
   * Constructs an image with the given size and pixel value constraints.
   *
   * @param row      the height of the image
   * @param col      the width of the image
   * @param maxValue the maxvalue of the pixels
   */
  public ImageImpl(int row, int col, int maxValue) {
    if (row < 0 || col < 0 || maxValue < 0) {
      throw new IllegalArgumentException("Can't have negative row, col, maxValue");
    }
    this.image = new Pixel[row][col];
    this.maxValue = maxValue;
  }

  /**
   * Constructs an Image with a 2D array of pixels.
   *
   * @param pixels a 2D array of pixels representing the desired image.
   */
  public ImageImpl(IPixel[][] pixels) {
    if (pixels == null) {
      throw new IllegalArgumentException("Can't have null inputs");
    }
    this.image = new Pixel[pixels.length][pixels[0].length];
    for (int row = 0; row < pixels.length; row++) {
      for (int col = 0; col < pixels[0].length; col++) {
        this.image[row][col] = pixels[row][col];
      }
    }
  }

  @Override
  public void setPixel(int row, int col, IPixel pixel) {
    if (row >= 0 && row < this.getHeight() && col >= 0 && col < this.getWidth()) {
      this.image[row][col] = pixel;
    } else {
      throw new IllegalArgumentException("Given row: " + row + " Given col:" + col +
              "\nRow and col have to be greater than 0 and less than "
              + this.getHeight() + " and " + this.getWidth() + " to reassign pixel");
    }
  }


  @Override
  public IPixelMutable getPixel(int row, int col) {
    if (row >= 0 && row < this.getHeight() && col >= 0 && col < this.getWidth()) {
      return this.image[row][col].clonePixel();
    } else {
      throw new IllegalArgumentException("Given row: " + row + " Given col:" + col +
              "\nRow and col have to be greater than 0 and less than "
              + this.getHeight() + " and " + this.getWidth() + " to get pixel");
    }
  }

  @Override
  public int getWidth() {
    return this.image[0].length;
  }

  @Override
  public int getHeight() {
    return this.image.length;
  }

  @Override
  public int getMaxValue() {
    return this.maxValue;
  }

  @Override
  public IImageMutable getClone() {
    IPixel[][] cloneImage = new IPixel[this.image.length][this.image[0].length];
    for (int row = 0; row < this.image.length; row++) {
      for (int col = 0; col < this.image[0].length; col++) {
        cloneImage[row][col] = this.getPixel(row, col);
      }
    }

    return new ImageImpl(new ContentPair<>(cloneImage, this.maxValue));
  }

  @Override
  public BufferedImage getBufferedImage() {
    BufferedImage bufferedImage = new BufferedImage(this.getWidth(), this.getHeight(),
            BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < this.getHeight(); i++) {
      for (int j = 0; j < this.getWidth(); j++) {
        IPixel pixel = this.image[i][j];
        Color c = new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue());

        bufferedImage.setRGB(j, i, c.getRGB());
      }
    }

    return bufferedImage;
  }
}
