package controller.commands.color;

import model.pixel.IPixelMutable;

/**
 * Represents an implementation of IProcess that changes a pixel to its value greyscale.
 */
public class PixelValues extends AbstractColorComponent {
  @Override
  protected IPixelMutable getComponent(IPixelMutable currentPixel) {
    IPixelMutable newPixel = currentPixel.clonePixel();
    int value = newPixel.getValue();
    newPixel.setRed(value);
    newPixel.setGreen(value);
    newPixel.setBlue(value);
    return newPixel;
  }

  @Override
  public String toString() {
    return "pixel-values greyscale";
  }
}
