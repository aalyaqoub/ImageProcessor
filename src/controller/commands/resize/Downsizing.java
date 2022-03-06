package controller.commands.resize;

import controller.commands.IProcess;
import model.image.IImageMutable;
import model.image.ImageImpl;
import model.pixel.IPixelMutable;
import model.pixel.Pixel;

/**
 * An implementation of IProcess that downsizes an image.
 */
public class Downsizing implements IProcess {
  private final int width;
  private final int height;

  /**
   * Constructs a downsize with the new width and height.
   *
   * @param width  the new width
   * @param height the new height
   */
  public Downsizing(int width, int height) {
    if (width < 1 || height < 1) {
      throw new IllegalArgumentException("width and height can't be 0");
    }
    this.width = width;
    this.height = height;
  }

  @Override
  public IImageMutable process(IImageMutable image) {
    if (image == null) {
      throw new IllegalArgumentException("Can't downsize a null image");
    }
    if (this.width > image.getWidth() || this.height > image.getHeight()) {
      throw new IllegalArgumentException("Can't downsize to a bigger image");
    }
    if (this.width == image.getWidth() && this.height == image.getHeight()) {
      return image.getClone();
    }
    IImageMutable imageOutput = new ImageImpl(this.height, this.width,
            image.getMaxValue());
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        double desiredX = col * 1.0 / this.width * image.getWidth(); // x
        double desiredY = row * 1.0 / this.height * image.getHeight(); // y
        IPixelMutable newPixel = getValue(image, desiredX, desiredY);
        imageOutput.setPixel(row, col, newPixel);
      }
    }
    return imageOutput;
  }


  private IPixelMutable getValue(IImageMutable image, double desiredX, double desiredY) {
    IPixelMutable a = image.getPixel((int) Math.floor(desiredY), (int) Math.floor(desiredX));
    IPixelMutable b = image.getPixel((int) Math.floor(desiredY), (int) Math.ceil(desiredX));
    IPixelMutable c = image.getPixel((int) Math.ceil(desiredY), (int) Math.floor(desiredX));
    IPixelMutable d = image.getPixel((int) Math.ceil(desiredY), (int) Math.ceil(desiredX));
    int redValue = Math.min(this.getComponentValue(a.getRed(), b.getRed(), c.getRed(), d.getRed(),
            desiredX, desiredY), image.getMaxValue());
    int greenValue = Math.min(this.getComponentValue(a.getGreen(), b.getGreen(), c.getGreen(),
            d.getGreen(), desiredX, desiredY), image.getMaxValue());
    int blueValue = Math.min(this.getComponentValue(a.getBlue(), b.getBlue(), c.getBlue(),
            d.getBlue(), desiredX, desiredY), image.getMaxValue());
    return new Pixel(redValue, greenValue, blueValue, image.getMaxValue());
  }

  private int getComponentValue(int c1, int c2, int c4, int c3, double desiredX, double desiredY) {
    double sameCeil = (Math.ceil(desiredX) == desiredX) ? .5 : (Math.ceil(desiredX) - desiredX);
    double sameFloor = (desiredX == Math.floor(desiredX)) ? .5 : (desiredX - Math.floor(desiredX));
    double redRow1Avg = c2 * sameFloor + c1 * sameCeil;
    double redRow2Avg = c4 * sameFloor + c3 * sameCeil;
    return (int) (redRow2Avg * ((desiredY == Math.floor(desiredY)) ?
            .5 : (desiredY - Math.floor(desiredY))) + redRow1Avg *
            ((desiredY == Math.ceil(desiredY)) ? .5 : (Math.ceil(desiredY) - desiredY)));
  }

  @Override
  public String toString() {
    return "downsizing";
  }
}
