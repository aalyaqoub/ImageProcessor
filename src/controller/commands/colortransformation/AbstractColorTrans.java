package controller.commands.colortransformation;

import controller.commands.CommandUtils;
import controller.commands.IProcess;
import model.image.IImageMutable;
import model.image.ImageImpl;
import model.pixel.IPixel;
import model.pixel.Pixel;

/**
 * Abstract class which can be extended for various Color Transformation commands.
 */
public abstract class AbstractColorTrans implements IProcess {
  private final double[][] transMatrix;


  protected AbstractColorTrans(double[][] transMatrix) {
    this.transMatrix = transMatrix;
  }


  @Override
  public IImageMutable process(IImageMutable image) {
    if (image == null) {
      throw new IllegalArgumentException("Can't apply a process image on a null image");
    }

    IImageMutable imageOutput = new ImageImpl(image.getHeight(), image.getWidth(),
            image.getMaxValue());
    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {
        imageOutput.setPixel(row, col, processPixel(image, row, col));
      }
    }
    return imageOutput;
  }

  /*
  applies the transformation matrix to a pixel.
   */
  protected IPixel processPixel(IImageMutable image, int row, int col) {
    int maxval = image.getMaxValue();
    int[] rgb = {0, 0, 0};
    IPixel pixel = image.getPixel(row, col);
    CommandUtils util = new CommandUtils();

    for (int i = 0; i < 3; i++) {
      rgb[i] = (int) (pixel.getRed() * transMatrix[i][0]
              + pixel.getGreen() * transMatrix[i][1]
              + pixel.getBlue() * transMatrix[i][2]);


    }
    return new Pixel(util.pixelInBounds(rgb[0], maxval),
            util.pixelInBounds(rgb[1], maxval),
            util.pixelInBounds(rgb[2], maxval),
            image.getMaxValue());
  }

}
