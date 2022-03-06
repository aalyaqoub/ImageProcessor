package controller.commands.brightness;

import controller.commands.IProcess;
import model.image.IImageMutable;
import model.image.ImageImpl;
import model.pixel.IPixel;
import model.pixel.IPixelMutable;

/**
 * Abstract implementation of IProcess that allows for the modification of pixel brightness.
 */
public abstract class AbstractBrightness implements IProcess {
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

        IPixel newPixel = this.brightnessEffect(currentPixel);

        imageOutput.setPixel(row, col, newPixel);
      }
    }
    return imageOutput;
  }

  /**
   * Takes in a pixel and creates a new pixel with the modified brightness.
   *
   * @param currentPixel the pixel that needs its brightness changed
   * @return an IPixelMutable with the changed brightness
   */
  private IPixelMutable brightnessEffect(IPixelMutable currentPixel) {
    IPixelMutable newPixel = currentPixel.clonePixel();
    newPixel.setRed(this.operator(newPixel.getRed()));
    newPixel.setGreen(this.operator(newPixel.getGreen()));
    newPixel.setBlue(this.operator(newPixel.getBlue()));
    return newPixel;
  }

  /**
   * Calculates the new rgb channel value.
   *
   * @param currentValue the current rgb channel value
   * @return the new rgb channel value
   */
  protected abstract int operator(int currentValue);

}
