package controller.commands.filter;

import controller.commands.CommandUtils;
import controller.commands.IProcess;
import model.image.IImage;
import model.image.IImageMutable;
import model.image.ImageImpl;
import model.pixel.IPixelMutable;
import model.pixel.Pixel;

/**
 * Abstract implementation of IProcess that allows for using filters on images.
 */
public abstract class AbstractFiltering implements IProcess {
  private final double[][] kernel;

  /**
   * creates command and initializes the kernel for use in a Filtering command.
   *
   * @param kernel filtering kernel to be applied to images
   */
  protected AbstractFiltering(double[][] kernel) {
    this.kernel = kernel;
  }

  /**
   * Applies a process on an IImageMutable and gives back the result.
   *
   * @param image the IImageMutable that needs to be modified
   * @return the modified IImageMutable
   */
  @Override
  public IImageMutable process(IImageMutable image) {
    if (image == null) {
      throw new IllegalArgumentException("Can't apply a process image on a null image");
    }


    IImageMutable imageOutput = new ImageImpl(image.getHeight(), image.getWidth(),
            image.getMaxValue());
    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {
        imageOutput.setPixel(row, col, filterPixel(image, row, col));
      }
    }
    return imageOutput;
  }

  /*
  this method applies a kernel to a pixel using information from surrounding pixels.
   */
  protected IPixelMutable filterPixel(IImage image, int row, int col) {
    double[] rgb = {0, 0, 0};
    int maxval = image.getMaxValue();
    CommandUtils util = new CommandUtils();

    for (int i = -kernel.length / 2; i <= kernel.length / 2; i++) {
      for (int j = -kernel.length / 2; j <= kernel.length / 2; j++) {
        int krow = i + kernel.length / 2;
        int kcol = j + kernel.length / 2;
        int[] scannedPixel = {row + i, col + j};

        try {
          rgb[0] += image.getPixel(scannedPixel[0], scannedPixel[1]).getRed() * kernel[krow][kcol];
          rgb[1] += image.getPixel(scannedPixel[0], scannedPixel[1]).getGreen() *
                  kernel[krow][kcol];
          rgb[2] += image.getPixel(scannedPixel[0], scannedPixel[1]).getBlue() * kernel[krow][kcol];

        } catch (Exception ignored) {
          //ignores index out of bounds exceptions from attempting to access pixels that
          // may not exist.
        }

      }
    }
    return new Pixel(util.pixelInBounds((int) rgb[0], maxval),
            util.pixelInBounds((int) rgb[1], maxval),
            util.pixelInBounds((int) rgb[2], maxval), maxval);
  }


}
