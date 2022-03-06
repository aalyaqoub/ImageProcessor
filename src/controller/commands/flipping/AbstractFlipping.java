package controller.commands.flipping;

import controller.commands.IProcess;
import model.image.IImageMutable;
import model.image.ImageImpl;
import model.pixel.IPixel;

/**
 * Abstract implementation of IProcess that allows for the modification of pixel positioning.
 */
public abstract class AbstractFlipping implements IProcess {
  @Override
  public IImageMutable process(IImageMutable image) {
    if (image == null) {
      throw new IllegalArgumentException("Can't apply a process image on a null image");
    }

    IImageMutable imageOutput = new ImageImpl(image.getHeight(), image.getWidth(),
            image.getMaxValue());
    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {
        IPixel currentPixel = image.getPixel(row, col);

        int[] newCoordinates = this.getNewCoordinates(image.getHeight(), image.getWidth(), row,
                col);

        IPixel newPixel = currentPixel.clonePixel();

        imageOutput.setPixel(newCoordinates[0], newCoordinates[1], newPixel);
      }
    }
    return imageOutput;
  }

  /**
   * Takes in a max height and width of the image and the row and column of the current pixel to
   * calculate the new position.
   *
   * @param height      the max image height
   * @param width       the max image width
   * @param rowPosition the row of the current pixel
   * @param colPosition the col of the current pixel
   * @return an array with the new row and col position
   */
  protected abstract int[] getNewCoordinates(int height, int width, int rowPosition,
                                             int colPosition);

}
