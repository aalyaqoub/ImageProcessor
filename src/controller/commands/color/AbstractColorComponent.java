package controller.commands.color;

import controller.commands.IProcess;
import model.image.IImageMutable;
import model.image.ImageImpl;
import model.pixel.IPixel;
import model.pixel.IPixelMutable;

/**
 * Abstract implementation of IProcess that allows for the modification of pixel color Components.
 */
public abstract class AbstractColorComponent implements IProcess {
  @Override
  public IImageMutable process(IImageMutable image) {
    if (image == null) {
      throw new IllegalArgumentException("Can't apply a process image on a null image");
    }

    IImageMutable imageOutput = new ImageImpl(image.getHeight(), image.getWidth(),
            image.getMaxValue());
    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {
        IPixelMutable currentPixel = image.getPixel(row, col);

        IPixel newPixel = this.getComponent(currentPixel);

        imageOutput.setPixel(row, col, newPixel);
      }
    }
    return imageOutput;
  }

  /**
   * Get the new rgb value based on the implementation.
   *
   * @param currentPixel the current pixel
   * @return a new pixel with the modified rgb value.
   */
  protected abstract IPixelMutable getComponent(IPixelMutable currentPixel);
}
