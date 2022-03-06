package controller.commands;

import model.image.IImageMutable;
import model.pixel.Pixel;

/**
 * An IProcess implementation that is capable of implementing another IProcess to a partial image.
 */
public class PartialProcess implements IProcess {
  IImageMutable mask;
  IProcess process;

  /**
   * Constructs a Partial Process with the image mask and the desired process.
   * @param mask the mask of an image where black means it should implement the process to it
   * @param process the process to implement on image
   */
  public PartialProcess(IImageMutable mask, IProcess process) {
    if (process == null || mask == null) {
      throw new IllegalArgumentException("Can't have null inputs");
    }
    this.mask = mask;
    this.process = process;
  }

  @Override
  public IImageMutable process(IImageMutable image) {
    if (image == null) {
      throw new IllegalArgumentException("Can't do a partial process on a null image");
    }
    IImageMutable resultImage = this.process.process(image);
    for (int row = 0; row < this.mask.getHeight(); row++) {
      for (int col = 0; col < this.mask.getWidth(); col++) {
        if (!this.mask.getPixel(row, col).equals(new Pixel(0, 0, 0, this.mask.getMaxValue()))) {
          resultImage.setPixel(row, col, image.getPixel(row, col));
        }
      }
    }
    return resultImage;
  }
}